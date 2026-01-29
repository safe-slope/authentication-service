package io.github.safeslope.api;

import io.github.safeslope.JwtKeyProvider;
import io.github.safeslope.entities.Tenant;
import io.github.safeslope.entities.User;
import io.github.safeslope.tenant.service.TenantService;
import io.github.safeslope.user.service.UserNotFoundException;
import io.github.safeslope.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.util.Base64;

@Component
@ConditionalOnProperty(name = "app.data-initializer.enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final TenantService tenantService;
    private final JwtKeyProvider jwtKeyProvider;
    private final String tenantName;
    private final String username;
    private final String password;

    public DataInitializer(UserService userService,
                           TenantService tenantService,
                           JwtKeyProvider jwtKeyProvider,
                           @Value("${app.data-initializer.tenant-name}") String tenantName,
                           @Value("${app.data-initializer.username}") String username,
                           @Value("${app.data-initializer.password}") String password) {
        this.userService = userService;
        this.tenantService = tenantService;
        this.jwtKeyProvider = jwtKeyProvider;
        this.tenantName = tenantName;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run(String... args) {
        log.info("Initializing super admin user with credentials from environment...");

        // Log JWT private key diagnostics (SAFE: no key material printed)
        try {
            PrivateKey pk = jwtKeyProvider.privateKey();
            byte[] encoded = pk.getEncoded();

            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            String fingerprintB64 = Base64.getEncoder().encodeToString(sha256.digest(encoded));

            log.info("JWT private key loaded successfully. algorithm={}, format={}, encodedBytes={}, sha256(b64)={}",
                    pk.getAlgorithm(), pk.getFormat(), encoded != null ? encoded.length : -1, fingerprintB64);
        } catch (Exception e) {
            log.error("Failed to load JWT private key via JwtKeyProvider: {}", e.getMessage(), e);
        }

        // Check if super admin user already exists
        try {
            userService.getByUsername(username);
            log.info("Super admin user '{}' already exists, skipping initialization", username);
            return;
        } catch (UserNotFoundException e) {
            // User doesn't exist, proceed with initialization
        }

        // Create or get tenant
        Tenant tenant;
        try {
            tenant = tenantService.getAll().stream()
                    .filter(t -> tenantName.equals(t.getName()))
                    .findFirst()
                    .orElse(null);

            if (tenant == null) {
                tenant = Tenant.builder()
                        .name(tenantName)
                        .build();
                tenant = tenantService.create(tenant);
                log.info("Created tenant '{}' with ID: {}", tenantName, tenant.getId());
            } else {
                log.info("Tenant '{}' already exists with ID: {}", tenantName, tenant.getId());
            }
        } catch (Exception e) {
            log.error("Failed to retrieve tenants, attempting to create new tenant: {}", e.getMessage());
            tenant = Tenant.builder()
                    .name(tenantName)
                    .build();
            tenant = tenantService.create(tenant);
            log.info("Created tenant '{}' with ID: {}", tenantName, tenant.getId());
        }

        // Create super admin user
        User superAdmin = User.builder()
                .username(username)
                .password(password)
                .role(User.Role.SUPER_ADMIN)
                .tenant(tenant)
                .build();

        superAdmin = userService.create(superAdmin);
        log.info("Created super admin user '{}' with SUPER_ADMIN role and ID: {}",
                superAdmin.getUsername(), superAdmin.getId());
    }
}
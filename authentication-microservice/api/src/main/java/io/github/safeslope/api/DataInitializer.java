package io.github.safeslope.api;

import io.github.safeslope.JwtKeyProvider;
import io.github.safeslope.entities.Tenant;
import io.github.safeslope.entities.User;
import io.github.safeslope.tenant.service.TenantService;
import io.github.safeslope.user.service.UserNotFoundException;
import io.github.safeslope.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.util.Base64;

@Component
@Profile("development")
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final TenantService tenantService;
    private final JwtKeyProvider jwtKeyProvider;

    public DataInitializer(UserService userService,
                           TenantService tenantService,
                           JwtKeyProvider jwtKeyProvider) {
        this.userService = userService;
        this.tenantService = tenantService;
        this.jwtKeyProvider = jwtKeyProvider;
    }

    @Override
    public void run(String... args) {
        log.info("Initializing test data for development environment...");

        // Log JWT private key diagnostics (SAFE: no key material printed)
        try {
            PrivateKey pk = jwtKeyProvider.privateKey();
            byte[] encoded = pk.getEncoded();

            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            String fingerprintB64 = Base64.getEncoder().encodeToString(sha256.digest(encoded));

            log.info("JWT private key loaded successfully. algorithm={}, format={}, encodedBytes={}, sha256(b64)={}",
                    pk.getAlgorithm(), pk.getFormat(), encoded != null ? encoded.length : -1, fingerprintB64);
        } catch (Exception e) {
            log.error("Failed to load JWT private key via JwtKeyProvider in development profile: {}", e.getMessage(), e);
        }

        // Check if test user already exists
        try {
            userService.getByUsername("user-test");
            log.info("Test user already exists, skipping initialization");
            return;
        } catch (UserNotFoundException e) {
            // User doesn't exist, proceed with initialization
        }

        // Create or get test tenant
        Tenant testTenant;
        try {
            testTenant = tenantService.getAll().stream()
                    .filter(t -> "test-tenant".equals(t.getName()))
                    .findFirst()
                    .orElse(null);

            if (testTenant == null) {
                testTenant = Tenant.builder()
                        .name("test-tenant")
                        .build();
                testTenant = tenantService.create(testTenant);
                log.info("Created test tenant with ID: {}", testTenant.getId());
            } else {
                log.info("Test tenant already exists with ID: {}", testTenant.getId());
            }
        } catch (Exception e) {
            testTenant = Tenant.builder()
                    .name("test-tenant")
                    .build();
            testTenant = tenantService.create(testTenant);
            log.info("Created test tenant with ID: {}", testTenant.getId());
        }

        // Create test user with SUPER_ADMIN role
        User testUser = User.builder()
                .username("user-test")
                .password("user-test")
                .role(User.Role.SUPER_ADMIN)
                .tenant(testTenant)
                .build();

        testUser = userService.create(testUser);
        log.info("Created test user '{}' with SUPER_ADMIN role and ID: {}",
                testUser.getUsername(), testUser.getId());
    }
}
package io.github.safeslope.api;

import io.github.safeslope.entities.Tenant;
import io.github.safeslope.entities.User;
import io.github.safeslope.tenant.repository.TenantRepository;
import io.github.safeslope.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("development")
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, 
                          TenantRepository tenantRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        log.info("Initializing test data for development environment...");
        
        // Check if test user already exists
        User existingUser = userRepository.findByUsername("user-test");
        if (existingUser != null) {
            log.info("Test user already exists, skipping initialization");
            return;
        }
        
        // Create or get test tenant
        Tenant testTenant = tenantRepository.findByName("test-tenant");
        if (testTenant == null) {
            testTenant = Tenant.builder()
                    .name("test-tenant")
                    .build();
            testTenant = tenantRepository.save(testTenant);
            log.info("Created test tenant with ID: {}", testTenant.getId());
        } else {
            log.info("Test tenant already exists with ID: {}", testTenant.getId());
        }
        
        // Create test user with SUPER_ADMIN role
        User testUser = User.builder()
                .username("user-test")
                .password(passwordEncoder.encode("user-test"))
                .role(User.Role.SUPER_ADMIN)
                .tenant(testTenant)
                .build();
        
        testUser = userRepository.save(testUser);
        log.info("Created test user '{}' with SUPER_ADMIN role and ID: {}", 
                testUser.getUsername(), testUser.getId());
    }
}

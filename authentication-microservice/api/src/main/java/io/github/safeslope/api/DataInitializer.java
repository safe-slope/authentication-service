package io.github.safeslope.api;

import io.github.safeslope.entities.Tenant;
import io.github.safeslope.entities.User;
import io.github.safeslope.tenant.repository.TenantRepository;
import io.github.safeslope.tenant.service.TenantService;
import io.github.safeslope.user.repository.UserRepository;
import io.github.safeslope.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("development")
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final TenantService tenantService;
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;

    public DataInitializer(UserService userService,
                          TenantService tenantService,
                          UserRepository userRepository,
                          TenantRepository tenantRepository) {
        this.userService = userService;
        this.tenantService = tenantService;
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
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
            testTenant = tenantService.create(testTenant);
            log.info("Created test tenant with ID: {}", testTenant.getId());
        } else {
            log.info("Test tenant already exists with ID: {}", testTenant.getId());
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

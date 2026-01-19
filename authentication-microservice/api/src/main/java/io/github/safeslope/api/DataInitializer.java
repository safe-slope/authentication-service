package io.github.safeslope.api;

import io.github.safeslope.entities.Tenant;
import io.github.safeslope.entities.User;
import io.github.safeslope.tenant.service.TenantService;
import io.github.safeslope.user.service.UserNotFoundException;
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

    public DataInitializer(UserService userService,
                          TenantService tenantService) {
        this.userService = userService;
        this.tenantService = tenantService;
    }

    @Override
    public void run(String... args) {
        log.info("Initializing test data for development environment...");
        
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
            // Try to get tenant by checking all tenants
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
            // If getting all tenants fails, just create a new one
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

package io.github.safeslope.tenant.repository;

public class TenantNotFoundException extends RuntimeException {
    public TenantNotFoundException(Integer id) {
        super("Tenant with ID " + id + " not found.");
    }

    public TenantNotFoundException(String name) {
        super("Tenant with name " + name + " not found.");
    }
}

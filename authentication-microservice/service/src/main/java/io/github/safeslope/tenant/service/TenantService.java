package io.github.safeslope.tenant.service;

import io.github.safeslope.entities.Tenant;
import io.github.safeslope.tenant.repository.TenantNotFoundException;
import io.github.safeslope.tenant.repository.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TenantService {
    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public List<Tenant> getAll() {
        return tenantRepository.findAll();
    }

    public Tenant get(Integer id) {
        return tenantRepository.findById(id).
                orElseThrow(() -> new TenantNotFoundException(id));
    }

    public Tenant create(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public Tenant update(Integer id, Tenant updated) {
        Tenant existing = tenantRepository.findById(id)
                .orElseThrow(() -> new TenantNotFoundException(id));

        existing.setName(updated.getName());
        return tenantRepository.save(existing);
    }

    public void delete(Integer id) {
        if (!tenantRepository.existsById(id)) {
            throw new TenantNotFoundException(id);
        }
        tenantRepository.deleteById(id);
    }
}

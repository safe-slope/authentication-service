package io.github.safeslope.api.v1.controller;

import io.github.safeslope.api.v1.dto.TenantDto;
import io.github.safeslope.api.v1.mapper.TenantMapper;
import io.github.safeslope.tenant.service.TenantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class TenantController {

    private final TenantService tenantService;
    private final TenantMapper tenantMapper;

    public TenantController(TenantService tenantService, TenantMapper tenantMapper) {
        this.tenantService = tenantService;
        this.tenantMapper = tenantMapper;
    }

    @GetMapping
    public Page<TenantDto> list(@PageableDefault(size = 20) Pageable pageable) {
        return tenantService.getAll(pageable).map(tenantMapper::toDto);
    }

    @GetMapping("/{id}")
    public TenantDto get(@PathVariable Integer id) {
        return tenantMapper.toDto(tenantService.get(id));
    }

    @PostMapping
    public TenantDto create(@RequestBody TenantDto dto) {
        return tenantMapper.toDto(tenantService.create(tenantMapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public TenantDto update(@PathVariable Integer id, @RequestBody TenantDto dto) {
        return tenantMapper.toDto(tenantService.update(id, tenantMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tenantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

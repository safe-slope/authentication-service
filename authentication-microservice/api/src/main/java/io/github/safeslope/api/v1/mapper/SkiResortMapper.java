package io.github.safeslope.api.v1.mapper;

import io.github.safeslope.api.v1.dto.SkiResortDto;
import io.github.safeslope.entities.SkiResort;
import io.github.safeslope.entities.Tenant;
import io.github.safeslope.tenant.service.TenantService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SkiResortMapper {
    @Autowired
    protected TenantService tenantService;

    @Mapping(target = "tenantId", source = "tenant.id")
    public abstract SkiResortDto toDto(SkiResort entity);

    public abstract List<SkiResortDto> toDtoList(List<SkiResort> entities);

    @Mapping(target = "tenant", ignore = true)
    public abstract SkiResort toEntity(SkiResortDto dto);

    public abstract List<SkiResort> toEntityList(List<SkiResortDto> dtos);

    @AfterMapping
    protected void afterToEntity(SkiResortDto dto, @MappingTarget SkiResort entity) {
        Integer tenantId = dto.getTenantId();
        if (tenantId != null) {
            Tenant tenant = tenantService.get(tenantId);
            entity.setTenant(tenant);
        } else {
            entity.setTenant(null);
        }
    }
}

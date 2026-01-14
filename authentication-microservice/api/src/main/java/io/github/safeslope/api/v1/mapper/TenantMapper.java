package io.github.safeslope.api.v1.mapper;

import io.github.safeslope.api.v1.dto.TenantDto;
import io.github.safeslope.entities.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TenantMapper {

    public abstract TenantDto toDto(Tenant entity);

    public abstract List<TenantDto> toDtoList(List<Tenant> entities);

    @Mapping(target = "users", ignore = true)
    public abstract Tenant toEntity(TenantDto dto);

    public abstract List<Tenant> toEntityList(List<TenantDto> dtos);
}

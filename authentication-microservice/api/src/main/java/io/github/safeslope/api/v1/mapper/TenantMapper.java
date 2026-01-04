package io.github.safeslope.api.v1.mapper;

import io.github.safeslope.api.v1.dto.TenantDto;
import io.github.safeslope.entities.Tenant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TenantMapper {
    
    TenantDto toDto(Tenant entity);

    List<TenantDto> toDtoList(List<Tenant> entities);

    Tenant toEntity(TenantDto dto);

    List<Tenant> toEntityList(List<TenantDto> dtos);
}

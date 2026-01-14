package io.github.safeslope.api.v1.mapper;

import io.github.safeslope.api.v1.dto.UserDto;
import io.github.safeslope.entities.User;
import io.github.safeslope.entities.Tenant;
import io.github.safeslope.tenant.service.TenantService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    protected TenantService tenantService;

    @Autowired
    public void setTenantService(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @Mapping(target = "role", expression = "java(entity.getRole() != null ? entity.getRole().name() : null)")
    @Mapping(target = "tenantId", source = "tenant.id")
    public abstract UserDto toDto(User entity);

    public abstract List<UserDto> toDtoList(List<User> entities);

    @Mapping(target = "role", expression = "java(dto.getRole() != null ? io.github.safeslope.entities.User.Role.valueOf(dto.getRole()) : null)")
    @Mapping(target = "tenant", ignore = true)
    public abstract User toEntity(UserDto dto);

    public abstract List<User> toEntityList(List<UserDto> dtos);

    @AfterMapping
    protected void afterToEntity(UserDto dto, @MappingTarget User entity) {
        Integer tenantId = dto.getTenantId();
        if (tenantId != null) {
            Tenant tenant = tenantService.get(tenantId);
            entity.setTenant(tenant);
        } else {
            entity.setTenant(null);
        }
    }
}

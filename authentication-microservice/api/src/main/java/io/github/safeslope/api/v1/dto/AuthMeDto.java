package io.github.safeslope.api.v1.dto;

import io.github.safeslope.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthMeDto {
    private String username;
    private User.Role role;
    private String tenantName;
}

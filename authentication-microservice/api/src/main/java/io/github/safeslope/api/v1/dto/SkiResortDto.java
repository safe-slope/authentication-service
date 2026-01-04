package io.github.safeslope.api.v1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkiResortDto {
    private Integer id;
    private String name;
    private String address;
    private Integer tenantId;
}

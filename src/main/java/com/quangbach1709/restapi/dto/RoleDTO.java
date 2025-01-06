package com.quangbach1709.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    private String role;
    private String description;
    private Set<Long> userIds; // Danh sách ID của các User có Role này

}

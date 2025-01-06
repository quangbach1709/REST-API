package com.quangbach1709.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private Boolean isActive;
    private Long personId; // ID của Person của User này
    private Set<Long> roleIds; // Danh sách ID của các Role của User này
}

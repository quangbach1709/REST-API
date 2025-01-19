package com.quangbach1709.restapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private Boolean isActive;
    private PersonDTO person;
    private Set<RoleDTO> roles;
}

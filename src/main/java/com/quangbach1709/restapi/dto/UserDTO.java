package com.quangbach1709.restapi.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private Boolean isActive;
    private PersonDTO person;
}

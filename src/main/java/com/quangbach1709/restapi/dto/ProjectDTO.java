package com.quangbach1709.restapi.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ProjectDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Long companyId;
    private String companyName;
    private Set<PersonDTO> persons;
}
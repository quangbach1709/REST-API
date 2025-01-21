package com.quangbach1709.restapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompanyDTO {
    private Long id;
    private String name;
    private String code;
    private String address;
    private List<DepartmentDTO> departments;
}
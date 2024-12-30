package com.quangbach1709.restapi.dto;

import lombok.Data;

@Data
public class CountryDTO {
    private Long id;
    private String name;
    private String code;
    private String description;
}

package com.quangbach1709.restapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CompanyDTO {
    private Long id;
    private String name;
    private String code;
    private String address;
}

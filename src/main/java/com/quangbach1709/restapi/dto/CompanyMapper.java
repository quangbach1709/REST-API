package com.quangbach1709.restapi.dto;

import com.quangbach1709.restapi.entity.Company;

public class CompanyMapper {
    public static CompanyDTO toDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setCode(company.getCode());
        dto.setAddress(company.getAddress());
        return dto;
    }

    public static Company toEntity(CompanyDTO dto) {
        Company company = new Company();
        company.setId(dto.getId());
        company.setName(dto.getName());
        company.setCode(dto.getCode());
        company.setAddress(dto.getAddress());
        return company;
    }
}

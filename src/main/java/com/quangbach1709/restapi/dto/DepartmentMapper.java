package com.quangbach1709.restapi.dto;

import com.quangbach1709.restapi.entity.Department;

public class DepartmentMapper {
    public static DepartmentDTO toDTO(Department department) {
        if (department == null) return null;

        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setCode(department.getCode());
        dto.setName(department.getName());
        dto.setCompanyId(department.getCompany().getId());

        if (department.getParent() != null) {
            dto.setParentId(department.getParent().getId());
        }

        return dto;
    }

    public static Department toEntity(DepartmentDTO dto) {
        if (dto == null) return null;

        Department department = new Department();
        department.setId(dto.getId());
        department.setCode(dto.getCode());
        department.setName(dto.getName());
        return department;
    }
}
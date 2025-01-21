package com.quangbach1709.restapi.service;

import com.quangbach1709.restapi.dto.DepartmentDTO;
import com.quangbach1709.restapi.dto.DepartmentMapper;
import com.quangbach1709.restapi.entity.Department;
import com.quangbach1709.restapi.repository.CompanyRepository;
import com.quangbach1709.restapi.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(DepartmentMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<DepartmentDTO> getDepartmentsByCompanyId(Long companyId) {
        return departmentRepository.findByCompanyId(companyId)
                .stream()
                .map(DepartmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DepartmentDTO getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(DepartmentMapper::toDTO)
                .orElse(null);
    }

    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        Department department = DepartmentMapper.toEntity(dto);
        department.setCompany(companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found")));

        if (dto.getParentId() != null) {
            department.setParent(departmentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent department not found")));
        }

        return DepartmentMapper.toDTO(departmentRepository.save(department));
    }

    public DepartmentDTO updateDepartment(Long id, DepartmentDTO dto) {
        return departmentRepository.findById(id)
                .map(department -> {
                    department.setName(dto.getName());
                    department.setCode(dto.getCode());

                    if (dto.getParentId() != null) {
                        department.setParent(departmentRepository.findById(dto.getParentId())
                                .orElseThrow(() -> new IllegalArgumentException("Parent department not found")));
                    }

                    department.setCompany(companyRepository.findById(dto.getCompanyId())
                            .orElseThrow(() -> new IllegalArgumentException("Company not found")));
                    return DepartmentMapper.toDTO(departmentRepository.save(department));
                })
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
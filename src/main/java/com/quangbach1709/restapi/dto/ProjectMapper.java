package com.quangbach1709.restapi.dto;

import com.quangbach1709.restapi.entity.Project;

import java.util.stream.Collectors;

public class ProjectMapper {
    public static ProjectDTO toDTO(Project project) {
        if (project == null) return null;

        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setCode(project.getCode());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());

        if (project.getCompany() != null) {
            dto.setCompanyId(project.getCompany().getId());
            dto.setCompanyName(project.getCompany().getName());
        }

        if (project.getPersons() != null) {
            dto.setPersons(project.getPersons().stream()
                    .map(PersonMapper::toDTO)
                    .collect(Collectors.toSet()));
        }

        return dto;
    }

    public static Project toEntity(ProjectDTO dto) {
        if (dto == null) return null;

        Project project = new Project();
        project.setId(dto.getId());
        project.setCode(dto.getCode());
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        return project;
    }
}
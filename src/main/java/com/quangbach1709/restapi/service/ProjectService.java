package com.quangbach1709.restapi.service;

import com.quangbach1709.restapi.dto.ProjectDTO;
import com.quangbach1709.restapi.dto.ProjectMapper;
import com.quangbach1709.restapi.entity.Project;
import com.quangbach1709.restapi.repository.CompanyRepository;
import com.quangbach1709.restapi.repository.PersonRepository;
import com.quangbach1709.restapi.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PersonRepository personRepository;

    public Page<ProjectDTO> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable)
                .map(ProjectMapper::toDTO);
    }

    public Page<ProjectDTO> getProjectsByCompany(Long companyId, Pageable pageable) {
        return projectRepository.findByCompanyId(companyId, pageable)
                .map(ProjectMapper::toDTO);
    }

    public ProjectDTO getProjectById(Long id) {
        return projectRepository.findById(id)
                .map(ProjectMapper::toDTO)
                .orElse(null);
    }

    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = ProjectMapper.toEntity(projectDTO);

        project.setCompany(companyRepository.findById(projectDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found")));

        if (projectDTO.getPersons() != null) {
            project.setPersons(projectDTO.getPersons().stream()
                    .map(personDTO -> personRepository.findById(personDTO.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Person not found")))
                    .collect(java.util.stream.Collectors.toSet()));
        }

        return ProjectMapper.toDTO(projectRepository.save(project));
    }

    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setName(projectDTO.getName());
                    project.setCode(projectDTO.getCode());
                    project.setDescription(projectDTO.getDescription());

                    project.setCompany(companyRepository.findById(projectDTO.getCompanyId())
                            .orElseThrow(() -> new IllegalArgumentException("Company not found")));

                    if (projectDTO.getPersons() != null) {
                        project.setPersons(projectDTO.getPersons().stream()
                                .map(personDTO -> personRepository.findById(personDTO.getId())
                                        .orElseThrow(() -> new IllegalArgumentException("Person not found")))
                                .collect(java.util.stream.Collectors.toSet()));
                    }

                    return ProjectMapper.toDTO(projectRepository.save(project));
                })
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
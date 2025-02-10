package com.quangbach1709.restapi.controller;

import com.quangbach1709.restapi.dto.ProjectDTO;
import com.quangbach1709.restapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public Page<ProjectDTO> getAllProjects(Pageable pageable) {
        return projectService.getAllProjects(pageable);
    }

    @GetMapping("/company/{companyId}")
    public Page<ProjectDTO> getProjectsByCompany(@PathVariable Long companyId, Pageable pageable) {
        return projectService.getProjectsByCompany(companyId, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        ProjectDTO projectDTO = projectService.getProjectById(id);
        return projectDTO != null ? ResponseEntity.ok(projectDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ProjectDTO createProject(@RequestBody ProjectDTO projectDTO) {
        return projectService.createProject(projectDTO);
    }

    @PostMapping("/paginate")
    public Page<ProjectDTO> getProjectsWithCustomPagination(@RequestBody Map<String, Integer> request) {
        int pageIndex = request.getOrDefault("pageIndex", 0);
        int pageSize = request.getOrDefault("pageSize", 20);
        return projectService.getAllProjects(PageRequest.of(pageIndex, pageSize));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.updateProject(id, projectDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
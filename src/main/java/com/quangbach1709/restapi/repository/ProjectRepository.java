package com.quangbach1709.restapi.repository;

import com.quangbach1709.restapi.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findAll(Pageable pageable);

    Page<Project> findByCompanyId(Long companyId, Pageable pageable);
}
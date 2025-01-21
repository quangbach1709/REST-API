package com.quangbach1709.restapi.repository;

import com.quangbach1709.restapi.entity.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {
    public static Specification<Task> hasCompanyId(Long companyId) {
        return (root, query, cb) -> {
            if (companyId == null) return null;
            return cb.equal(root.get("project").get("company").get("id"), companyId);
        };
    }

    public static Specification<Task> hasProjectId(Long projectId) {
        return (root, query, cb) -> {
            if (projectId == null) return null;
            return cb.equal(root.get("project").get("id"), projectId);
        };
    }

    public static Specification<Task> hasPersonId(Long personId) {
        return (root, query, cb) -> {
            if (personId == null) return null;
            return cb.equal(root.get("person").get("id"), personId);
        };
    }

    public static Specification<Task> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Task> hasPriority(String priority) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("priority"), priority);
    }

    public static Specification<Task> nameLike(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }
}
package com.quangbach1709.restapi.dto;

import com.quangbach1709.restapi.entity.Task;

public class TaskMapper {
    public static TaskDTO toDTO(Task task) {
        if (task == null) return null;

        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setStartTime(task.getStartTime());
        dto.setEndTime(task.getEndTime());
        dto.setPriority(task.getPriority());
        dto.setStatus(task.getStatus());

        if (task.getProject() != null) {
            dto.setProjectId(task.getProject().getId());
            dto.setProjectName(task.getProject().getName());
            if (task.getProject().getCompany() != null) {
                dto.setCompanyId(task.getProject().getCompany().getId());
                dto.setCompanyName(task.getProject().getCompany().getName());
            }
        }

        if (task.getPerson() != null) {
            dto.setPersonId(task.getPerson().getId());
            dto.setPersonName(task.getPerson().getFullName());
        }

        return dto;
    }

    public static Task toEntity(TaskDTO dto) {
        if (dto == null) return null;

        Task task = new Task();
        task.setId(dto.getId());
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setStartTime(dto.getStartTime());
        task.setEndTime(dto.getEndTime());
        task.setPriority(dto.getPriority());
        task.setStatus(dto.getStatus());
        return task;
    }
}
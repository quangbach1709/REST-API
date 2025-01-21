package com.quangbach1709.restapi.dto;

import com.quangbach1709.restapi.entity.Task;
import com.quangbach1709.restapi.entity.TaskPriority;
import com.quangbach1709.restapi.entity.TaskStatus;

public class TaskMapper {
    public static TaskDTO toDTO(Task task) {
        if (task == null) return null;

        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setStartTime(task.getStartTime());
        dto.setEndTime(task.getEndTime());
        dto.setPriority(task.getPriority().getValue());
        dto.setStatus(task.getStatus().getValue());

        // ... rest of the mapping code
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
        task.setPriority(TaskPriority.fromValue(dto.getPriority()));
        task.setStatus(TaskStatus.fromValue(dto.getStatus()));
        return task;
    }
}
package com.quangbach1709.restapi.service;

import com.quangbach1709.restapi.dto.TaskDTO;
import com.quangbach1709.restapi.dto.TaskMapper;
import com.quangbach1709.restapi.entity.Task;
import com.quangbach1709.restapi.entity.TaskPriority;
import com.quangbach1709.restapi.entity.TaskStatus;
import com.quangbach1709.restapi.repository.PersonRepository;
import com.quangbach1709.restapi.repository.ProjectRepository;
import com.quangbach1709.restapi.repository.TaskRepository;
import com.quangbach1709.restapi.repository.TaskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PersonRepository personRepository;

    public Page<TaskDTO> getTasks(
            Long companyId,
            Long projectId,
            Long personId,
            String status,
            String priority,
            String name,
            Pageable pageable) {

        Specification<Task> spec = Specification.where(null);

        if (companyId != null) {
            spec = spec.and(TaskSpecification.hasCompanyId(companyId));
        }
        if (projectId != null) {
            spec = spec.and(TaskSpecification.hasProjectId(projectId));
        }
        if (personId != null) {
            spec = spec.and(TaskSpecification.hasPersonId(personId));
        }
        if (status != null && !status.trim().isEmpty()) {
            spec = spec.and(TaskSpecification.hasStatus(status));
        }
        if (priority != null && !priority.trim().isEmpty()) {
            spec = spec.and(TaskSpecification.hasPriority(priority));
        }
        if (name != null && !name.trim().isEmpty()) {
            spec = spec.and(TaskSpecification.nameLike(name));
        }

        return taskRepository.findAll(spec, pageable).map(TaskMapper::toDTO);
    }

    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(TaskMapper::toDTO)
                .orElse(null);
    }

    private void validateTask(TaskDTO taskDTO) {
        try {
            TaskStatus.fromValue(taskDTO.getStatus());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status. Must be one of: New, In Progress, Completed, On Hold");
        }

        try {
            TaskPriority.fromValue(taskDTO.getPriority());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid priority. Must be one of: High, Medium, Low");
        }
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        validateTask(taskDTO);
        Task task = TaskMapper.toEntity(taskDTO);

        task.setStatus(TaskStatus.fromValue(taskDTO.getStatus()));
        task.setPriority(TaskPriority.fromValue(taskDTO.getPriority()));

        task.setProject(projectRepository.findById(taskDTO.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found")));

        task.setPerson(personRepository.findById(taskDTO.getPersonId())
                .orElseThrow(() -> new IllegalArgumentException("Person not found")));

        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        validateTask(taskDTO);
        return taskRepository.findById(id)
                .map(task -> {
                    task.setName(taskDTO.getName());
                    task.setDescription(taskDTO.getDescription());
                    task.setStartTime(taskDTO.getStartTime());
                    task.setEndTime(taskDTO.getEndTime());
                    task.setPriority(TaskPriority.fromValue(taskDTO.getPriority()));
                    task.setStatus(TaskStatus.fromValue(taskDTO.getStatus()));

                    task.setProject(projectRepository.findById(taskDTO.getProjectId())
                            .orElseThrow(() -> new IllegalArgumentException("Project not found")));

                    task.setPerson(personRepository.findById(taskDTO.getPersonId())
                            .orElseThrow(() -> new IllegalArgumentException("Person not found")));

                    return TaskMapper.toDTO(taskRepository.save(task));
                })
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
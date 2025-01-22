package com.quangbach1709.restapi.service;

import com.quangbach1709.restapi.dto.TaskDTO;
import com.quangbach1709.restapi.dto.TaskMapper;
import com.quangbach1709.restapi.entity.Task;
import com.quangbach1709.restapi.repository.PersonRepository;
import com.quangbach1709.restapi.repository.ProjectRepository;
import com.quangbach1709.restapi.repository.TaskRepository;
import com.quangbach1709.restapi.repository.TaskSpecification;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

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

    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = TaskMapper.toEntity(taskDTO);

        task.setProject(projectRepository.findById(taskDTO.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found")));

        task.setPerson(personRepository.findById(taskDTO.getPersonId())
                .orElseThrow(() -> new IllegalArgumentException("Person not found")));

        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setName(taskDTO.getName());
                    task.setDescription(taskDTO.getDescription());
                    task.setStartTime(taskDTO.getStartTime());
                    task.setEndTime(taskDTO.getEndTime());
                    task.setPriority(taskDTO.getPriority());
                    task.setStatus(taskDTO.getStatus());

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

    public ByteArrayInputStream exportTasksToExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Tasks");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Project");
            headerRow.createCell(1).setCellValue("Description");
            headerRow.createCell(2).setCellValue("Start Time");
            headerRow.createCell(3).setCellValue("End Time");
            headerRow.createCell(4).setCellValue("Priority");
            headerRow.createCell(5).setCellValue("Status");
            headerRow.createCell(6).setCellValue("Person");

            // Get all tasks
            List<Task> tasks = taskRepository.findAll();

            // Create data rows
            int rowNum = 1;
            for (Task task : tasks) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(task.getProject().getName());
                row.createCell(1).setCellValue(task.getDescription());
                row.createCell(2).setCellValue(task.getStartTime().toString());
                row.createCell(3).setCellValue(task.getEndTime().toString());
                row.createCell(4).setCellValue(task.getPriority());
                row.createCell(5).setCellValue(task.getStatus());
                row.createCell(6).setCellValue(task.getPerson().getFullName());
            }

            // Autosize columns
            for (int i = 0; i < 7; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export tasks to Excel", e);
        }
    }
}
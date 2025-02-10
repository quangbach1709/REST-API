package com.quangbach1709.restapi.controller;

import com.quangbach1709.restapi.dto.ProjectDTO;
import com.quangbach1709.restapi.dto.TaskDTO;
import com.quangbach1709.restapi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public Page<TaskDTO> getTasks(
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Long personId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String name,
            Pageable pageable) {
        return taskService.getTasks(companyId, projectId, personId, status, priority, name, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        TaskDTO taskDTO = taskService.getTaskById(id);
        return taskDTO != null ? ResponseEntity.ok(taskDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.createTask(taskDTO));
    }

    @PostMapping("/paginate")
    public Page<TaskDTO> getTasksWithCustomPagination(@RequestBody Map<String, Integer> request) {
        int pageIndex = request.getOrDefault("pageIndex", 0);
        int pageSize = request.getOrDefault("pageSize", 20);
        return taskService.getTasks(PageRequest.of(pageIndex, pageSize));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export/excel")
    public ResponseEntity<Resource> exportToExcel() {
        String filename = "tasks.xlsx";
        InputStreamResource file = new InputStreamResource(taskService.exportTasksToExcel());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }
}
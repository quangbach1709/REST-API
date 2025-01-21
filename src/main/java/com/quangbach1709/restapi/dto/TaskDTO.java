package com.quangbach1709.restapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String priority;
    private String status;
    private Long projectId;
    private String projectName;
    private Long personId;
    private String personName;
    private Long companyId;
    private String companyName;
}
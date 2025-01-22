package com.quangbach1709.restapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @NotBlank(message = "Priority cannot be blank")
    @Pattern(regexp = "^(High|Medium|Low)$",
            message = "Priority must be either 'High', 'Medium' or 'Low'")
    private String priority;

    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp = "^(New|In Progress|Completed|On Hold)$",
            message = "Status must be either 'New', 'In Progress', 'Completed' or 'On Hold'")
    private String status;
    private Long projectId;
    private String projectName;
    private Long personId;
    private String personName;
    private Long companyId;
    private String companyName;
}
// src/main/java/com/quangbach1709/restapi/entity/TaskPriority.java
package com.quangbach1709.restapi.entity;

public enum TaskPriority {
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low");

    private final String value;

    TaskPriority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TaskPriority fromValue(String value) {
        for (TaskPriority priority : TaskPriority.values()) {
            if (priority.getValue().equalsIgnoreCase(value)) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Invalid priority: " + value);
    }
}
// src/main/java/com/quangbach1709/restapi/entity/TaskStatus.java
package com.quangbach1709.restapi.entity;

public enum TaskStatus {
    NEW("New"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    ON_HOLD("On Hold");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TaskStatus fromValue(String value) {
        for (TaskStatus status : com.quangbach1709.restapi.entity.TaskStatus.values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + value);
    }
}
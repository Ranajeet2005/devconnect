
package com.ranajeet.devconnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskGenerationRequest {

    @NotBlank(message = "Project description is required")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    public TaskGenerationRequest() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


package com.ranajeet.devconnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProjectPlanRequest {

    @NotBlank(message = "Project idea is required")
    @Size(max = 2000, message = "Project idea must not exceed 2000 characters")
    private String idea;

    public ProjectPlanRequest() {
    }

    public String getIdea() {
        return idea;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }
}
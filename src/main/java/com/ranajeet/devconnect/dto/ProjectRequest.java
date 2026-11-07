
package com.ranajeet.devconnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProjectRequest {

    @NotBlank(message = "Project name is required")
    @Size(max = 150)
    private String name;

    @Size(max = 1000)
    private String description;

    @NotNull(message = "Owner ID is required")
    private Long ownerId;

    public ProjectRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}

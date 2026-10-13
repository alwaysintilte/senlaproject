package com.senla.project.models.DTO.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleRequest {
    @NotBlank(message = "Role name cannot be blank")
    @Size(min = 2, max = 30, message = "Role name must be between 2 and 30 characters")
    private String name;

    public RoleRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

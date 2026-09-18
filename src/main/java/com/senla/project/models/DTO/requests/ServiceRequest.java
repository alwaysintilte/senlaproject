package com.senla.project.models.DTO.requests;

import jakarta.validation.constraints.*;

public class ServiceRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    @Size(max = 1000, message = "Description must be at most 10000 characters")
    private String description;
    @NotNull(message = "Duration cannot be null")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 1440, message = "Duration must be at most 1440 minutes (24 hours)")
    private Integer duration;
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be positive")
    @DecimalMax(value = "10000", message = "Price must be at most 10000")
    private Double price;
    @NotBlank(message = "Category cannot be blank")
    @Pattern(
            regexp = "HAIRCUT|SHAVE|BEARD|MASSAGE|COLORING|STYLING|OTHER",
            message = "Invalid category"
    )
    private String category;

    public ServiceRequest() {}

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

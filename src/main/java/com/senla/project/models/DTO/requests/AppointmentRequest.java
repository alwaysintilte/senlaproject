package com.senla.project.models.DTO.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentRequest {
    @NotNull(message = "Barber id cannot be null")
    private Long barberId;
    @Size(max = 10000, message = "Notes must be at most 10000 characters")
    private String notes;
    @NotNull(message = "Appointment date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime appointmentDate;
    @NotEmpty(message = "At least one service must be selected")
    private List<Long> serviceIds;

    public AppointmentRequest() {}

    public Long getBarberId() {
        return barberId;
    }

    public void setBarberId(Long barberId) {
        this.barberId = barberId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public List<Long> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<Long> serviceIds) {
        this.serviceIds = serviceIds;
    }
}

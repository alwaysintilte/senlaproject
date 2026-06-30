package com.senla.project.models.DTO.requests;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentRequest {
    private Long barberId;

    private String notes;

    private LocalDateTime appointmentDate;

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

package com.senla.project.models.DTO.responses;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentResponse {
    private Long id;

    private LocalDateTime appointmentDate;

    private String status;

    private String notes;

    private BarberResponse barberResponse;

    private List<ServiceResponse> serviceResponses;

    private Double totalPrice;

    public AppointmentResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BarberResponse getBarberInfoResponse() {
        return barberResponse;
    }

    public void setBarberInfoResponse(BarberResponse barberResponse) {
        this.barberResponse = barberResponse;
    }

    public List<ServiceResponse> getServiceResponses() {
        return serviceResponses;
    }

    public void setServiceResponses(List<ServiceResponse> serviceResponses) {
        this.serviceResponses = serviceResponses;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

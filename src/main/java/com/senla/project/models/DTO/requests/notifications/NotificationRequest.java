package com.senla.project.models.DTO.requests.notifications;

import java.time.LocalDateTime;
import java.util.List;

public class NotificationRequest {
    private LocalDateTime appointmentDate;
    private Long clientId;
    private String clientFirstName;
    private String clientLastName;
    private String clientPhone;
    private String clientEmail;
    private Long barberId;
    private String barberFirstName;
    private String barberLastName;
    private String notificationChannel;
    private String notificationSubject;
    private List<ServiceInfo> services;

    public NotificationRequest() {
    }

    public NotificationRequest(LocalDateTime appointmentDate, Long clientId, String clientFirstName, String clientLastName, String clientPhone, String clientEmail, Long barberId, String barberFirstName, String barberLastName, String notificationChannel, String notificationSubject, List<ServiceInfo> services) {
        this.appointmentDate = appointmentDate;
        this.clientId = clientId;
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.clientPhone = clientPhone;
        this.clientEmail = clientEmail;
        this.barberId = barberId;
        this.barberFirstName = barberFirstName;
        this.barberLastName = barberLastName;
        this.notificationChannel = notificationChannel;
        this.notificationSubject = notificationSubject;
        this.services = services;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public Long getBarberId() {
        return barberId;
    }

    public void setBarberId(Long barberId) {
        this.barberId = barberId;
    }

    public String getBarberFirstName() {
        return barberFirstName;
    }

    public void setBarberFirstName(String barberFirstName) {
        this.barberFirstName = barberFirstName;
    }

    public String getBarberLastName() {
        return barberLastName;
    }

    public void setBarberLastName(String barberLastName) {
        this.barberLastName = barberLastName;
    }

    public String getNotificationChannel() {
        return notificationChannel;
    }

    public void setNotificationChannel(String notificationChannel) {
        this.notificationChannel = notificationChannel;
    }

    public String getNotificationSubject() {
        return notificationSubject;
    }

    public void setNotificationSubject(String notificationSubject) {
        this.notificationSubject = notificationSubject;
    }

    public List<ServiceInfo> getServices() {
        return services;
    }

    public void setServices(List<ServiceInfo> services) {
        this.services = services;
    }
}

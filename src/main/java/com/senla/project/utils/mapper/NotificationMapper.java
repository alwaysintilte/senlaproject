package com.senla.project.utils.mapper;

import com.senla.project.models.Appointment;
import com.senla.project.models.Barber;
import com.senla.project.models.Client;
import com.senla.project.models.DTO.requests.notifications.NotificationRequest;
import com.senla.project.models.DTO.requests.notifications.ServiceInfo;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {
    public NotificationRequest mapToNotificationRequest(Appointment appointment, Client client, Barber barber, String subject){
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setAppointmentDate(appointment.getAppointmentDate());
        notificationRequest.setClientId(client.getId());
        notificationRequest.setClientFirstName(client.getFirstName());
        notificationRequest.setClientLastName(client.getLastName());
        notificationRequest.setClientPhone(client.getPhone());
        notificationRequest.setClientEmail(client.getEmail());
        notificationRequest.setBarberId(barber.getId());
        notificationRequest.setBarberFirstName(barber.getFirstName());
        notificationRequest.setBarberLastName(barber.getLastName());
        if(client.getEmail()!=null){
            notificationRequest.setNotificationChannel("EMAIL");
        } else {
            notificationRequest.setNotificationChannel("SMS");
        }
        notificationRequest.setNotificationSubject(subject);
        List<ServiceInfo> services = appointment.getServices().stream()
                .map(s -> new ServiceInfo(s.getName(), s.getDuration(), s.getPrice()))
                .collect(Collectors.toList());
        notificationRequest.setServices(services);
        return notificationRequest;
    }
}

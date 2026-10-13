package com.senla.project.services;

import com.senla.project.exceptions.InvalidRequestDataException;
import com.senla.project.exceptions.NotFoundException;
import com.senla.project.kafka.NotificationProducer;
import com.senla.project.models.*;
import com.senla.project.models.DTO.requests.AppointmentRequest;
import com.senla.project.models.DTO.requests.notifications.NotificationRequest;
import com.senla.project.models.DTO.responses.AppointmentResponse;
import com.senla.project.models.enums.AppointmentStatus;
import com.senla.project.utils.AuditingService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import com.senla.project.repositories.*;
import com.senla.project.utils.mapper.AppointmentMapper;
import com.senla.project.utils.mapper.NotificationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final BarberRepository barberRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentMapper appointmentMapper;
    private final NotificationProducer notificationProducer;
    private final NotificationMapper notificationMapper;
    private final AuditingService auditingService;

    public AppointmentService(AppointmentRepository appointmentRepository, ClientRepository clientRepository, BarberRepository barberRepository, ServiceRepository serviceRepository, AppointmentMapper appointmentMapper, NotificationProducer notificationProducer, NotificationMapper notificationMapper, AuditingService auditingService) {
        this.appointmentRepository = appointmentRepository;
        this.clientRepository = clientRepository;
        this.barberRepository = barberRepository;
        this.serviceRepository = serviceRepository;
        this.appointmentMapper = appointmentMapper;
        this.notificationProducer = notificationProducer;
        this.notificationMapper = notificationMapper;
        this.auditingService = auditingService;
    }

    @Transactional
    public AppointmentResponse createAppointment(Long clientId, AppointmentRequest request) {
        User auditingUser = auditingService.getCurrentAuditor();
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client"));
        Barber barber = barberRepository.findById(request.getBarberId())
                .orElseThrow(() -> new NotFoundException("Barber"));
        List<Service> services = serviceRepository.findAllById(request.getServiceIds());
        if (services.isEmpty()) {
            throw new NotFoundException("Services");
        }
        Appointment appointment = appointmentMapper.toEntity(request);
        appointment.setClient(client);
        appointment.setBarber(barber);
        appointment.setServices(services);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setCreatedBy(auditingUser);
        appointment.setUpdatedBy(auditingUser);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        NotificationRequest notificationRequest = notificationMapper.mapToNotificationRequest(appointment, client, barber, "Barbershop Appointment");
        notificationProducer.sendNotificationRequest(notificationRequest);
        return appointmentMapper.toDto(savedAppointment);
    }
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment"));
        return appointmentMapper.toDto(appointment);
    }
    public Page<AppointmentResponse> getAppointmentsByBarberId(Long barberId, Pageable pageable) {
        if (!barberRepository.existsById(barberId)) {
            throw new NotFoundException("Barber");
        }
        Page<Appointment> appointmentsPage = appointmentRepository.findByBarberId(barberId, pageable);
        List<Long> ids = appointmentsPage.getContent().stream().map(appointment -> appointment.getId()).collect(Collectors.toList());
        if (!ids.isEmpty()) {
            List<Appointment> withServices = appointmentRepository.findAllWithServicesByIdIn(ids);
            Map<Long, Appointment> map = withServices.stream().collect(Collectors.toMap(appointment -> appointment.getId(), a -> a));
            appointmentsPage = appointmentsPage.map(appointment -> map.getOrDefault(appointment.getId(), appointment));
        }
        return appointmentsPage.map(appointment -> appointmentMapper.toDto(appointment));
    }
    public Page<AppointmentResponse> getAppointmentsByClientId(Long clientId, Pageable pageable) {
        if (!clientRepository.existsById(clientId)) {
            throw new NotFoundException("Client");
        }
        Page<Appointment> appointmentsPage = appointmentRepository.findByClientId(clientId, pageable);
        List<Long> ids = appointmentsPage.getContent().stream().map(appointment -> appointment.getId()).collect(Collectors.toList());
        if (!ids.isEmpty()) {
            List<Appointment> withServices = appointmentRepository.findAllWithServicesByIdIn(ids);
            Map<Long, Appointment> map = withServices.stream().collect(Collectors.toMap(appointment -> appointment.getId(), a -> a));
            appointmentsPage = appointmentsPage.map(appointment -> map.getOrDefault(appointment.getId(), appointment));
        }
        return appointmentsPage.map(appointment -> appointmentMapper.toDto(appointment));
    }
    @Transactional
    public AppointmentResponse updateAppointmentStatus(Long id, String status) {
        User auditingUser = auditingService.getCurrentAuditor();
        AppointmentStatus appointmentStatus;
        try {
            appointmentStatus = AppointmentStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestDataException("Invalid appointment status", HttpStatus.BAD_REQUEST);
        }
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment"));
        appointment.setStatus(appointmentStatus);
        appointment.setUpdatedBy(auditingUser);
        Appointment updated = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(updated);
    }
    @Transactional
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        User auditingUser = auditingService.getCurrentAuditor();
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment"));
        appointmentMapper.updateEntity(request, existingAppointment);
        if (request.getBarberId() != null) {
            Barber barber = barberRepository.findById(request.getBarberId())
                    .orElseThrow(() -> new NotFoundException("Barber"));
            existingAppointment.setBarber(barber);
        }
        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
            List<Service> services = serviceRepository.findAllById(request.getServiceIds());
            if (services.isEmpty()) {
                throw new NotFoundException("Services");
            }
            existingAppointment.setServices(services);
        }
        existingAppointment.setUpdatedBy(auditingUser);
        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
        return appointmentMapper.toDto(updatedAppointment);
    }
    @Transactional
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new NotFoundException("Appointment");
        }
        appointmentRepository.deleteById(id);
    }
}
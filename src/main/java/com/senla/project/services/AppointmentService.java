package com.senla.project.services;

import com.senla.project.models.*;
import com.senla.project.models.DTO.requests.AppointmentRequest;
import com.senla.project.models.DTO.responses.AppointmentResponse;
import com.senla.project.models.enums.AppointmentStatus;
import com.senla.project.models.enums.ServiceCategory;
import com.senla.project.repositories.*;
import com.senla.project.utils.mapper.AppointmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final BarberRepository barberRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentService(AppointmentRepository appointmentRepository, ClientRepository clientRepository, BarberRepository barberRepository, ServiceRepository serviceRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.clientRepository = clientRepository;
        this.barberRepository = barberRepository;
        this.serviceRepository = serviceRepository;
        this.appointmentMapper = appointmentMapper;
    }

    public AppointmentResponse createAppointment(Long clientId, AppointmentRequest request) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));
        Barber barber = barberRepository.findById(request.getBarberId())
                .orElseThrow(() -> new RuntimeException("Барбер не найден"));
        List<Service> services = serviceRepository.findAllById(request.getServiceIds());
        if (services.isEmpty()) {
            throw new RuntimeException("Выбранные услуги не найдены");
        }
        Appointment appointment = appointmentMapper.toEntity(request);
        appointment.setClient(client);
        appointment.setBarber(barber);
        appointment.setServices(services);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setCreatedBy(client);
        appointment.setUpdatedBy(client);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(savedAppointment);
    }
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Посещение с id " + id + " не найдено"));
        return appointmentMapper.toDto(appointment);
    }
    public Page<AppointmentResponse> getAppointmentsByBarberId(Long barberId, Pageable pageable) {
        if (!barberRepository.existsById(barberId)) {
            throw new RuntimeException("Барбер с id " + barberId + " не найден");
        }
        Page<Appointment> appointmentsPage = appointmentRepository.findByBarberId(barberId, pageable);
        return appointmentsPage.map(appointment -> appointmentMapper.toDto(appointment));
    }
    public Page<AppointmentResponse> getAppointmentsByClientId(Long clientId, Pageable pageable) {
        if (!clientRepository.existsById(clientId)) {
            throw new RuntimeException("Клиент с id " + clientId + " не найден");
        }
        Page<Appointment> appointmentsPage = appointmentRepository.findByClientId(clientId, pageable);
        return appointmentsPage.map(appointment -> appointmentMapper.toDto(appointment));
    }
    public AppointmentResponse updateAppointmentStatus(Long id, String status) {
        AppointmentStatus appointmentStatus;
        try {
            appointmentStatus = AppointmentStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Такого статуса не существует");
        }
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Посещение с id " + id + " не найдено"));
        appointment.setStatus(appointmentStatus);
        Appointment updated = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(updated);
    }
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Посещение с id " + id + " не найдено"));
        appointmentMapper.updateEntity(request, existingAppointment);
        if (request.getBarberId() != null) {
            Barber barber = barberRepository.findById(request.getBarberId())
                    .orElseThrow(() -> new RuntimeException("Барбер не найден"));
            existingAppointment.setBarber(barber);
        }
        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
            List<Service> services = serviceRepository.findAllById(request.getServiceIds());
            if (services.isEmpty()) {
                throw new RuntimeException("Выбранные услуги не найдены");
            }
            existingAppointment.setServices(services);
        }
        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
        return appointmentMapper.toDto(updatedAppointment);
    }
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Посещение с id " + id + " не найдено");
        }
        appointmentRepository.deleteById(id);
    }
}
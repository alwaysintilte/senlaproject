package com.senla.project.controllers;

import com.senla.project.models.DTO.requests.AppointmentRequest;
import com.senla.project.models.DTO.responses.AppointmentResponse;
import com.senla.project.models.enums.AppointmentStatus;
import com.senla.project.services.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public AppointmentResponse createAppointment(
            @RequestParam Long clientId,
            @RequestBody AppointmentRequest request) {
        return appointmentService.createAppointment(clientId, request);
    }

    @GetMapping("/{id}")
    public AppointmentResponse getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @GetMapping("/client/{clientId}")
    public Page<AppointmentResponse> getAppointmentsByClientId(@PathVariable Long clientId, @PageableDefault(size = 10) Pageable pageable) {
        return appointmentService.getAppointmentsByClientId(clientId, pageable);
    }

    @GetMapping("/barber/{barberId}")
    public Page<AppointmentResponse> getAppointmentsByBarberId(@PathVariable Long barberId, @PageableDefault(size = 10) Pageable pageable) {
        return appointmentService.getAppointmentsByBarberId(barberId, pageable);
    }

    @PutMapping("/{id}")
    public AppointmentResponse updateAppointment(@PathVariable Long id, @RequestBody AppointmentRequest request) {
        return appointmentService.updateAppointment(id, request);
    }

    @PutMapping("/{id}/status")
    public AppointmentResponse updateAppointmentStatus(@PathVariable Long id, @RequestParam String status) {
        return appointmentService.updateAppointmentStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }
}

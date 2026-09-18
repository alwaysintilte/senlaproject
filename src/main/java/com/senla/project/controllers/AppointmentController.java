package com.senla.project.controllers;

import com.senla.project.models.DTO.requests.AppointmentRequest;
import com.senla.project.models.DTO.responses.AppointmentResponse;
import com.senla.project.models.enums.AppointmentStatus;
import com.senla.project.services.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestParam Long clientId, @Valid @RequestBody AppointmentRequest request) {
        return new ResponseEntity<>(appointmentService.createAppointment(clientId, request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id) {
        return new ResponseEntity<>(appointmentService.getAppointmentById(id), HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<Page<AppointmentResponse>> getAppointmentsByClientId(@PathVariable Long clientId, @PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(appointmentService.getAppointmentsByClientId(clientId, pageable), HttpStatus.OK);
    }

    @GetMapping("/barber/{barberId}")
    public ResponseEntity<Page<AppointmentResponse>> getAppointmentsByBarberId(@PathVariable Long barberId, @PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(appointmentService.getAppointmentsByBarberId(barberId, pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentRequest request) {
        return new ResponseEntity<>(appointmentService.updateAppointment(id, request), HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AppointmentResponse> updateAppointmentStatus(@PathVariable Long id, @RequestParam String status) {
        return new ResponseEntity<>(appointmentService.updateAppointmentStatus(id, status), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

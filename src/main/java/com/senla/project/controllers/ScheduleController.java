package com.senla.project.controllers;

import com.senla.project.models.DTO.requests.ScheduleRequest;
import com.senla.project.models.DTO.responses.ScheduleResponse;
import com.senla.project.services.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(@RequestParam Long barberId, @Valid @RequestBody ScheduleRequest request
    ) {
        return new ResponseEntity<>(scheduleService.createSchedule(barberId, request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> getScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.getScheduleById(id), HttpStatus.OK);
    }

    @GetMapping("/barber/{barberId}/date")
    public ResponseEntity<List<ScheduleResponse>> getScheduleByBarberIdAndWorkDate(
            @PathVariable Long barberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return new ResponseEntity<>(scheduleService.getScheduleByBarberIdAndWorkDate(barberId, date), HttpStatus.OK);
    }

    @GetMapping("/barber/{barberId}/dates")
    public ResponseEntity<List<ScheduleResponse>> getScheduleByBarberIdAndWorkDateBetween(
            @PathVariable Long barberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return new ResponseEntity<>(scheduleService.getScheduleByBarberIdAndWorkDateBetween(barberId, startDate, endDate), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponse> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleRequest request) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

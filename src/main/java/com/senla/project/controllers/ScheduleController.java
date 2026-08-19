package com.senla.project.controllers;

import com.senla.project.models.DTO.requests.ScheduleRequest;
import com.senla.project.models.DTO.responses.ScheduleResponse;
import com.senla.project.services.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
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
    public ScheduleResponse createSchedule(
            @RequestParam Long barberId,
            @RequestBody ScheduleRequest request
    ) {
        return scheduleService.createSchedule(barberId, request);
    }

    @GetMapping("/{id}")
    public ScheduleResponse getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    @GetMapping("/barber/{barberId}/date")
    public List<ScheduleResponse> getScheduleByBarberIdAndWorkDate(
            @PathVariable Long barberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return scheduleService.getScheduleByBarberIdAndWorkDate(barberId, date);
    }

    @GetMapping("/barber/{barberId}/dates")
    public List<ScheduleResponse> getScheduleByBarberIdAndWorkDateBetween(
            @PathVariable Long barberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return scheduleService.getScheduleByBarberIdAndWorkDateBetween(barberId, startDate, endDate);
    }

    @PutMapping("/{id}")
    public ScheduleResponse updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequest request) {
        return scheduleService.updateSchedule(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }
}

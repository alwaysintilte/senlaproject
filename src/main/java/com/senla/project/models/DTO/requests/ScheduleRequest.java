package com.senla.project.models.DTO.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleRequest {
    @NotNull(message = "Work date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate workDate;
    @NotNull(message = "Start time cannot be null")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @NotNull(message = "End time cannot be null")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    public ScheduleRequest() {}

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}

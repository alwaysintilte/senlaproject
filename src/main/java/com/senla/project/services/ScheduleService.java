package com.senla.project.services;

import com.senla.project.models.Barber;
import com.senla.project.models.DTO.requests.ScheduleRequest;
import com.senla.project.models.DTO.responses.ScheduleResponse;
import com.senla.project.models.Schedule;
import com.senla.project.repositories.BarberRepository;
import com.senla.project.repositories.ScheduleRepository;
import com.senla.project.utils.mapper.ScheduleMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final BarberRepository barberRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleService(ScheduleRepository scheduleRepository, BarberRepository barberRepository, ScheduleMapper scheduleMapper) {
        this.scheduleRepository = scheduleRepository;
        this.barberRepository = barberRepository;
        this.scheduleMapper = scheduleMapper;
    }

    public ScheduleResponse createSchedule(Long barberId, ScheduleRequest request) {
        Barber barber = barberRepository.findById(barberId)
                .orElseThrow(() -> new RuntimeException("Барбер с id " + barberId + " не найден"));
        Schedule schedule = scheduleMapper.toEntity(request);
        schedule.setBarber(barber);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toDto(savedSchedule);
    }
    public ScheduleResponse getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Расписание с id " + id + " не найдено"));
        return scheduleMapper.toDto(schedule);
    }
    public List<ScheduleResponse> getScheduleByBarberIdAndWorkDate(Long barberId, LocalDate workDate) {
        if (!barberRepository.existsById(barberId)) {
            throw new RuntimeException("Барбер с ID " + barberId + " не найден");
        }
        List<Schedule> schedules = scheduleRepository.findByBarberIdAndWorkDate(barberId, workDate);
        return scheduleMapper.toDtoList(schedules);
    }
    public List<ScheduleResponse> getScheduleByBarberIdAndWorkDateBetween(Long barberId, LocalDate startDate, LocalDate endDate) {
        if (!barberRepository.existsById(barberId)) {
            throw new RuntimeException("Барбер с ID " + barberId + " не найден");
        }
        List<Schedule> schedules = scheduleRepository.findByBarberIdAndWorkDateBetween(barberId, startDate, endDate);
        return scheduleMapper.toDtoList(schedules);
    }
    public ScheduleResponse updateSchedule(Long id, ScheduleRequest request) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Расписание с id " + id + " не найдено"));
        scheduleMapper.updateEntity(request, existingSchedule);
        Schedule updatedSchedule = scheduleRepository.save(existingSchedule);
        return scheduleMapper.toDto(updatedSchedule);
    }
    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Расписание с id " + id + " не найдено");
        }
        scheduleRepository.deleteById(id);
    }
}

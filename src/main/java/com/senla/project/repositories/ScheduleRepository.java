package com.senla.project.repositories;

import com.senla.project.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByBarberIdAndWorkDate(Long barberId, LocalDate workDate);

    List<Schedule> findByBarberIdAndWorkDateBetween(Long barberId, LocalDate startDate, LocalDate endDate);
}

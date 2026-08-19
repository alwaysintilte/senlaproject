package com.senla.project.repositories;

import com.senla.project.models.Appointment;
import com.senla.project.models.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findByClientId(Long clientId, Pageable pageable);
    Page<Appointment> findByBarberId(Long barberId, Pageable pageable);
}

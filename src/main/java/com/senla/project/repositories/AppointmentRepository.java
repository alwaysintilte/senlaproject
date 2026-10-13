package com.senla.project.repositories;

import com.senla.project.models.Appointment;
import com.senla.project.models.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findByClientId(Long clientId, Pageable pageable);
    Page<Appointment> findByBarberId(Long barberId, Pageable pageable);
    @EntityGraph(attributePaths = {"services"})
    @Query("SELECT a FROM Appointment a WHERE a.id IN :ids")
    List<Appointment> findAllWithServicesByIdIn(@Param("ids") List<Long> ids);
}

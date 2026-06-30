package com.senla.project.repositories;

import com.senla.project.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Page<Client> findDistinctByAppointmentsBarberId(Long barberId, Pageable pageable);
}

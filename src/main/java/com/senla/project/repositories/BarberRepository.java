package com.senla.project.repositories;

import com.senla.project.models.Barber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {
    Page<Barber> findBySpecialty(String specialty, Pageable pageable);
}

package com.senla.project.repositories;

import com.senla.project.models.Service;
import com.senla.project.models.enums.ServiceCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Page<Service> findByCategory(ServiceCategory category, Pageable pageable);
}

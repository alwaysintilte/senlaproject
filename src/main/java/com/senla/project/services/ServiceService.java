package com.senla.project.services;

import com.senla.project.models.Service;
import com.senla.project.models.DTO.requests.ServiceRequest;
import com.senla.project.models.DTO.responses.ServiceResponse;
import com.senla.project.models.enums.ServiceCategory;
import com.senla.project.repositories.ServiceRepository;
import com.senla.project.utils.mapper.ServiceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public ServiceService(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    public ServiceResponse createService(ServiceRequest request) {
        Service service = serviceMapper.toEntity(request);
        Service savedService = serviceRepository.save(service);
        return serviceMapper.toDto(savedService);
    }
    public ServiceResponse getServiceById(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Услуга с id " + id + " не найдена"));
        return serviceMapper.toDto(service);
    }
    public Page<ServiceResponse> getServicesByCategory(String category, Pageable pageable) {
        ServiceCategory categoryEnum;
        try {
            categoryEnum = ServiceCategory.valueOf(category);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Такой категории не существует");
        }
        Page<Service> servicesPage = serviceRepository.findByCategory(categoryEnum, pageable);
        return servicesPage.map(service -> serviceMapper.toDto(service));
    }
    public Page<ServiceResponse> getAllServices(Pageable pageable) {
        Page<Service> servicesPage = serviceRepository.findAll(pageable);
        return servicesPage.map(service -> serviceMapper.toDto(service));
    }
    public ServiceResponse updateService(Long id, ServiceRequest request) {
        Service existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Услуга с id " + id + " не найдена"));
        serviceMapper.updateEntity(request, existingService);
        Service updatedService = serviceRepository.save(existingService);
        return serviceMapper.toDto(updatedService);
    }
    public void deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new RuntimeException("Услуга с id " + id + " не найдена");
        }
        serviceRepository.deleteById(id);
    }
}


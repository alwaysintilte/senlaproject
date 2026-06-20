package com.senla.project.controllers;

import com.senla.project.models.DTO.requests.ServiceRequest;
import com.senla.project.models.DTO.responses.ServiceResponse;
import com.senla.project.services.ServiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping
    public ServiceResponse createService(@RequestBody ServiceRequest request) {
        return serviceService.createService(request);
    }

    @GetMapping("/{id}")
    public ServiceResponse getServiceById(@PathVariable Long id) {
        return serviceService.getServiceById(id);
    }

    @GetMapping
    public Page<ServiceResponse> getAllServices(@PageableDefault(size = 10) Pageable pageable) {
        return serviceService.getAllServices(pageable);
    }

    @GetMapping("/category/{category}")
    public Page<ServiceResponse> getServicesByCategory(@PathVariable String category, @PageableDefault(size = 10) Pageable pageable) {
        return serviceService.getServicesByCategory(category, pageable);
    }

    @PutMapping("/{id}")
    public ServiceResponse updateService(@PathVariable Long id, @RequestBody ServiceRequest request) {
        return serviceService.updateService(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
    }
}

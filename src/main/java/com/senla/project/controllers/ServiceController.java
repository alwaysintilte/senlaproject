package com.senla.project.controllers;

import com.senla.project.models.DTO.requests.ServiceRequest;
import com.senla.project.models.DTO.responses.ServiceResponse;
import com.senla.project.services.ServiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping
    public ResponseEntity createService(@RequestBody ServiceRequest request) {
        return new ResponseEntity<>(serviceService.createService(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getServiceById(@PathVariable Long id) {
        return new ResponseEntity<>(serviceService.getServiceById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllServices(@PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(serviceService.getAllServices(pageable), HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity getServicesByCategory(@PathVariable String category, @PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(serviceService.getServicesByCategory(category, pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateService(@PathVariable Long id, @RequestBody ServiceRequest request) {
        return new ResponseEntity<>(serviceService.updateService(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}

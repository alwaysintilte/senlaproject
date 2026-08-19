package com.senla.project.controllers;

import com.senla.project.models.DTO.requests.BarberRequest;
import com.senla.project.models.DTO.responses.BarberResponse;
import com.senla.project.services.BarberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/barbers")
public class BarberController {

    private final BarberService barberService;

    public BarberController(BarberService barberService) {
        this.barberService = barberService;
    }

    @PostMapping
    public BarberResponse createBarber(@RequestBody BarberRequest request) {
        return barberService.createBarber(request);
    }

    @GetMapping("/{id}")
    public BarberResponse getBarberById(@PathVariable Long id) {
        return barberService.getBarberById(id);
    }

    @GetMapping
    public Page<BarberResponse> getAllBarbers(@PageableDefault(size = 10) Pageable pageable) {
        return barberService.getAllBarbers(pageable);
    }

    @GetMapping("/specialty")
    public Page<BarberResponse> getBarbersBySpecialty(@RequestParam String specialty, @PageableDefault(size = 10) Pageable pageable) {
        return barberService.getBarbersBySpecialty(specialty, pageable);
    }

    @PutMapping("/{id}")
    public BarberResponse updateBarber(@PathVariable Long id, @RequestBody BarberRequest request) {
        return barberService.updateBarber(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteBarber(@PathVariable Long id) {
        barberService.deleteBarber(id);
    }
}

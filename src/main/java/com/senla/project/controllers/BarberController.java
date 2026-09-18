package com.senla.project.controllers;

import com.senla.project.models.DTO.requests.BarberRequest;
import com.senla.project.models.DTO.responses.BarberResponse;
import com.senla.project.services.BarberService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/barbers")
public class BarberController {

    private final BarberService barberService;

    public BarberController(BarberService barberService) {
        this.barberService = barberService;
    }

    @PostMapping
    public ResponseEntity<BarberResponse> createBarber(@Valid @RequestBody BarberRequest request) {
        return new ResponseEntity<>(barberService.createBarber(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarberResponse> getBarberById(@PathVariable Long id) {
        return new ResponseEntity<>(barberService.getBarberById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<BarberResponse>> getAllBarbers(@PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(barberService.getAllBarbers(pageable), HttpStatus.OK);
    }

    @GetMapping("/specialty")
    public ResponseEntity<Page<BarberResponse>> getBarbersBySpecialty(@RequestParam String specialty, @PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(barberService.getBarbersBySpecialty(specialty, pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarberResponse> updateBarber(@PathVariable Long id, @Valid @RequestBody BarberRequest request) {
        return new ResponseEntity<>(barberService.updateBarber(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBarber(@PathVariable Long id) {
        barberService.deleteBarber(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

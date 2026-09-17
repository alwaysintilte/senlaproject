package com.senla.project.controllers;

import com.senla.project.models.DTO.requests.BarberRequest;
import com.senla.project.models.DTO.responses.BarberResponse;
import com.senla.project.services.BarberService;
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
    public ResponseEntity createBarber(@RequestBody BarberRequest request) {
        return new ResponseEntity<>(barberService.createBarber(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getBarberById(@PathVariable Long id) {
        return new ResponseEntity<>(barberService.getBarberById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllBarbers(@PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(barberService.getAllBarbers(pageable), HttpStatus.OK);
    }

    @GetMapping("/specialty")
    public ResponseEntity getBarbersBySpecialty(@RequestParam String specialty, @PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(barberService.getBarbersBySpecialty(specialty, pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBarber(@PathVariable Long id, @RequestBody BarberRequest request) {
        return new ResponseEntity<>(barberService.updateBarber(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBarber(@PathVariable Long id) {
        barberService.deleteBarber(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}

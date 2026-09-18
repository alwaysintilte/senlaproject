package com.senla.project.controllers;

import com.senla.project.models.DTO.requests.ClientRequest;
import com.senla.project.models.DTO.responses.ClientResponse;
import com.senla.project.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest request) {
        return new ResponseEntity<>(clientService.createClient(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.getClientById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponse>> getAllClients(@PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(clientService.getAllClients(pageable), HttpStatus.OK);
    }

    @GetMapping("/barber/{barberId}")
    public ResponseEntity<Page<ClientResponse>> getClientsByBarberId(@PathVariable Long barberId, @PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(clientService.getClientsDistinctByAppointmentsBarberId(barberId, pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Long id, @Valid @RequestBody ClientRequest request) {
        return new ResponseEntity<>(clientService.updateClient(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

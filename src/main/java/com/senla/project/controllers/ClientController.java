package com.senla.project.controllers;

import com.senla.project.models.DTO.requests.ClientRequest;
import com.senla.project.models.DTO.responses.ClientResponse;
import com.senla.project.services.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ClientResponse createClient(@RequestBody ClientRequest request) {
        return clientService.createClient(request);
    }

    @GetMapping("/{id}")
    public ClientResponse getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @GetMapping
    public Page<ClientResponse> getAllClients(@PageableDefault(size = 10) Pageable pageable) {
        return clientService.getAllClients(pageable);
    }

    @GetMapping("/barber/{barberId}")
    public Page<ClientResponse> getClientsByBarberId(@PathVariable Long barberId, @PageableDefault(size = 10) Pageable pageable) {
        return clientService.getClientsDistinctByAppointmentsBarberId(barberId, pageable);
    }

    @PutMapping("/{id}")
    public ClientResponse updateClient(@PathVariable Long id, @RequestBody ClientRequest request) {
        return clientService.updateClient(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}

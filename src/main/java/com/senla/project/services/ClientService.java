package com.senla.project.services;

import com.senla.project.models.Client;
import com.senla.project.models.DTO.responses.ClientResponse;
import com.senla.project.models.Role;
import com.senla.project.models.DTO.requests.ClientRequest;
import com.senla.project.repositories.BarberRepository;
import com.senla.project.repositories.ClientRepository;
import com.senla.project.repositories.RoleRepository;
import com.senla.project.repositories.UserRepository;
import com.senla.project.utils.mapper.ClientMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BarberRepository barberRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, UserRepository userRepository, RoleRepository roleRepository, BarberRepository barberRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.barberRepository = barberRepository;
        this.clientMapper = clientMapper;
    }

    public ClientResponse createClient(ClientRequest request) {
        if(userRepository.existsByEmail(request.getEmail()) || userRepository.existsByPhone(request.getPhone())){
            throw new RuntimeException("Пользователь уже существует");
        }
        Client client = clientMapper.toEntity(request);
        Role role = roleRepository.findByName("CLIENT")
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        client.setRole(role);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDto(savedClient);
    }
    public ClientResponse getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Клиент с id " + id + " не найден"));
        return clientMapper.toDto(client);
    }
    public Page<ClientResponse> getAllClients(Pageable pageable) {
        Page<Client> clientsPage = clientRepository.findAll(pageable);
        return clientsPage.map(client -> clientMapper.toDto(client));
    }
    public ClientResponse updateClient(Long id, ClientRequest request) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Клиент с id " + id + " не найден"));
        if (request.getEmail() != null && !request.getEmail().equals(existingClient.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Этот email уже занят другим пользователем");
            }
        }
        if (request.getPhone() != null && !request.getPhone().equals(existingClient.getPhone())) {
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new RuntimeException("Этот телефон уже занят другим пользователем");
            }
        }
        clientMapper.updateEntity(request, existingClient);
        Client updatedClient = clientRepository.save(existingClient);
        return clientMapper.toDto(updatedClient);
    }
    public Page<ClientResponse> getClientsDistinctByAppointmentsBarberId(Long id, Pageable pageable){
        if (!barberRepository.existsById(id)) {
            throw new RuntimeException("Барбер с ID " + id + " не найден");
        }
        Page<Client> clientPage = clientRepository.findDistinctByAppointmentsBarberId(id, pageable);
        return clientPage.map(client -> clientMapper.toDto(client));
    }
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Клиент с id " + id + " не найден");
        }
        clientRepository.deleteById(id);
    }
}

package com.senla.project.services;

import com.senla.project.exceptions.AlreadyExistsException;
import com.senla.project.exceptions.NotFoundException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BarberRepository barberRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, UserRepository userRepository, RoleRepository roleRepository, BarberRepository barberRepository, ClientMapper clientMapper, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.barberRepository = barberRepository;
        this.clientMapper = clientMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ClientResponse createClient(ClientRequest request) {
        if(userRepository.existsByEmail(request.getEmail()) || userRepository.existsByPhone(request.getPhone())){
            throw new AlreadyExistsException("User");
        }
        Client client = clientMapper.toEntity(request);
        Role role = roleRepository.findByName("CLIENT")
                .orElseThrow(() -> new NotFoundException("Role"));
        client.setRole(role);
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDto(savedClient);
    }
    public ClientResponse getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client"));
        return clientMapper.toDto(client);
    }
    public Page<ClientResponse> getAllClients(Pageable pageable) {
        Page<Client> clientsPage = clientRepository.findAll(pageable);
        return clientsPage.map(client -> clientMapper.toDto(client));
    }
    @Transactional
    public ClientResponse updateClient(Long id, ClientRequest request) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client"));
        if (request.getEmail() != null && !request.getEmail().equals(existingClient.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new AlreadyExistsException("Email");
            }
        }
        if (request.getPhone() != null && !request.getPhone().equals(existingClient.getPhone())) {
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new AlreadyExistsException("Phone");
            }
        }
        clientMapper.updateEntity(request, existingClient);
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existingClient.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        Client updatedClient = clientRepository.save(existingClient);
        return clientMapper.toDto(updatedClient);
    }
    public Page<ClientResponse> getClientsDistinctByAppointmentsBarberId(Long id, Pageable pageable){
        if (!barberRepository.existsById(id)) {
            throw new NotFoundException("Barber");
        }
        Page<Client> clientPage = clientRepository.findDistinctByAppointmentsBarberId(id, pageable);
        return clientPage.map(client -> clientMapper.toDto(client));
    }
    @Transactional
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new NotFoundException("Client");
        }
        clientRepository.deleteById(id);
    }
}

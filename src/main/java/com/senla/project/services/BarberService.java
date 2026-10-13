package com.senla.project.services;

import com.senla.project.exceptions.AlreadyExistsException;
import com.senla.project.exceptions.NotFoundException;
import com.senla.project.models.Barber;
import com.senla.project.models.DTO.requests.BarberRequest;
import com.senla.project.models.DTO.responses.BarberResponse;
import com.senla.project.models.Role;
import com.senla.project.repositories.BarberRepository;
import com.senla.project.repositories.RoleRepository;
import com.senla.project.repositories.UserRepository;
import com.senla.project.utils.mapper.BarberMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BarberService {
    private final BarberRepository barberRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BarberMapper barberMapper;
    private final PasswordEncoder passwordEncoder;

    public BarberService(BarberRepository barberRepository, UserRepository userRepository, RoleRepository roleRepository, BarberMapper barberMapper, PasswordEncoder passwordEncoder) {
        this.barberRepository = barberRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.barberMapper = barberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public BarberResponse createBarber(BarberRequest request) {
        if(userRepository.existsByEmail(request.getEmail()) || userRepository.existsByPhone(request.getPhone())){
            throw new AlreadyExistsException("User");
        }
        Barber barber = barberMapper.toEntity(request);
        Role role = roleRepository.findByName("BARBER")
                .orElseThrow(() -> new NotFoundException("Role"));
        barber.setRole(role);
        barber.setPassword(passwordEncoder.encode(request.getPassword()));
        Barber savedBarber = barberRepository.save(barber);
        return barberMapper.toDto(savedBarber);
    }
    public BarberResponse getBarberById(Long id) {
        Barber barber = barberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Barber"));
        return barberMapper.toDto(barber);
    }
    public Page<BarberResponse> getBarbersBySpecialty(String specialty, Pageable pageable) {
        Page<Barber> barbersPage = barberRepository.findBySpecialty(specialty, pageable);
        return barbersPage.map(barber -> barberMapper.toDto(barber));
    }
    public Page<BarberResponse> getAllBarbers(Pageable pageable) {
        Page<Barber> barbersPage = barberRepository.findAll(pageable);
        return barbersPage.map(barber -> barberMapper.toDto(barber));
    }
    @Transactional
    public BarberResponse updateBarber(Long id, BarberRequest request) {
        Barber existingBarber = barberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Barber"));
        if (request.getEmail() != null && !request.getEmail().equals(existingBarber.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new AlreadyExistsException("Email");
            }
        }
        if (request.getPhone() != null && !request.getPhone().equals(existingBarber.getPhone())) {
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new AlreadyExistsException("Phone");
            }
        }
        barberMapper.updateEntity(request, existingBarber);
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existingBarber.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        Barber updatedBarber = barberRepository.save(existingBarber);
        return barberMapper.toDto(updatedBarber);
    }
    @Transactional
    public void deleteBarber(Long id) {
        if (!barberRepository.existsById(id)) {
            throw new NotFoundException("Barber");
        }
        barberRepository.deleteById(id);
    }
}

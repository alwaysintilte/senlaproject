package com.senla.project.services;

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
import org.springframework.stereotype.Service;

@Service
public class BarberService {
    private final BarberRepository barberRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BarberMapper barberMapper;

    public BarberService(BarberRepository barberRepository, UserRepository userRepository, RoleRepository roleRepository, BarberMapper barberMapper) {
        this.barberRepository = barberRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.barberMapper = barberMapper;
    }

    public BarberResponse createBarber(BarberRequest request) {
        if(userRepository.existsByEmail(request.getEmail()) || userRepository.existsByPhone(request.getPhone())){
            throw new RuntimeException("Пользователь уже существует");
        }
        Barber barber = barberMapper.toEntity(request);
        Role role = roleRepository.findByName("BARBER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        barber.setRole(role);
        barber.setRating(0.0);
        Barber savedBarber = barberRepository.save(barber);
        return barberMapper.toDto(savedBarber);
    }
    public BarberResponse getBarberById(Long id) {
        Barber barber = barberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Клиент с id " + id + " не найден"));
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
    public BarberResponse updateBarber(Long id, BarberRequest request) {
        Barber existingBarber = barberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Барбер с id " + id + " не найден"));
        if (request.getEmail() != null && !request.getEmail().equals(existingBarber.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Этот email уже занят другим пользователем");
            }
        }
        if (request.getPhone() != null && !request.getPhone().equals(existingBarber.getPhone())) {
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new RuntimeException("Этот телефон уже занят другим пользователем");
            }
        }
        barberMapper.updateEntity(request, existingBarber);
        Barber updatedBarber = barberRepository.save(existingBarber);
        return barberMapper.toDto(updatedBarber);
    }
    public void deleteBarber(Long id) {
        if (!barberRepository.existsById(id)) {
            throw new RuntimeException("Барбер с id " + id + " не найден");
        }
        barberRepository.deleteById(id);
    }
}

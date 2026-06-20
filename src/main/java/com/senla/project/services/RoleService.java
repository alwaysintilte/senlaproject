package com.senla.project.services;

import com.senla.project.models.DTO.requests.RoleRequest;
import com.senla.project.models.DTO.responses.RoleResponse;
import com.senla.project.models.Role;
import com.senla.project.repositories.RoleRepository;
import com.senla.project.utils.mapper.RoleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new RuntimeException("Роль уже существует");
        }
        Role role = roleMapper.toEntity(request);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDto(savedRole);
    }
    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Роль с id " + id + " не найдена"));
        return roleMapper.toDto(role);
    }
    public RoleResponse getRoleByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Роль с name " + name + " не найдена"));
        return roleMapper.toDto(role);
    }
    public List<RoleResponse> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roleMapper.toDtoList(roles);
    }
}

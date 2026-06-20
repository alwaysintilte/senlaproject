package com.senla.project.services;

import com.senla.project.models.DTO.requests.UserLoginRequest;
import com.senla.project.models.User;
import com.senla.project.models.DTO.requests.UserRequest;
import com.senla.project.models.DTO.responses.UserResponse;
import com.senla.project.models.Role;
import com.senla.project.repositories.UserRepository;
import com.senla.project.repositories.RoleRepository;
import com.senla.project.utils.hash.HashUtil;
import com.senla.project.utils.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    public UserResponse createUser(UserRequest request) {
        if(userRepository.existsByEmail(request.getEmail()) || userRepository.existsByPhone(request.getPhone())){
            throw new RuntimeException("Пользователь уже существует");
        }
        User user = userMapper.toEntity(request);
        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        user.setRole(role);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с id " + id + " не найден"));
        return userMapper.toDto(user);
    }
    public Page<UserResponse> getUsersByRoleName(String roleName, Pageable pageable) {
        Page<User> usersPage = userRepository.findByRoleName(roleName, pageable);
        return usersPage.map(user -> userMapper.toDto(user));
    }
    public UserResponse loginUser(UserLoginRequest request) {
        User user;
        if (request.getEmail() != null) {
            user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        } else if (request.getPhone() != null) {
            user = userRepository.findByPhone(request.getPhone())
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        } else {
            throw new RuntimeException("Необходимо указать email или телефон");
        }
        String hashedRequestPassword = HashUtil.encode(request.getPassword());
        if (!user.getPassword().equals(hashedRequestPassword)) {
            throw new RuntimeException("Неверный пароль");
        }
        return userMapper.toDto(user);
    }
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(user -> userMapper.toDto(user));
    }
    public UserResponse updateUser(Long id, UserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с id " + id + " не найден"));
        if (request.getEmail() != null && !request.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Этот email уже занят другим пользователем");
            }
        }
        if (request.getPhone() != null && !request.getPhone().equals(existingUser.getPhone())) {
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new RuntimeException("Этот телефон уже занят другим пользователем");
            }
        }
        userMapper.updateEntity(request, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Пользователь с id " + id + " не найден");
        }
        userRepository.deleteById(id);
    }
}

package com.senla.project.services;

import com.senla.project.exceptions.AlreadyExistsException;
import com.senla.project.exceptions.InvalidRequestDataException;
import com.senla.project.exceptions.NotFoundException;
import com.senla.project.models.DTO.requests.UserLoginRequest;
import com.senla.project.models.DTO.responses.JwtTokenResponse;
import com.senla.project.models.User;
import com.senla.project.models.DTO.requests.UserRequest;
import com.senla.project.models.DTO.responses.UserResponse;
import com.senla.project.models.Role;
import com.senla.project.repositories.UserRepository;
import com.senla.project.repositories.RoleRepository;
import com.senla.project.security.JwtService;
import com.senla.project.utils.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationConfiguration config) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = config.getAuthenticationManager();
    }

    @Transactional
    public UserResponse createUser(UserRequest request) {
        if(userRepository.existsByEmail(request.getEmail()) || userRepository.existsByPhone(request.getPhone())){
            throw new AlreadyExistsException("User");
        }
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new NotFoundException("Role"));
        user.setRole(role);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User"));
        return userMapper.toDto(user);
    }
    public Page<UserResponse> getUsersByRoleName(String roleName, Pageable pageable) {
        Page<User> usersPage = userRepository.findByRoleName(roleName, pageable);
        return usersPage.map(user -> userMapper.toDto(user));
    }
    public JwtTokenResponse loginUser(UserLoginRequest request) {
        String identifier;
        if (request.getEmail() != null) {
            identifier = request.getEmail();
        } else if (request.getPhone() != null) {
            identifier = request.getPhone();
        } else {
            throw new InvalidRequestDataException("Email or phone must be provided", HttpStatus.BAD_REQUEST);
        }
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(identifier, request.getPassword()));
        } catch (AuthenticationException e) {
            throw new InvalidRequestDataException("Invalid email, phone or password", HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails);
        JwtTokenResponse jwtTokenResponse = new JwtTokenResponse();
        jwtTokenResponse.setToken(jwt);
        return jwtTokenResponse;
    }
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(user -> userMapper.toDto(user));
    }
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User"));
        if (request.getEmail() != null && !request.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new AlreadyExistsException("Email");
            }
        }
        if (request.getPhone() != null && !request.getPhone().equals(existingUser.getPhone())) {
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new AlreadyExistsException("Phone");
            }
        }
        userMapper.updateEntity(request, existingUser);
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User");
        }
        userRepository.deleteById(id);
    }
}

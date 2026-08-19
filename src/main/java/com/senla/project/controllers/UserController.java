package com.senla.project.controllers;

import com.senla.project.models.DTO.requests.UserLoginRequest;
import com.senla.project.models.DTO.requests.UserRequest;
import com.senla.project.models.DTO.responses.JwtTokenResponse;
import com.senla.project.models.DTO.responses.UserResponse;
import com.senla.project.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/login")
    public JwtTokenResponse loginUser(@RequestBody UserLoginRequest request) {
        return userService.loginUser(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public Page<UserResponse> getAllUsers(@PageableDefault(size = 10) Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @GetMapping("/role/{roleName}")
    public Page<UserResponse> getUsersByRoleName(@PathVariable String roleName, @PageableDefault(size = 10) Pageable pageable) {
        return userService.getUsersByRoleName(roleName, pageable);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

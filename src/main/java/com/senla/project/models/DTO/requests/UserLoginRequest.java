package com.senla.project.models.DTO.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLoginRequest {
    @Size(max = 20, message = "Phone must be at most 20 characters")
    private String phone;
    @Size(max = 100, message = "Email must be at most 100 characters")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 256, message = "Password must be between 8 and 256 characters")
    private String password;

    public UserLoginRequest() {}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

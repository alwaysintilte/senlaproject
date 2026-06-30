package com.senla.project.models.DTO.responses;

public class JwtTokenResponse {
    private String token;

    public JwtTokenResponse() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

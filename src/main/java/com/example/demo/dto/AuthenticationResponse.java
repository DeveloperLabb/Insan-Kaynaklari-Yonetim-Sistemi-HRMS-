package com.example.demo.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}

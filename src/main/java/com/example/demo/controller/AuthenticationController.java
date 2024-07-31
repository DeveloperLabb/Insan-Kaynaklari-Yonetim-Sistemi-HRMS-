package com.example.demo.controller;

import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.model.User;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.BlacklistService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    private final BlacklistService blacklistService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, BlacklistService blacklistService) {
        this.authenticationService = authenticationService;
        this.blacklistService = blacklistService;
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
            )  {
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    )  {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping("/log-out")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        String actualToken = token.substring(7); // "Bearer " kısmını çıkar
        blacklistService.blacklistToken(actualToken);
        return ResponseEntity.ok().build();
    }

}


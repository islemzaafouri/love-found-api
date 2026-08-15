package com.lovefound.love_found_api.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lovefound.love_found_api.auth.dto.AuthResponse;
import com.lovefound.love_found_api.auth.dto.LoginRequest;
import com.lovefound.love_found_api.auth.dto.RegisterAdopterRequest;
import com.lovefound.love_found_api.auth.dto.RegisterShelterRequest;
import com.lovefound.love_found_api.auth.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register/adopter")
    public ResponseEntity<String> registerAdopter(@Valid @RequestBody RegisterAdopterRequest request) {
        return ResponseEntity.ok(authService.registerAdopter(request));
    }

    @PostMapping("/register/shelter")
    public ResponseEntity<String> registerShelter(@Valid @RequestBody RegisterShelterRequest request) {
        
        return ResponseEntity.ok(authService.registerShelter(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}

package com.lovefound.love_found_api.WEB.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lovefound.love_found_api.DTO.Auth.AuthResponse;
import com.lovefound.love_found_api.DTO.Auth.LoginRequest;
import com.lovefound.love_found_api.DTO.Auth.RegisterAdopterRequest;
import com.lovefound.love_found_api.DTO.Auth.RegisterShelterRequest;
import com.lovefound.love_found_api.business.Auth.AuthService;

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
        System.out.println("🔥 REGISTER CONTROLLER REACHED");
        return ResponseEntity.ok(authService.registerAdopter(request));
    }

    @PostMapping("/register/shelter")
    public ResponseEntity<String> registerShelter(@Valid @RequestBody RegisterShelterRequest request) {
        System.out.println("🔥 SHELTER CONTROLLER REACHED");
        return ResponseEntity.ok(authService.registerShelter(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}

package com.lovefound.love_found_api.auth.service;

import com.lovefound.love_found_api.auth.dto.AuthResponse;
import com.lovefound.love_found_api.auth.dto.LoginRequest;
import com.lovefound.love_found_api.auth.dto.RegisterAdopterRequest;
import com.lovefound.love_found_api.auth.dto.RegisterShelterRequest;

public interface AuthService {
    String registerAdopter(RegisterAdopterRequest request);
    String registerShelter(RegisterShelterRequest request);
    AuthResponse login(LoginRequest request);
    
}

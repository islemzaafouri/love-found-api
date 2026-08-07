package com.lovefound.love_found_api.business.Auth;

import com.lovefound.love_found_api.DTO.Auth.AuthResponse;
import com.lovefound.love_found_api.DTO.Auth.LoginRequest;
import com.lovefound.love_found_api.DTO.Auth.RegisterAdopterRequest;
import com.lovefound.love_found_api.DTO.Auth.RegisterShelterRequest;

public interface AuthService {
    String registerAdopter(RegisterAdopterRequest request);
    String registerShelter(RegisterShelterRequest request);
    AuthResponse login(LoginRequest request);
    
}

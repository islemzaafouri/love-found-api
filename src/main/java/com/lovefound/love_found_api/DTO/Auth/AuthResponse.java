package com.lovefound.love_found_api.DTO.Auth;

import com.lovefound.love_found_api.DAO.models.Role;

public class AuthResponse {
    private String token;
    private String email;
    private Role role;
}

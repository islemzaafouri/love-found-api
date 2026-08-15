package com.lovefound.love_found_api.auth.dto;

import com.lovefound.love_found_api.auth.model.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthResponse {
    private String token;
    private String email;
    private Role role;
}

package com.lovefound.love_found_api.DTO.Auth;

import com.lovefound.love_found_api.DAO.models.enums.user.Role;

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

package com.lovefound.love_found_api.business.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lovefound.love_found_api.DAO.entities.AdopterProfile;
import com.lovefound.love_found_api.DAO.entities.ShelterProfile;
import com.lovefound.love_found_api.DAO.entities.User;
import com.lovefound.love_found_api.DAO.models.Role;
import com.lovefound.love_found_api.DAO.repos.UserRepo;
import com.lovefound.love_found_api.DTO.Auth.AuthResponse;
import com.lovefound.love_found_api.DTO.Auth.LoginRequest;
import com.lovefound.love_found_api.DTO.Auth.RegisterAdopterRequest;
import com.lovefound.love_found_api.DTO.Auth.RegisterShelterRequest;
import com.lovefound.love_found_api.exceptions.AlreadyExistsException;
import com.lovefound.love_found_api.exceptions.ResourceNotFoundException;
import com.lovefound.love_found_api.security.JwtUtil;


@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    @Override
    public String registerAdopter(RegisterAdopterRequest request) {
        System.out.println("🔥 REGISTER SERVICE REACHED");
        if (userRepo.findByEmail(request.getEmail()) != null) {
            System.out.println("🔥 EMAIL ALREADY EXISTS");
            throw new AlreadyExistsException("Email already exists");
        }
        // Create a new user entity and set its properties
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADOPTER);

        AdopterProfile adopterProfile = new AdopterProfile();
        adopterProfile.setUser(user);
        adopterProfile.setFirstName(request.getFirstName());
        adopterProfile.setLastName(request.getLastName());
        adopterProfile.setPhoneNumber(request.getPhoneNumber());
        adopterProfile.setCity(request.getCity());

        user.setAdopterProfile(adopterProfile);
        userRepo.save(user);
        return ("Adopter registered successfully");
    }

    @Override
    @Transactional
    public String registerShelter(RegisterShelterRequest request) {
        if (userRepo.findByEmail(request.getEmail()) != null) {
            throw new AlreadyExistsException("Email already exists");
        }
        // Create a new user entity and set its properties
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.SHELTER);

        ShelterProfile shelterProfile = new ShelterProfile();
        shelterProfile.setUser(user);
        shelterProfile.setShelterName(request.getShelterName());
        shelterProfile.setPhoneNumber(request.getPhoneNumber());
        shelterProfile.setCity(request.getCity());
        shelterProfile.setAddress(request.getAddress());
        shelterProfile.setDescription(request.getDescription());

        user.setShelterProfile(shelterProfile);
        userRepo.save(user);

        return ("shelter registered successfully");
    } 

    @Override
    public AuthResponse login (LoginRequest request){
        authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
        request.getEmail(),
        request.getPassword()));
    
        User user = userRepo.findByEmail(request.getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthResponse(token, user.getEmail(), user.getRole());

    }


}

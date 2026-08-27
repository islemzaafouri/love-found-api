package com.lovefound.love_found_api.profiles;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lovefound.love_found_api.profiles.dto.AdopterProfileRequest;
import com.lovefound.love_found_api.profiles.dto.AdopterProfileResponse;
import com.lovefound.love_found_api.profiles.dto.ShelterProfileRequest;
import com.lovefound.love_found_api.profiles.dto.ShelterProfileResponse;
import com.lovefound.love_found_api.profiles.service.ProfileService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:4200")
public class ProfileController {
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    public ProfileController(ProfileService profileService,ProfileMapper profileMapper){
        this.profileService=profileService;
        this.profileMapper=profileMapper;
    }

    @GetMapping("adopter/me")
    public ResponseEntity<AdopterProfileResponse>getAdopterProfile(Authentication authentication){
        AdopterProfileResponse response =
                profileService.getMyAdopterProfile(authentication);

        return ResponseEntity.ok(response);

    }

    @PutMapping("/adopter/me")
    public ResponseEntity<AdopterProfileResponse> updateMyAdopterProfile(
            @Valid @RequestBody AdopterProfileRequest request,
            Authentication authentication) {

        AdopterProfileResponse response =
                profileService.updateAdopterProfile(
                        request,
                        authentication
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/shelter/me")
    public ResponseEntity<ShelterProfileResponse> getMyShelterProfile(
            Authentication authentication) {

        ShelterProfileResponse response =
                profileService.getShelterProfile(authentication);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/shelter/me")
    public ResponseEntity<ShelterProfileResponse> updateMyShelterProfile(
            @Valid @RequestBody ShelterProfileRequest request,
            Authentication authentication) {

        ShelterProfileResponse response =
                profileService.updateShelterProfile(
                        request,
                        authentication
                );

        return ResponseEntity.ok(response);
    }



}

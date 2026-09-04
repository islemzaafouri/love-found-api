package com.lovefound.love_found_api.profiles.service;

import org.springframework.security.core.Authentication;

import com.lovefound.love_found_api.profiles.dto.AdopterProfileRequest;
import com.lovefound.love_found_api.profiles.dto.AdopterProfileResponse;
import com.lovefound.love_found_api.profiles.dto.ShelterProfileRequest;
import com.lovefound.love_found_api.profiles.dto.ShelterProfileResponse;
import com.lovefound.love_found_api.profiles.entities.AdopterProfile;
import com.lovefound.love_found_api.profiles.entities.ShelterProfile;

public interface ProfileService {
    AdopterProfileResponse getMyAdopterProfile(Authentication authentication);

    AdopterProfileResponse updateAdopterProfile(
            AdopterProfileRequest updatedProfile,
            Authentication authentication
    );


    ShelterProfileResponse getShelterProfile(Authentication authentication);

    ShelterProfileResponse updateShelterProfile(
            ShelterProfileRequest updatedProfile,
            Authentication authentication
    );
}

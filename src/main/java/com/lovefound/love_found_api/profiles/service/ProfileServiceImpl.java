package com.lovefound.love_found_api.profiles.service;

import com.lovefound.love_found_api.profiles.ProfileMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.lovefound.love_found_api.auth.model.entities.AdopterProfile;
import com.lovefound.love_found_api.auth.model.entities.ShelterProfile;
import com.lovefound.love_found_api.auth.model.entities.User;
import com.lovefound.love_found_api.auth.repository.AdopterProfileRepo;
import com.lovefound.love_found_api.auth.repository.ShelterProfileRepo;
import com.lovefound.love_found_api.auth.repository.UserRepo;
import com.lovefound.love_found_api.core.exceptions.ResourceNotFoundException;
import com.lovefound.love_found_api.profiles.dto.AdopterProfileRequest;
import com.lovefound.love_found_api.profiles.dto.AdopterProfileResponse;
import com.lovefound.love_found_api.profiles.dto.ShelterProfileRequest;
import com.lovefound.love_found_api.profiles.dto.ShelterProfileResponse;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileMapper profileMapper;
    private final UserRepo userRepository;
    private final AdopterProfileRepo adopterProfileRepository;
    private final ShelterProfileRepo shelterProfileRepository;

    public ProfileServiceImpl(
            UserRepo userRepository,
            AdopterProfileRepo adopterProfileRepository,
            ShelterProfileRepo shelterProfileRepository, ProfileMapper profileMapper
    ) {
        this.userRepository = userRepository;
        this.adopterProfileRepository = adopterProfileRepository;
        this.shelterProfileRepository = shelterProfileRepository;
        this.profileMapper = profileMapper;
    }

    @Override
    public AdopterProfileResponse getMyAdopterProfile(Authentication authentication){
        User user=getAuthenticatedUser(authentication);
        AdopterProfile profile =
                adopterProfileRepository
                        .findById(user.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Adopter profile not found."
                                )
                        );

        return profileMapper.toAdopterResponse(profile);

    @Override
    public AdopterProfileResponse updateAdopterProfile(AdopterProfileRequest updatedProfile,Authentication authentication){
        User user=getAuthenticatedUser(authentication);
        AdopterProfile profile =adopterProfileRepository.findById(user.getId()).orElseThrow(() ->new ResourceNotFoundException( "Adopter profile not found." ));

        profileMapper.updateAdopterProfile(profile, updatedProfile);

        AdopterProfile savedProfile =
                adopterProfileRepository.save(profile);

        return profileMapper.toAdopterResponse(savedProfile);

    }

    @Override
    public ShelterProfileResponse getShelterProfile (Authentication authentication){
        User user=getAuthenticatedUser(authentication);

        ShelterProfile profile=shelterProfileRepository.findById(user.getId()).orElseThrow(()->
                                new ResourceNotFoundException("Shelter profile not found."));
        return profileMapper.toShelterResponse(profile);
    
    }

    @Override
    public ShelterProfileResponse updateShelterProfile(
            ShelterProfileRequest request,
            Authentication authentication
    ) {

        User user = getAuthenticatedUser(authentication);

        ShelterProfile profile =
                shelterProfileRepository
                        .findById(user.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Shelter profile not found."
                                )
                        );

        profileMapper.updateShelterProfile(profile, request);

    //helper
    private User getAuthenticatedUser(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new ResourceNotFoundException("Authenticated user not found.");
        }

        return user;
 
    }


}

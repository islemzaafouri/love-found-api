package com.lovefound.love_found_api.profiles.service;

import com.lovefound.love_found_api.profiles.ProfileMapper;
import com.lovefound.love_found_api.profiles.Repos.AdopterProfileRepo;
import com.lovefound.love_found_api.profiles.Repos.ShelterProfileRepo;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.lovefound.love_found_api.auth.model.entities.User;
import com.lovefound.love_found_api.auth.repository.UserRepo;
import com.lovefound.love_found_api.core.exceptions.ResourceNotFoundException;
import com.lovefound.love_found_api.profiles.dto.AdopterProfileRequest;
import com.lovefound.love_found_api.profiles.dto.AdopterProfileResponse;
import com.lovefound.love_found_api.profiles.dto.ShelterProfileRequest;
import com.lovefound.love_found_api.profiles.dto.ShelterProfileResponse;
import com.lovefound.love_found_api.profiles.entities.AdopterProfile;
import com.lovefound.love_found_api.profiles.entities.ShelterProfile;

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
                        .findByUserId(user.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Adopter profile not found."
                                )
                        );

        return profileMapper.toAdopterResponse(profile);}

    @Override
    public AdopterProfileResponse updateAdopterProfile(AdopterProfileRequest updatedProfile,Authentication authentication){
        User user=getAuthenticatedUser(authentication);
        AdopterProfile profile =adopterProfileRepository.findByUserId(user.getId()).orElseThrow(() ->new ResourceNotFoundException( "Adopter profile not found." ));

        profileMapper.updateAdopterProfile(profile, updatedProfile);

        AdopterProfile savedProfile =
                adopterProfileRepository.save(profile);

        return profileMapper.toAdopterResponse(savedProfile);

    }

    @Override
    public ShelterProfileResponse getShelterProfile (Authentication authentication){
        User user=getAuthenticatedUser(authentication);

        ShelterProfile profile=shelterProfileRepository.findByUserId(user.getId()).orElseThrow(()->
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
                        .findByUserId(user.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Shelter profile not found."
                                )
                        );

        profileMapper.updateShelterProfile(profile, request);
        ShelterProfile savedProfile = shelterProfileRepository.save(profile);

        return profileMapper.toShelterResponse(savedProfile);
        }

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

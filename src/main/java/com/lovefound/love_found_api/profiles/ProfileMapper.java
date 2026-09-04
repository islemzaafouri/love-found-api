package com.lovefound.love_found_api.profiles;

import org.springframework.stereotype.Component;

import com.lovefound.love_found_api.profiles.dto.AdopterProfileRequest;
import com.lovefound.love_found_api.profiles.dto.AdopterProfileResponse;
import com.lovefound.love_found_api.profiles.dto.ShelterProfileRequest;
import com.lovefound.love_found_api.profiles.dto.ShelterProfileResponse;
import com.lovefound.love_found_api.profiles.entities.AdopterProfile;
import com.lovefound.love_found_api.profiles.entities.ShelterProfile;

@Component
public class ProfileMapper {

    // ADOPTER
    

    public AdopterProfileResponse toAdopterResponse(
            AdopterProfile profile
    ) {

        AdopterProfileResponse response = new AdopterProfileResponse();

        response.setId(profile.getId());
        response.setFirstName(profile.getFirstName());
        response.setLastName(profile.getLastName());
        response.setPhoneNumber(profile.getPhoneNumber());
        response.setCity(profile.getCity());

        response.setLifestyleActivity(
                profile.getLifestyleActivity()
        );

        response.setExperience(
                profile.getExperience()
        );

        response.setHomeType(
                profile.getHomeType()
        );

        response.setHasOtherPets(
                profile.getHasOtherPets()
        );

        response.setHasChildren(
                profile.getHasChildren()
        );

        response.setWorkSchedule(
                profile.getWorkSchedule()
        );

        response.setPreferredSpecies(
                profile.getPreferredSpecies()
        );

        response.setHasAllergies(
                profile.getHasAllergies()
        );

        return response;
    }


    public void updateAdopterProfile(
            AdopterProfile profile,
            AdopterProfileRequest request
    ) {

        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setCity(request.getCity());

        profile.setLifestyleActivity(
                request.getLifestyleActivity()
        );

        profile.setExperience(
                request.getExperience()
        );

        profile.setHomeType(
                request.getHomeType()
        );

        profile.setHasOtherPets(
                request.getHasOtherPets()
        );

        profile.setHasChildren(
                request.getHasChildren()
        );

        profile.setWorkSchedule(
                request.getWorkSchedule()
        );

        profile.setPreferredSpecies(
                request.getPreferredSpecies()
        );

        profile.setHasAllergies(
                request.getHasAllergies()
        );
    }


    // SHELTER
    

    public ShelterProfileResponse toShelterResponse(
            ShelterProfile profile
    ) {

        ShelterProfileResponse response =
                new ShelterProfileResponse();

        response.setId(profile.getId());
        response.setShelterName(profile.getShelterName());
        response.setPhoneNumber(profile.getPhoneNumber());
        response.setAddress(profile.getAddress());
        response.setCity(profile.getCity());
        response.setDescription(profile.getDescription());

        return response;
    }


    public void updateShelterProfile(
            ShelterProfile profile,
            ShelterProfileRequest request
    ) {

        profile.setShelterName(
                request.getShelterName()
        );

        profile.setPhoneNumber(
                request.getPhoneNumber()
        );

        profile.setAddress(
                request.getAddress()
        );

        profile.setCity(
                request.getCity()
        );

        profile.setDescription(
                request.getDescription()
        );
    }
}
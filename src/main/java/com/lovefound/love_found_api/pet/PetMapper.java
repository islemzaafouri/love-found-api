package com.lovefound.love_found_api.pet;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.lovefound.love_found_api.pet.dto.PetResponse;
import com.lovefound.love_found_api.pet.dto.ShelterSummary;
import com.lovefound.love_found_api.pet.model.entity.Pet;
import com.lovefound.love_found_api.profiles.entities.ShelterProfile;

@Component
public class PetMapper {

    // Convert Pet Entity -> PetResponse DTO
    public PetResponse toResponse(Pet pet) {
        if (pet == null) {
            return null;
        }

        PetResponse response = new PetResponse();
        response.setId(pet.getId());
        response.setName(pet.getName());
        response.setSpecies(pet.getSpecies());
        response.setAge(pet.getAge());
        response.setGender(pet.getGender());
        response.setBreed(pet.getBreed());
        response.setPhotoUrl(pet.getPhotoUrl());
        response.setPersonalityTraits(pet.getPersonalityTraits());
        response.setDescription(pet.getDescription());
        response.setRescueStory(pet.getRescueStory());
        response.setStatus(pet.getStatus());

        // Safely extract shelter info without exposing the User entity
        if (pet.getShelter() != null) {
            response.setShelter(toShelterSummary(pet.getShelter()));
        }

        return response;
    }

    // Convert List<Pet> Entities -> List<PetResponse> DTOs
    public List<PetResponse> toResponseList(List<Pet> pets) {
        if (pets == null) {
            return List.of();
        }
        return pets.stream()
                   .map(this::toResponse)
                   .collect(Collectors.toList());
    }

    private ShelterSummary toShelterSummary(ShelterProfile shelter) {
        ShelterSummary summary = new ShelterSummary();
        summary.setId(shelter.getId());
        summary.setName(shelter.getShelterName());
        summary.setCity(shelter.getCity());
        summary.setPhone(shelter.getPhoneNumber());
        summary.setDecription(shelter.getDescription());
        return summary;
    }


    
}


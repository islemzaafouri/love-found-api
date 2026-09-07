package com.lovefound.love_found_api.adoption_application.dto;

import java.time.LocalDateTime;

import com.lovefound.love_found_api.adoption_application.models.enums.ApplicationStatus;
import com.lovefound.love_found_api.pet.dto.PetResponse;
import com.lovefound.love_found_api.pet.model.entity.Pet;
import com.lovefound.love_found_api.profiles.dto.AdopterProfileResponse;
import com.lovefound.love_found_api.profiles.entities.AdopterProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationResponse {
    Long Id;
    AdopterProfileResponse adopter;
    PetResponse pet;
    String motivation;
    ApplicationStatus status;
        private LocalDateTime createdAt;

}

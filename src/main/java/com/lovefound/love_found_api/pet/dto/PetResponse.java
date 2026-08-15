package com.lovefound.love_found_api.pet.dto;

import java.util.Set;

import com.lovefound.love_found_api.pet.model.enums.PersonalityTrait;
import com.lovefound.love_found_api.pet.model.enums.PetGender;
import com.lovefound.love_found_api.pet.model.enums.PetSpecies;
import com.lovefound.love_found_api.pet.model.enums.PetStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetResponse {
    private Long id;
    private String name;
    private PetSpecies species;
    private Integer age;
    private PetGender gender;
    private String breed;
    private String photourl;
    private Set<PersonalityTrait> personalityTraits;
    private String description;
    private String rescueStory;
    private PetStatus status;

    // 👈 Safe, clean shelter details ONLY (No User object!)
    private ShelterSummary shelter;
}

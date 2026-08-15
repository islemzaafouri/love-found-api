package com.lovefound.love_found_api.pet.dto;

import java.util.Set;

import com.lovefound.love_found_api.pet.model.enums.PersonalityTrait;
import com.lovefound.love_found_api.pet.model.enums.PetGender;
import com.lovefound.love_found_api.pet.model.enums.PetSpecies;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PetRequest {
    @NotBlank(message = "name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "species is required ")
    private PetSpecies species;

    @NotNull(message = "age is required")
    @PositiveOrZero(message = "Age cannot be negative")
    @Max(value = 100, message = "Age seems invalid")
    private Integer age;

    @NotNull(message="gender is required")
    private PetGender gender;

    @NotBlank(message = "breed is required")
    @Size(max = 50, message = "Breed cannot exceed 50 characters")
    private String breed;

    @Pattern(
        regexp = "^(https?://.*)?$", 
        message = "Photo URL must be a valid http or https URL"
    )
    private String photourl;

    @NotEmpty(message = "At least one personality trait is required")
    private Set<PersonalityTrait> personalityTraits;
    
    
    private String rescueStory;

    @Size(max = 500)
    private String description;

}

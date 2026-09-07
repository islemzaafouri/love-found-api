package com.lovefound.love_found_api.adoption_application.dto;

import com.lovefound.love_found_api.pet.model.entity.Pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationRequest {

    @NotNull(message = "Pet is required")
    private Long petId;

    @NotBlank(message = "Motivation is required")
    @Size(
        min = 20,
        max = 1000,
        message = "Motivation must be between 20 and 1000 characters"
    )
    private String motivation;
}

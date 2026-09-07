package com.lovefound.love_found_api.adoption_application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationUpdateRequest {

    @NotBlank(message = "Motivation is required")
    @Size(
        min = 20,
        max = 1000,
        message = "Motivation must be between 20 and 1000 characters"
    )
    private String motivation;
}

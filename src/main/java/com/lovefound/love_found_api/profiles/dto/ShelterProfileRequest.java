package com.lovefound.love_found_api.profiles.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShelterProfileRequest {
    @NotBlank(message = "Shelter name is required")
    private String shelterName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private String address;

    private String city;

    private String description;

}

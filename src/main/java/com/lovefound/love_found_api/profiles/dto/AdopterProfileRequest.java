package com.lovefound.love_found_api.profiles.dto;

import com.lovefound.love_found_api.profiles.enums.ExperienceLevel;
import com.lovefound.love_found_api.profiles.enums.HomeType;
import com.lovefound.love_found_api.profiles.enums.LifeStyleActivity;
import com.lovefound.love_found_api.profiles.enums.PreferredSpecies;
import com.lovefound.love_found_api.profiles.enums.WorkSchedule;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdopterProfileRequest {
     @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String phoneNumber;

    @NotBlank(message = "City is required")
    private String city;

    private LifeStyleActivity lifestyleActivity;

    private ExperienceLevel experience;

    private HomeType homeType;

    private Boolean hasOtherPets;

    private Boolean hasChildren;

    private WorkSchedule workSchedule;

    private PreferredSpecies preferredSpecies;

    private Boolean hasAllergies;
}

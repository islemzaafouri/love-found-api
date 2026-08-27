package com.lovefound.love_found_api.profiles.dto;

import com.lovefound.love_found_api.auth.model.enums.ExperienceLevel;
import com.lovefound.love_found_api.auth.model.enums.HomeType;
import com.lovefound.love_found_api.auth.model.enums.LifeStyleActivity;
import com.lovefound.love_found_api.auth.model.enums.PreferredSpecies;
import com.lovefound.love_found_api.auth.model.enums.WorkSchedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdopterProfileResponse {
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
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

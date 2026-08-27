package com.lovefound.love_found_api.profiles.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShelterProfileResponse {
    private Long id;

    private String shelterName;
    private String phoneNumber;
    private String address;
    private String city;
    private String description;

}

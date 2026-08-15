package com.lovefound.love_found_api.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShelterSummary {
    private Long id;
    private String name;
    private String city;
    private String phone;
    private String decription;
}

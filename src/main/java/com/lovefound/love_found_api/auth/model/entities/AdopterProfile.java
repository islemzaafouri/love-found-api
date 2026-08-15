package com.lovefound.love_found_api.auth.model.entities;

import com.lovefound.love_found_api.auth.model.enums.ExperienceLevel;
import com.lovefound.love_found_api.auth.model.enums.HomeType;
import com.lovefound.love_found_api.auth.model.enums.LifeStyleActivity;
import com.lovefound.love_found_api.auth.model.enums.PreferredSpecies;
import com.lovefound.love_found_api.auth.model.enums.WorkSchedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "adopter_profiles")
@Getter
@Setter
@NoArgsConstructor
public class AdopterProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Personal Details ---
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;


    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "city", nullable = false)
    private String city;

    // --- Adoption Matching Preferences ---
    @Enumerated(EnumType.STRING)
    private LifeStyleActivity lifestyleActivity;

    @Enumerated(EnumType.STRING)
    private ExperienceLevel experience;

    @Enumerated(EnumType.STRING)
    private HomeType homeType;

    private Boolean hasOtherPets;

    private Boolean hasChildren;

    @Enumerated(EnumType.STRING)
    private WorkSchedule workSchedule;

    @Enumerated(EnumType.STRING)
    private PreferredSpecies preferredSpecies;

    private Boolean hasAllergies;

    // --- Foreign Key Relationship ---
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}

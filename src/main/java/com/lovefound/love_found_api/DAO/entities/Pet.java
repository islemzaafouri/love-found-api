package com.lovefound.love_found_api.DAO.entities;

import java.util.HashSet;
import java.util.Set;

import com.lovefound.love_found_api.DAO.models.enums.pets.PersonalityTrait;
import com.lovefound.love_found_api.DAO.models.enums.pets.PetGender;
import com.lovefound.love_found_api.DAO.models.enums.pets.PetSpecies;
import com.lovefound.love_found_api.DAO.models.enums.pets.PetStatus;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pets")
@Getter
@Setter
@NoArgsConstructor
public class Pet {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Basic Information ---

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetSpecies species;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetGender gender;

    private String breed;

     // ---------- Personality ----------

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(
        name = "pet_personality_traits",
        joinColumns = @JoinColumn(name = "pet_id")
    )
    @Column(name = "trait")
    private Set<PersonalityTrait> personalityTraits = new HashSet<>();

    // ---------- Story & Description ----------

    @Column(columnDefinition = "TEXT")
    private String rescueStory;

    @Column(length = 500)
    private String description;

    // ---------- Adoption Status ----------

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetStatus status = PetStatus.AVAILABLE;


    // ---------- Shelter Relationship ----------

    @ManyToOne
    @JoinColumn(name = "shelter_id", nullable = false)
    private ShelterProfile shelter;


}

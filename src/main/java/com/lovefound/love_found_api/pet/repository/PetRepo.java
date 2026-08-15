package com.lovefound.love_found_api.pet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lovefound.love_found_api.pet.model.enums.PetSpecies;
import com.lovefound.love_found_api.pet.model.enums.PetStatus;
import com.lovefound.love_found_api.pet.model.entity.Pet;

@Repository
public interface PetRepo extends JpaRepository<Pet,Long> {
    List<Pet> findByStatus(PetStatus status);

    List<Pet> findByShelterId(Long shelterId);
    
    List<Pet>findBySpecies(PetSpecies species);

}

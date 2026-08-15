package com.lovefound.love_found_api.pet.service;

import java.util.List;

import com.lovefound.love_found_api.pet.model.enums.PetSpecies;
import com.lovefound.love_found_api.pet.dto.PetRequest;
import com.lovefound.love_found_api.pet.model.entity.Pet;

public interface PetService {
    Pet createPet(Long shelterId,PetRequest petDTO);

    List<Pet> getAllPets();

    Pet getPetById(Long id);

    Pet updatePet(Long id, Long shelterId,PetRequest petDTO);

    void deactivatePet(Long petId,Long shelterId );

    List<Pet> getPetsByShelterId(Long shelterId);

    List<Pet> getPetsBySpecies(PetSpecies species);

    List<Pet> getAvailablePets();
}

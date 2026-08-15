package com.lovefound.love_found_api.pet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lovefound.love_found_api.pet.model.enums.PetSpecies;
import com.lovefound.love_found_api.pet.model.enums.PetStatus;
import com.lovefound.love_found_api.auth.model.entities.ShelterProfile;
import com.lovefound.love_found_api.auth.repository.ShelterProfileRepo;
import com.lovefound.love_found_api.core.exceptions.ResourceNotFoundException;
import com.lovefound.love_found_api.core.exceptions.UnauthorizedException;
import com.lovefound.love_found_api.pet.dto.PetRequest;
import com.lovefound.love_found_api.pet.model.entity.Pet;
import com.lovefound.love_found_api.pet.repository.PetRepo;

@Service
public class PetServiceImpl implements PetService {
    private final PetRepo petRepo;
    private final ShelterProfileRepo shelterRepo;
    public PetServiceImpl(PetRepo petRepo,ShelterProfileRepo shelterRepo){
        this.petRepo=petRepo;
        this.shelterRepo=shelterRepo;
    }

    @Override
    public Pet createPet (Long shelterId , PetRequest petDTO){
        Pet pet =new Pet();
        pet.setName(petDTO.getName());
        pet.setSpecies(petDTO.getSpecies());
        pet.setAge(petDTO.getAge());
        pet.setGender(petDTO.getGender());
        pet.setBreed(petDTO.getBreed());
        pet.setPhotourl(petDTO.getPhotourl());
        pet.setPersonalityTraits(petDTO.getPersonalityTraits());
        pet.setDescription(petDTO.getDescription());
        pet.setRescueStory(petDTO.getRescueStory());
    
    ShelterProfile shelter=shelterRepo.findById(shelterId).orElseThrow(()->new ResourceNotFoundException("Shelter not found"));
    pet.setShelter(shelter);
    return petRepo.save(pet);}

    @Override
    public List<Pet> getAllPets(){
        return petRepo.findAll();
    }

    @Override
    public Pet getPetById (Long petId){
        return petRepo.findById(petId).orElseThrow(()->new ResourceNotFoundException("Pet not found"));
    }

    @Override
    public Pet updatePet(Long petId ,Long shelterId, PetRequest updatedPet)
    {
        Pet pet = getPetById(petId);
        if(!pet.getShelter().getId().equals(shelterId)){
            throw new UnauthorizedException("Unauthorized: Shelter does not own this pet");
        }
        pet.setName(updatedPet.getName());
        pet.setSpecies(updatedPet.getSpecies());
        pet.setAge(updatedPet.getAge());
        pet.setGender(updatedPet.getGender());
        pet.setBreed(updatedPet.getBreed());
        pet.setPhotourl(updatedPet.getPhotourl());
        pet.setPersonalityTraits(updatedPet.getPersonalityTraits());
        pet.setDescription(updatedPet.getDescription());
        pet.setRescueStory(updatedPet.getRescueStory());

        return petRepo.save(pet);
    }
    @Override
    public void deactivatePet(Long petId,Long shelterId){
        Pet pet=getPetById(petId);
        if (!pet.getShelter().getId().equals(shelterId)){
            throw new UnauthorizedException("Unauthorized: Shelter does not own this pet");
        }
        pet.setStatus(PetStatus.ADOPTED);
        petRepo.save(pet);
    }

    @Override
    public List<Pet> getPetsByShelterId (Long shelterId)
    {
        return petRepo.findByShelterId(shelterId);
    }

    @Override
    public List<Pet> getPetsBySpecies(PetSpecies species){
        return petRepo.findBySpecies(species);
    }

    @Override
    public List<Pet> getAvailablePets() {
    return petRepo.findByStatus(PetStatus.AVAILABLE);
}

}

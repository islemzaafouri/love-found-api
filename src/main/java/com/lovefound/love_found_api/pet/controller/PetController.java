package com.lovefound.love_found_api.pet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lovefound.love_found_api.auth.model.entities.ShelterProfile;
import com.lovefound.love_found_api.auth.repository.ShelterProfileRepo;
import com.lovefound.love_found_api.core.exceptions.ResourceNotFoundException;
import com.lovefound.love_found_api.pet.PetMapper;
import com.lovefound.love_found_api.pet.dto.PetRequest;
import com.lovefound.love_found_api.pet.dto.PetResponse;
import com.lovefound.love_found_api.pet.model.entity.Pet;
import com.lovefound.love_found_api.pet.model.enums.PetSpecies;
import com.lovefound.love_found_api.pet.service.PetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;
    private final PetMapper petMapper;
    private final ShelterProfileRepo shelterRepo;

    public PetController (PetService petService,PetMapper petMapper,ShelterProfileRepo shelterRepo){
        this.petService=petService;
        this.petMapper = petMapper;
        this.shelterRepo=shelterRepo;
    }

    @GetMapping
    public ResponseEntity<List<PetResponse>> getAvailabelPets(){
        List<Pet> pets=petService.getAvailablePets();
        return ResponseEntity.ok(petMapper.toResponseList(pets));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse>getPetById(@PathVariable Long id)
    {
        Pet pet = petService.getPetById(id);
        return ResponseEntity.ok(petMapper.toResponse(pet));
    }

    @GetMapping("/shelter/{shelterId}")
    public ResponseEntity<List<PetResponse>> getPetsByShelter(@PathVariable Long shelterId)
    {
        List<Pet> pets=petService.getPetsByShelterId(shelterId);
        return ResponseEntity.ok(petMapper.toResponseList(pets));

    }

    @GetMapping("/species/{species}")
    public ResponseEntity<List<PetResponse>> getPetsBySpecies(@PathVariable PetSpecies species)
    {
        List<Pet> pets = petService.getPetsBySpecies(species);
        return ResponseEntity.ok(petMapper.toResponseList(pets));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PetResponse>> getAllPets () 
    {
        List<Pet> pets = petService.getAllPets();
        return ResponseEntity.ok(petMapper.toResponseList(pets));
    }

    // ---------- Shelter-only writes ----------
    @PostMapping
    public ResponseEntity<PetResponse> createPet (@Valid @RequestBody 
        PetRequest petRequest,
        Authentication authentication)
        {
            Long shelterId = extractShelterId(authentication);
        Pet createdPet = petService.createPet( shelterId,petRequest);
        System.out.println("User: " + authentication.getName());
        System.out.println("Authorities: " + authentication.getAuthorities());
        return ResponseEntity.status(HttpStatus.CREATED).body(petMapper.toResponse(createdPet));
        }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivatePet(@PathVariable Long id,
        Authentication authentication)
        {
            Long shelterId=extractShelterId(authentication);
            petService.deactivatePet(id, shelterId);
            return ResponseEntity.noContent().build();
        }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponse>updatePet(@PathVariable Long id,
        @Valid @RequestBody PetRequest petRequest,Authentication authentication)
    {
        Long shelterId=extractShelterId(authentication);
        Pet updatedPet = petService.updatePet(id,  shelterId,petRequest);
        return ResponseEntity.ok(petMapper.toResponse(updatedPet));
    }

    // ---------- Helper ----------

    private Long extractShelterId(Authentication authentication) {
    String userEmail = authentication.getName();
    ShelterProfile shelter = shelterRepo.findByUserEmail(userEmail)
        .orElseThrow(() -> new ResourceNotFoundException("Shelter profile not found for logged in user"));
    return shelter.getId();
} 
    
    
}

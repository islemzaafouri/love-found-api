package com.lovefound.love_found_api.adoption_application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lovefound.love_found_api.adoption_application.models.entities.Application;
import com.lovefound.love_found_api.adoption_application.models.enums.ApplicationStatus;
import com.lovefound.love_found_api.pet.model.entity.Pet;
import com.lovefound.love_found_api.pet.model.enums.PetStatus;

public interface ApplicationRepo extends JpaRepository<Application,Long> {
    List<Application> findByStatus(ApplicationStatus status);
    List<Application> findByPetShelterId(Long shelterId);
    Boolean existsByAdopterIdAndPetId(Long adopterId, Long petId);
    List<Application> findByAdopterId(Long id);
}

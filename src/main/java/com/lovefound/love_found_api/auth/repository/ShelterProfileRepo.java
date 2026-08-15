package com.lovefound.love_found_api.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lovefound.love_found_api.auth.model.entities.ShelterProfile;

@Repository
public interface ShelterProfileRepo extends JpaRepository<ShelterProfile, Long> {
    Optional<ShelterProfile> findByUserEmail(String email);
}

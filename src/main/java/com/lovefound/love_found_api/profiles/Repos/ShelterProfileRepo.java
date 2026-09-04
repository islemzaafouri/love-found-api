package com.lovefound.love_found_api.profiles.Repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lovefound.love_found_api.profiles.entities.ShelterProfile;

@Repository
public interface ShelterProfileRepo extends JpaRepository<ShelterProfile, Long> {
    Optional<ShelterProfile> findByUserEmail(String email);
    Optional<ShelterProfile> findByUserId(Long userId);
}

package com.lovefound.love_found_api.profiles.Repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lovefound.love_found_api.profiles.entities.AdopterProfile;
import com.lovefound.love_found_api.profiles.entities.ShelterProfile;

@Repository
public interface AdopterProfileRepo extends JpaRepository<AdopterProfile, Long> {
    Optional<AdopterProfile> findByUserId(Long userId);
}

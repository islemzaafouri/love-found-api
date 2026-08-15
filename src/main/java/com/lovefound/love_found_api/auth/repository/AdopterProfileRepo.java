package com.lovefound.love_found_api.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lovefound.love_found_api.auth.model.entities.AdopterProfile;

@Repository
public interface AdopterProfileRepo extends JpaRepository<AdopterProfile, Long> {

}

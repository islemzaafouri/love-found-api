package com.lovefound.love_found_api.DAO.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lovefound.love_found_api.DAO.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);

}

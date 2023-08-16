package com.website.profile.repository;

import com.website.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface UserCredentialRepository extends JpaRepository<Profile,String> {
    Optional<Profile> findByUserEmail(String userEmail);
}

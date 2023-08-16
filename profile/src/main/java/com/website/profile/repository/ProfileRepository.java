package com.website.profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.website.profile.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String>{
	
	public Optional<Profile> findByProfileId(Long profileId);
	
	public Optional<Profile> findByUserEmail(String userEmail);
	
	public Optional<Profile> findByUserMobNo(String userMobNo);

}

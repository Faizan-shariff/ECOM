package com.website.profile.service;

import java.util.List;
import java.util.Map;

import com.website.profile.dto.ChangePasswordDto;
import com.website.profile.dto.Profiledto;
import com.website.profile.entity.Profile;
import com.website.profile.exception.InvalidOldPasswordException;
import com.website.profile.exception.MobileNumberAlreadyExistsException;
import com.website.profile.exception.ResourceNotFoundException;

public interface ProfileService {
	
	public List<Profile> listProfiles();
	
	public Map<String,Object> insertProfile(Profiledto profiledto) throws Exception;
	
	public Map<String,Object> login(Profiledto profiledto) throws ResourceNotFoundException, Exception;
	
	public void changePassword(ChangePasswordDto userCredentials) throws ResourceNotFoundException, InvalidOldPasswordException;
	
	public Profiledto getProfileById(Long profileId) throws ResourceNotFoundException;
	
	public void deleteProfileById(Long profileId) throws ResourceNotFoundException;
	
	public Profile updateProfileById(Profile profiledetails) throws ResourceNotFoundException;
	
	public Profiledto getProfileByUserEmailId(String userEmail) throws ResourceNotFoundException;
	
}

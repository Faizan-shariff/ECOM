package com.website.profile.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.website.profile.dto.ChangePasswordDto;
import com.website.profile.dto.Profiledto;
import com.website.profile.entity.Profile;
import com.website.profile.exception.ResourceNotFoundException;
import com.website.profile.service.ProfileService;

@RestController
@CrossOrigin
@RequestMapping("/api/profile")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	
	@PostMapping("/insert")
	public ResponseEntity<Map<String,Object>> saveProfile(@RequestBody Profiledto profiledto)throws Exception {
		Map<String,Object> savedprofileEntity = profileService.insertProfile(profiledto);
		return new ResponseEntity<>(savedprofileEntity, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String,Object>> login(@RequestBody Profiledto profiledto) throws Exception {
		Map<String,Object> foundProfileEntity = profileService.login(profiledto);
		return new ResponseEntity<>(foundProfileEntity, HttpStatus.OK);
	}
	
	@PutMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto userCredentials) throws ResourceNotFoundException, Exception {
		profileService.changePassword(userCredentials);
		return new ResponseEntity<>("Password changed successfully!",HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<Profile>> getAllProfiles() {
		List<Profile> profile = profileService.listProfiles();
		return new ResponseEntity<>(profile, HttpStatus.OK);
	}
	
	@GetMapping("/getById/{profileId}")
	public ResponseEntity<Profiledto> getProfileWithAddressesByProfileId(@PathVariable("profileId") Long profileId)
			throws ResourceNotFoundException {

		Profiledto profiledto = profileService.getProfileById(profileId);
		return new ResponseEntity<>(profiledto, HttpStatus.OK);
	}
	
	@GetMapping("/getProfileByUserEmailId/{userEmail}")
	public ResponseEntity<Profiledto> getProfileByUserEmailId(@PathVariable("userEmail") String userEmail)
			throws ResourceNotFoundException {
		
		Profiledto profiledto = profileService.getProfileByUserEmailId(userEmail);
		return new ResponseEntity<>(profiledto, HttpStatus.OK);
	}
	
	@PutMapping("/updateById")
	public ResponseEntity<Profile> updateProfileById(@RequestBody Profile profiledetails)
			throws ResourceNotFoundException {

		final Profile updatedProfile = profileService.updateProfileById(profiledetails);
		return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteById/{profileId}")
	public ResponseEntity<String> deleteProfileById(@PathVariable("profileId") Long profileId) throws Exception {

		profileService.deleteProfileById(profileId);
		return new ResponseEntity<String>("Profile deleted successfully!.", HttpStatus.OK);
	}

}

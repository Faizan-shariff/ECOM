package com.website.profile.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.website.profile.controller.AuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.website.profile.dto.AuthRequest;
import com.website.profile.dto.ChangePasswordDto;
import com.website.profile.dto.Profiledto;
import com.website.profile.entity.Address;
import com.website.profile.entity.Profile;
import com.website.profile.exception.EmailAlreadyExistsException;
import com.website.profile.exception.InvalidOldPasswordException;
import com.website.profile.exception.MobileNumberAlreadyExistsException;
import com.website.profile.exception.ResourceNotFoundException;
import com.website.profile.repository.AddressRepository;
import com.website.profile.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
//	private AuthClient authClient;
	private AuthController authController;

	@Autowired
	private PasswordEncoder passwordEncoder;
		
	
	@Override
	public List<Profile> listProfiles() {
		return profileRepository.findAll();
	}

	@Override
	public Map<String, Object> insertProfile(Profiledto profiledto) throws Exception {
//		profileRepository.findByUserMobNo(profiledto.getUserMobNo()).ifPresent((u)->{
//			throw new MobileNumberAlreadyExistsException("Profile with this mobile number already exists. Please use a different mobile number.");
//		});
//		
//		profileRepository.findByUserEmail(profiledto.getUserEmail()).ifPresent((u)->{
//			throw new EmailAlreadyExistsException("Profile with this Email ID already exists. Please use a different Email Id.");
//		});
		Profile profileObj = new Profile();
		profileObj.setUserName(profiledto.getUserName());
		profileObj.setUserGender(profiledto.getUserGender());
		profileObj.setUserEmail(profiledto.getUserEmail());
		profileObj.setUserMobNo(profiledto.getUserMobNo());
		profileObj.setUserPassword(passwordEncoder.encode(profiledto.getUserPassword()));
		
		Profile savedProfile = profileRepository.save(profileObj);
		
		String token = String.valueOf(authController.getToken(new AuthRequest(profiledto.getUserEmail(), profiledto.getUserPassword())));
		Map<String,Object> tokenAndProfile= new HashMap<>();
		
		tokenAndProfile.put("profile", savedProfile);
		tokenAndProfile.put("token", token);
		
		return tokenAndProfile;
	}

	@Override
	public Map<String, Object> login(Profiledto profiledto) throws ResourceNotFoundException, Exception {
		String email = profiledto.getUserEmail();
		Profile foundProfile = profileRepository.findByUserEmail(email).orElseThrow(()-> new ResourceNotFoundException("Unable to find profile with the given email"));
		Map<String,Object> tokenAndProfile = new HashMap<>();
		try {
			String token = String.valueOf(authController.getToken(new AuthRequest(email, profiledto.getUserPassword())));
			tokenAndProfile.put("token", token);
			tokenAndProfile.put("profile", foundProfile);
		}catch(Exception exc) {
			throw new ResourceNotFoundException("No Profile found with provided credentials. Please check email and password and try again.");
		}
		return tokenAndProfile;
	}

	@Override
	public void changePassword(ChangePasswordDto userCredentials)
			throws ResourceNotFoundException, InvalidOldPasswordException {
		String email = userCredentials.getEmail();
		String oldPassword = userCredentials.getOldPassword();
		Profile existingProfile = profileRepository.findByUserEmail(email).orElseThrow(()->new ResourceNotFoundException("No User found for provided Email."));
		if(passwordEncoder.matches(oldPassword, existingProfile.getUserPassword())) {
			String newPassword = userCredentials.getNewPassword();
			if(!newPassword.isBlank()) {
				existingProfile.setUserPassword(passwordEncoder.encode(newPassword));
				profileRepository.save(existingProfile);
			}
		}else {
			throw new InvalidOldPasswordException("Unable to change password. Entered old password is Invalid!!");
			}
	}

	@Override
	public Profiledto getProfileById(Long profileId) throws ResourceNotFoundException {
		Optional<Profile> profileObj = profileRepository.findByProfileId(profileId);
		List<Address> addressObj = addressRepository.getAllAddressByProfileId(profileId);
		
		if(profileObj.isPresent()) {
			Profiledto profiledto = new Profiledto();
			profiledto.setProfileId(profileObj.get().getProfileId());
			profiledto.setAddress(addressObj);
			profiledto.setUserName(profileObj.get().getUserName());
			profiledto.setUserEmail(profileObj.get().getUserEmail());
			profiledto.setUserGender(profileObj.get().getUserGender());
			profiledto.setUserMobNo(profileObj.get().getUserMobNo());
			
			return profiledto;
		}else {
			throw new ResourceNotFoundException("Profile with profileId: "+profileId+" Not Found");
		}
	}
	@Override
	public Profiledto getProfileByUserEmailId(String userEmail) throws ResourceNotFoundException {
		Optional<Profile> profileObj = profileRepository.findByUserEmail(userEmail);
        List<Address> addressObj = addressRepository.getAllAddressByProfileId(profileObj.get().getProfileId());

        if (profileObj.isPresent()) {
            Profiledto profiledto = new Profiledto();
            profiledto.setProfileId(profileObj.get().getProfileId());
            profiledto.setAddress(addressObj);
            profiledto.setUserEmail(profileObj.get().getUserEmail());
            profiledto.setUserGender(profileObj.get().getUserGender());
            profiledto.setUserMobNo(profileObj.get().getUserMobNo());
            profiledto.setUserName(profileObj.get().getUserName());
            return profiledto;
        } else {
            throw new ResourceNotFoundException("Profile with Email Id: " + userEmail + " Not Found");
        }
	}

	@Transactional
	@Override
	public void deleteProfileById(Long profileId) throws ResourceNotFoundException {
		Optional<Profile> foundProfile = profileRepository.findByProfileId(profileId);
		if(foundProfile.isPresent()) {
			addressRepository.deleteAllAddressByProfileId(profileId);
			profileRepository.deleteById(String.valueOf(profileId));
			
		}else {
			profileRepository.findByProfileId(profileId).orElseThrow(()->{
				return new ResourceNotFoundException("Profile with ProfileId: \" + profileId + \" is not available and cannot be deleted.");
			});
		}
		
	}

	@Override
	public Profile updateProfileById(Profile profile) throws ResourceNotFoundException {
		if(profile.getUserMobNo()!= null && !profile.getUserMobNo().isBlank()) {
			profileRepository.findByUserMobNo(profile.getUserMobNo()).ifPresent((u)->{
				if(u.getUserMobNo().equals(profile.getUserMobNo())&& !u.getProfileId().equals(profile.getProfileId()))
					
						{
//					throw new MobileNumberAlreadyExistsException("Profile with this number already exists. Please use a different mobile number.");
						}
					
			});
		}
		if(profile.getUserEmail()!=null && !profile.getUserEmail().isBlank()) {
			Optional<Profile> findProfile = profileRepository.findByUserEmail(profile.getUserEmail());
			if(findProfile.isPresent()) {
				Profile u = findProfile.get();
				if(u.getUserEmail().equals(profile.getUserEmail())&&!u.getProfileId().equals(profile.getProfileId())) {
//					throw new EmailAlreadyExistsException("Profile with EmailId "+profile.getUserEmail()+" already exists, therefore cannot update this profile");
				}
			}
		}
		boolean blankEmail = profile.getUserEmail().isBlank();
		boolean blankMobileNo = profile.getUserMobNo().isBlank();
		boolean blankUserName = profile.getUserName().isBlank();
		Profile existingProfile = profileRepository.findByProfileId(profile.getProfileId()).get();

		if (!blankEmail) {
            existingProfile.setUserEmail(profile.getUserEmail());
        }
        if (!blankMobileNo) {
            existingProfile.setUserMobNo(profile.getUserMobNo());
        }
        if (!blankUserName) {
            existingProfile.setUserName(profile.getUserName());
        }
		return profileRepository.save(existingProfile);
	}

		

}

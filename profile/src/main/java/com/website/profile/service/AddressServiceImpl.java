package com.website.profile.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.website.profile.entity.Address;
import com.website.profile.entity.Profile;
import com.website.profile.exception.ResourceNotFoundException;
import com.website.profile.repository.AddressRepository;
import com.website.profile.repository.ProfileRepository;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private ProfileRepository profileRepository;

	

	@Override
	public Address saveAddressByProfileId(Address address) throws ResourceNotFoundException {
		Address addressObj = new Address();
		addressObj.setHouseNo(address.getHouseNo());
		addressObj.setStreet(address.getStreet());
		addressObj.setLocality(address.getLocality());
		addressObj.setCity(address.getCity());
		addressObj.setState(address.getState());
		addressObj.setPincode(address.getPincode());
		addressObj.setProfileId(address.getProfileId());
		Optional<Profile> findProfileId = profileRepository.findByProfileId(address.getProfileId());
		if(findProfileId.isPresent()) {
			return addressRepository.save(addressObj);
		}else {
			throw new ResourceNotFoundException("Profile with profileId: " + address.getProfileId()+"Not Found and therefore address cannot be inserted into the database.");
		}
	}

	@Override
	public Optional<Address> getAddressById(Long addressId) {
		return addressRepository.findById(String.valueOf(addressId));
	}

	@Override
	public List<Address> getAllAddressByProfileId(Long profileId) throws ResourceNotFoundException {
		Optional<Profile> findProfileId = profileRepository.findByProfileId(profileId);
		if(findProfileId.isPresent()) {
			return addressRepository.getAllAddressByProfileId(profileId);
		}else {
			throw new ResourceNotFoundException("Profile with profileId: " + profileId + "  Not Found.");
		}
	}

	@Override
	public void deleteAddress(Long addressId) throws ResourceNotFoundException {
		addressRepository.deleteById(String.valueOf(addressId));
	}

	@Override
	public Address updateAddress(Address addressObj) {
		return addressRepository.save(addressObj);
	}

}

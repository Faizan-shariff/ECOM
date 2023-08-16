package com.website.profile.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.website.profile.entity.Address;
import com.website.profile.exception.ResourceNotFoundException;
import com.website.profile.service.AddressService;



@RestController
@CrossOrigin
@RequestMapping("/api/address")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@PostMapping("/addAddress")
	public ResponseEntity<Address> saveAddressByProfileId(@RequestBody Address address)
			throws NullPointerException, ResourceNotFoundException {
		Address savedAddress = addressService.saveAddressByProfileId(address);
		return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
	}
	
	@GetMapping("/getAllAddressByProfileId/{profileId}")
	public ResponseEntity<List<Address>> getAllAddressByProfileId(@PathVariable("profileId") Long profileId)
			throws ResourceNotFoundException {
		List<Address> savedAddress = addressService.getAllAddressByProfileId(profileId);
		return new ResponseEntity<>(savedAddress, HttpStatus.OK);
	}
	
	@PutMapping("/updateAddress")
	public ResponseEntity<Address> updateAddress(@RequestBody Address address) throws ResourceNotFoundException {

		Address addressObj = addressService.getAddressById(address.getAddressId()).orElseThrow(() -> {
			return new ResourceNotFoundException(
					"Profile with ProfileId: " + address.getProfileId() + " and AddressId: "
							+ address.getAddressId() + " not found and therefore address cannot be updated.");
		});
		addressObj.setHouseNo(address.getHouseNo());
		addressObj.setStreet(address.getStreet());
		addressObj.setLocality(address.getLocality());
		addressObj.setCity(address.getCity());
		addressObj.setState(address.getState());
		addressObj.setPincode(address.getPincode());

		final Address updatedAddress = addressService.updateAddress(addressObj);
		return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteAddress/{profileId}/{addressId}")
	public ResponseEntity<String> deleteAddress(@PathVariable("profileId") Long profileId,
			@PathVariable("addressId") Long addressId) throws ResourceNotFoundException {

		addressService.getAddressById(addressId).orElseThrow(() -> {
			return new ResourceNotFoundException("Profile with ProfileId: " + profileId + " and AddressId: " + addressId
					+ " not found and therefore address cannot be deleted. ");
		});

		addressService.deleteAddress(addressId);
		return new ResponseEntity<String>("Address deleted successfully.!", HttpStatus.OK);
	}

	@GetMapping("/getById/{addressId}")
	public ResponseEntity<Address> getAddressByAddressId(@PathVariable("addressId") Long addressId)
			throws ResourceNotFoundException {
		addressService.getAddressById(addressId).orElseThrow(() -> {
			return new ResourceNotFoundException("Address with AddressId: " + addressId + " Not Found. ");
		});

		return addressService.getAddressById(addressId).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	

}

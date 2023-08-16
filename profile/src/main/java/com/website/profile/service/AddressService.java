package com.website.profile.service;

import java.util.List;
import java.util.Optional;

import com.website.profile.entity.Address;
import com.website.profile.exception.ResourceNotFoundException;

public interface AddressService {
	
	public Address saveAddressByProfileId(Address address)throws ResourceNotFoundException;
	
	public Optional<Address> getAddressById(Long addressId);
	
	public List<Address> getAllAddressByProfileId(Long profileId)throws ResourceNotFoundException;
	
	public void deleteAddress(Long addressId)throws ResourceNotFoundException;
	
	public Address updateAddress(Address addressObj);	

}

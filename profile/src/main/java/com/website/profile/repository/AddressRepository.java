package com.website.profile.repository;

import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.website.profile.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,String>{
	
	@Query(value="SELECT * FROM address where profile_id = ?1",nativeQuery=true)
	public List<Address> getAllAddressByProfileId(Long profileId);
	
	@SQLDelete(sql="DELETE FROM address where profile_id = ?1")
	public void deleteAllAddressByProfileId(Long profileId);
	
	@Query(value="SELECT * FROM address where profile_id = ?1 AND address_id=?2",nativeQuery=true)
	public Optional<Address> findAddressByProfileIdAndAddressId(Long profileId, Long addressId);
	

}

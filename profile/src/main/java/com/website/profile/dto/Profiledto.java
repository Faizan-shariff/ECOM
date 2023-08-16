package com.website.profile.dto;

import java.util.List;

import com.website.profile.entity.Address;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profiledto {
	
	private Long profileId;
	
	@NotEmpty(message="User Name should not be empty!")
	private String userName;
	
	private String userGender;
	
	@Email
	@NotEmpty(message="User Email Should not be Empty!!")
	private String userEmail;
	
	private String userMobNo;
	
	private String userPassword;
	
	private List<Address> address;
	
}

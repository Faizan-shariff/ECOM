package com.website.profile.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address {
	
	@Id
	@Column(name="address_id")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long addressId;
	
	@Column(name="profile_id",nullable=false)
	private Long profileId;
	
	@Column(name="house_no",nullable=false)
	private String houseNo;
	
	@Column(name="street",nullable=false)
	private String street;
	
	@Column(name="locality",nullable=false)
	private String locality;
	
	@Column(name="city",nullable=false)
	private String city;
	
	@Column(name="state",nullable=false)
	private String state;
	
	@Column(name="pincode",nullable=false)
	private int pincode;
	

}

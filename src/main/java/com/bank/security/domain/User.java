package com.bank.security.domain;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.bank.security.entity.Gender;
import com.bank.security.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

	private Long id;

	@NotNull(message="The first name must not be null")
	private String firstName;

	@NotNull(message="The last name must not be null")
	private String lastName;

	@NotNull(message="The last name must not be null")
	private String username;


	@NotNull(message="The gender must be FEMALE or MALE")
	private Gender gender;

	@NotNull(message="The country must not be null")
	private String country;
	
	@NotNull(message="The city must not be null")
	private String city;
	
	@NotNull(message="The address must not be null")
	private String address;

	@Email(message="Enter correct email")
	private String email;
	
	@NotNull(message="The password must not be null")
	private String password;

	private List<Role> roles;

}

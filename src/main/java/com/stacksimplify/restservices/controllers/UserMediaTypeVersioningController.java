package com.stacksimplify.restservices.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.dtos.UserDtoV1;
import com.stacksimplify.restservices.dtos.UserDtoV2;
import com.stacksimplify.restservices.dtos.UserMmDto;
import com.stacksimplify.restservices.entities.Users;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/versioning/mediatype/Users")
public class UserMediaTypeVersioningController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//Request Parameter based Versioning - V1
	@GetMapping(value="/{id}" , produces="application/vnd.stacksimplify.app-v1+json")
	public UserDtoV1 getUserById(@PathVariable("id") @Min(1)Long id) throws UserNotFoundException{		
		
		Optional<Users> userOptional = userService.getUserById(id);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		Users user = userOptional.get();
		
		UserDtoV1 userDtoV1 = modelMapper.map(user, UserDtoV1.class);
		return userDtoV1;
	}
	
	//Request Parameter based Versioning - V2
		@GetMapping(value="/{id}" ,produces="application/vnd.stacksimplify.app-v2+json")
		public UserDtoV2 getUserById2(@PathVariable("id") @Min(1)Long id) throws UserNotFoundException{		
			
			Optional<Users> userOptional = userService.getUserById(id);
			if(!userOptional.isPresent()) {
				throw new UserNotFoundException("User not found");
			}
			Users user = userOptional.get();
			
			UserDtoV2 userDtoV2 = modelMapper.map(user, UserDtoV2.class);
			return userDtoV2;
		}
}

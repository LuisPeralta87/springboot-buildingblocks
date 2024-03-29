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

import com.stacksimplify.restservices.dtos.UserMmDto;
import com.stacksimplify.restservices.entities.Users;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/modelmapper/Users")
public class UserModelMapperController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/{id}")
	public UserMmDto getUserById(@PathVariable("id") @Min(1)Long id) throws UserNotFoundException{		
		
		Optional<Users> userOptional = userService.getUserById(id);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		Users user = userOptional.get();
		
		UserMmDto userMmDto = modelMapper.map(user, UserMmDto.class);
		return userMmDto;
	}
}

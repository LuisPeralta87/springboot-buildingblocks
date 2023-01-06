package com.stacksimplify.restservices.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;
import com.stacksimplify.restservices.entities.Users;
import com.stacksimplify.restservices.entities.Views;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

import jakarta.validation.constraints.Min;

@RestController
@Validated
@RequestMapping(value="/jsonview/Users")
public class UserJsonViewController {

	//Autowire the User Service
		@Autowired
		private UserService userService;
	
	//Get User by ID - External
		@JsonView(Views.External.class)
		@GetMapping("/external/{id}")
		public Optional<Users> getUserById(@PathVariable("id") @Min(1)Long id){		
			try {
				return userService.getUserById(id);
			} catch (UserNotFoundException ex) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
			}
		}
		
		//Get User by ID - Internal
			@JsonView(Views.Internal.class)
			@GetMapping("/internal/{id}")
			public Optional<Users> getUserById2(@PathVariable("id") @Min(1)Long id){		
				try {
					return userService.getUserById(id);
				} catch (UserNotFoundException ex) {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
				}
			}
}

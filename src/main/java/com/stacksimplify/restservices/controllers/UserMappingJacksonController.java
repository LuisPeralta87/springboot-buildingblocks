package com.stacksimplify.restservices.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.stacksimplify.restservices.entities.Users;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value="jacksonfilter/users")
@Validated
public class UserMappingJacksonController {

	//Autowire the User Service
		@Autowired
		private UserService userService;
		
	//Get User by ID - Fields with Hash Set
		@GetMapping("/{id}")
		public MappingJacksonValue getUserById(@PathVariable("id") @Min(1)Long id){		
			try {
				
				Optional<Users> userOptional = userService.getUserById(id);
				Users user = userOptional.get();
				
				Set<String> fields = new HashSet<String>();
				fields.add("userId");
				fields.add("username");
				fields.add("ssn");
				fields.add("orders");
				FilterProvider filterProvider = new SimpleFilterProvider()
						.addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields));
				MappingJacksonValue mapper = new MappingJacksonValue(user);
				mapper.setFilters(filterProvider);
				return mapper;
				
			} catch (UserNotFoundException ex) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
			}
		}
		
		//Get User by ID - Fields with @RequestParam
				@GetMapping("/params/{id}")
				public MappingJacksonValue getUserById2(@PathVariable("id") @Min(1)Long id,
						@RequestParam Set<String> fields){		
					try {
						
						Optional<Users> userOptional = userService.getUserById(id);
						Users user = userOptional.get();
						
						FilterProvider filterProvider = new SimpleFilterProvider()
								.addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields));
						MappingJacksonValue mapper = new MappingJacksonValue(user);
						mapper.setFilters(filterProvider);
						return mapper;
						
					} catch (UserNotFoundException ex) {
						throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
					}
				}
}

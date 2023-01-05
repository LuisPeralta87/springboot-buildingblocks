package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.stacksimplify.restservices.entities.Users;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

//Controler
@RestController
public class UserController {

	//Autowire the User Service
	@Autowired
	private UserService userService;
	
	//getAllUsers method
	@GetMapping("/Users")
	public List<Users> getAllUsers(){
		return userService.getAllUsers();
	}
	
	//Create User
	//@RequestBody Annotation
	//@PostMapping Annotation
	@PostMapping("/Users")
	public ResponseEntity<Void> createUser(@RequestBody Users user, UriComponentsBuilder builder) {
		try {
			 userService.createUser(user);
			 HttpHeaders headers = new HttpHeaders();
			 headers.setLocation(builder.path("/Users/{id}").buildAndExpand(user.getId()).toUri());
			 return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} catch (UserExistsException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	//Get User by ID
	@GetMapping("/Users/{id}")
	public Optional<Users> getUserById(@PathVariable("id") Long id){		
		try {
			return userService.getUserById(id);
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	
	//Update User by ID
	@PutMapping("/Users/{id}")
	public Users updateUserById(@PathVariable("id")Long id, @RequestBody Users user ) {
		
		try {
			return userService.updateUserById(id, user);
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	//Delete User by ID
	@DeleteMapping("/Users/{id}")
	public void deleteUserById(@PathVariable("id")Long id) {
		userService.deleteUserById(id);
	}
	
	//Get User by Username
	@GetMapping("/Users/byusername/{username}")
	public Users getUserByUsername(@PathVariable("username")String username) {
		return userService.getUserByUsername(username);
	}
}

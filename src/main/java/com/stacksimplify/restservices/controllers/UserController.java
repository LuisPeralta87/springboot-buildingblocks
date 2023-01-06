package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.stacksimplify.restservices.entities.Users;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNameNotFoundException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

//Controller
@RestController
@Validated
@RequestMapping(value="/Users")
public class UserController {

	//Autowire the User Service
	@Autowired
	private UserService userService;
	
	//getAllUsers method
	@GetMapping
	public List<Users> getAllUsers(){
		return userService.getAllUsers();
	}
	
	//Create User
	//@RequestBody Annotation
	//@PostMapping Annotation
	@PostMapping
	public ResponseEntity<Void> createUser(@Valid @RequestBody Users user, UriComponentsBuilder builder) {
		try {
			 userService.createUser(user);
			 HttpHeaders headers = new HttpHeaders();
			 headers.setLocation(builder.path("/{id}").buildAndExpand(user.getId()).toUri());
			 return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} catch (UserExistsException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	//Get User by ID
	@GetMapping("/{id}")
	public Optional<Users> getUserById(@PathVariable("id") @Min(1)Long id){		
		try {
			return userService.getUserById(id);
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	
	//Update User by ID
	@PutMapping("/{id}")
	public Users updateUserById(@PathVariable("id")Long id, @RequestBody Users user ) {
		
		try {
			return userService.updateUserById(id, user);
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	//Delete User by ID
	@DeleteMapping("/{id}")
	public void deleteUserById(@PathVariable("id")Long id) {
		userService.deleteUserById(id);
	}
	
	//Get User by Username
	@GetMapping("/byusername/{username}")
	public Users getUserByUsername(@PathVariable("username")String username) throws UserNameNotFoundException{
		Users user = userService.getUserByUsername(username);
		if(user == null) {
			throw new UserNameNotFoundException("Username: ´"+ username + "´ not found un User Repository");
		}	
		return user;
	}
}

package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.entities.Users;
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
	public Users createUser(@RequestBody Users user) {
		return userService.createUser(user);
	}
	
	//Get User by ID
	@GetMapping("/Users/{id}")
	public Optional<Users> getUserById(@PathVariable("id") Long id){
		return userService.getUserById(id);
	}
	
	//Update User by ID
	@PutMapping("/Users/{id}")
	public Users updateUserById(@PathVariable("id")Long id, @RequestBody Users user ) {
		return userService.updateUserById(id, user);
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

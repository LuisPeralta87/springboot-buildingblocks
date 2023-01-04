package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stacksimplify.restservices.entities.Users;
import com.stacksimplify.restservices.repositories.UserRepository;

//Service
@Service
public class UserService {
	
	//AutoWire the User services
	@Autowired
	private UserRepository userRepository;
	
	//Get all users method
	public List<Users> getAllUsers(){
		return userRepository.findAll();
	}
	
	//CreateUser method
	public Users createUser(Users user) {
		return userRepository.save(user);
	}

	//Get User by ID
	public Optional<Users> getUserById(Long UserId) {
		Optional<Users> user = userRepository.findById(UserId);
		return user;
	}
	
	//Update User by ID
	public Users updateUserById(Long id, Users user) {
		user.setId(id);
		return userRepository.save(user);
	}
	
	//Delete User by ID
	public void deleteUserById(Long id) {
		if(userRepository.existsById(id)) {
			userRepository.deleteById(id);
		}
	}
	
	//Get User by Username
	public Users getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}

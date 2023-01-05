package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.Users;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
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
	public Users createUser(Users user) throws UserExistsException{
		//Check if user exists using user name
		Users existingUser = userRepository.findByUsername(user.getUsername());
		if(existingUser != null) {
			throw new UserExistsException("User already exists in Repository");
		}
		//If not throw UserExistsException
		return userRepository.save(user);
	}

	//Get User by ID
	public Optional<Users> getUserById(Long UserId) throws UserNotFoundException{
		Optional<Users> user = userRepository.findById(UserId);
		if(!user.isPresent()) {
			throw new UserNotFoundException("User not found in User Repository");
		}
		return user;
	}
	
	//Update User by ID
	public Users updateUserById(Long UserId, Users user) throws UserNotFoundException {
		Optional<Users> optionaluser = userRepository.findById(UserId);
		
		if(!optionaluser.isPresent()) {
			throw new UserNotFoundException("User not found in User Repository, Provide a correct user Id");
		}
		
		user.setId(UserId);
		return userRepository.save(user);
	}
	
	//Delete User by ID
	public void deleteUserById(Long UserId){
		Optional<Users> optionaluser = userRepository.findById(UserId);
		
		if(!optionaluser.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found in User Repository, Provide a correct user Id");
		}
		
		userRepository.deleteById(UserId);
	}
	
	//Get User by Username
	public Users getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
}

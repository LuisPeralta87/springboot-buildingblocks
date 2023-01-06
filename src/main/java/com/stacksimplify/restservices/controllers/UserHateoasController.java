package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.Users;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.UserRepository;
import com.stacksimplify.restservices.services.UserService;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class UserHateoasController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	//getAllUsers method
	@GetMapping
	public CollectionModel<Users> getAllUsers() throws UserNotFoundException {
		List<Users> allusers= userService.getAllUsers();
		
		for(Users user : allusers) {
			//Self Link
			Long userid = user.getUserId();
			Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
			user.add(selfLink);
			
			//Relationship link with getAllOrders
			CollectionModel<Order> orders =   WebMvcLinkBuilder.methodOn(OrderHateoasController.class).getAllOrders(userid);
			Link ordersLink = WebMvcLinkBuilder.linkTo(orders).withRel("all-orders");
			user.add(ordersLink);
		}
		//Self Link for GetAllUsers
		Link selfLinkAllUsers = WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();
		
		CollectionModel<Users> finalResource =  CollectionModel.of(allusers,selfLinkAllUsers);
		return finalResource;
	}
		
	//Get User by ID
	@GetMapping("/{id}")
	public EntityModel<Users> getUserById(@PathVariable("id") @Min(1)Long id){		
		try {
			Optional<Users> optinalUser = userService.getUserById(id);
			Users user = optinalUser.get();
			Long userId = user.getUserId();
			Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
			user.add(selfLink);
			EntityModel<Users> finalResource =  EntityModel.of(user);
			return finalResource;
			
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
}

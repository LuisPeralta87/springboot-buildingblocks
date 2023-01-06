package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.Users;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.OrderRepository;
import com.stacksimplify.restservices.repositories.UserRepository;

@RestController
@RequestMapping(value="/Users")
public class OrderController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	//Get all orders for a User
	@GetMapping("/{userid}/orders")
	public List<Order> getAllOrders(@PathVariable Long userid) throws UserNotFoundException{
		Optional<Users> userOptional = userRepository.findById(userid);
		
		if(!userOptional.isPresent()){
			throw new UserNotFoundException("User not found");
		}
		return userOptional.get().getOrders();
	}
	
	//Create Order Method
	@PostMapping("{userid}/orders")
	public Order createOrder(@PathVariable Long userid, @RequestBody Order order) throws UserNotFoundException {	
		Optional<Users> userOptional = userRepository.findById(userid);
		
		if(!userOptional.isPresent()){
			throw new UserNotFoundException("User not found");
		}
		Users user = userOptional.get();
		order.setUser(user);
		return orderRepository.save(order);
	}
	
	//Get Order By Id
	@GetMapping("/{userid}/orders/{orderid}")
	public Optional<Order> getOrderByOrderId(@PathVariable Long userid,@PathVariable("orderid") Long orderid) throws UserNotFoundException {
		Optional<Users> userOptional = userRepository.findById(userid);
		Optional<Order> orderOptional = orderRepository.findById(orderid);
		
		if(!userOptional.isPresent() | !orderOptional.isPresent()){
			throw new UserNotFoundException("User not found");
		}
		return orderRepository.findById(orderid);
	}
}

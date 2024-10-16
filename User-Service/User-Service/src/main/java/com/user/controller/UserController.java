package com.user.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.User;
import com.user.model.OrderReport;
import com.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/add")
	public User addUser( @Valid @RequestBody User  user) {
		return userService.createUser(user);
		
	}
	@GetMapping("/all")
	public List<User> getAllUser(){
		return userService.getaAllUser();
	}
	@GetMapping("/{userId}")
	public User getUserById(String userId){
	return userService.getUserById(userId);
	
	}
	@PostMapping("/order")
	public OrderReport order(@RequestBody OrderReport orderReport) {
		return userService.order(orderReport);
	}
}

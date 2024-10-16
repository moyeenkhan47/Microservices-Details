package com.user.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.configuration.FeignClientCall;
import com.user.entity.User;
import com.user.exception.ResourceNotFoundException;
import com.user.model.OrderReport;
import com.user.repository.UserRepository;
import com.user.service.UserService;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FeignClientCall feignClientCall;
	
	@Autowired
	private CircuitBreakerRegistry breakerRegistry;

	@Override
	public User createUser(User user) {
		return userRepository.save(user);

	}

	@Override
	public List<User> getaAllUser() {
		List<User> all = userRepository.findAll();
		return all;
	}

	@Override
	public User getUserById(String userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource with ID " + userId + " not found"));
		

	}

	@Override
	@CircuitBreaker(name = "userService" , fallbackMethod = "fallBackMethod")
	public OrderReport order(OrderReport orderReport) {
		Optional<User> user = userRepository.findById(orderReport.getUserId());
		orderReport.setEmail(user.get().getEmail());
		orderReport.setFullName(user.get().getFullName());
		return feignClientCall.order(orderReport);
	}
	
	public OrderReport fallBackMethod(OrderReport orderReport,Throwable throwable) {
		System.out.println("Circuite breaker activated. Error:"+throwable.getMessage());
		io.github.resilience4j.circuitbreaker.CircuitBreaker cb = breakerRegistry.circuitBreaker("userService");
		String state = cb.getState().toString();

		OrderReport fallBackOrder=new OrderReport();
		fallBackOrder.setUserId(orderReport.getUserId());
		fallBackOrder.setFullName(state);
		fallBackOrder.setEmail(state);
		return fallBackOrder;
	}

}

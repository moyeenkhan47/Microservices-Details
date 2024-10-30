package com.user.service.Impl;

import java.util.List;
import java.util.Optional;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.user.configuration.FeignClientCall;
import com.user.entity.User;
import com.user.exception.RateLimitExceededException;
import com.user.exception.ResourceNotFoundException;
import com.user.model.OrderReport;
import com.user.repository.UserRepository;
import com.user.service.UserService;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private FeignClientCall feignClientCall;

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
	@RateLimiter(name = "userServiceRateLimiter", fallbackMethod = "rateLimitFallback")
	@CircuitBreaker(name = "userService", fallbackMethod = "fallBackMethod")
	public OrderReport order(OrderReport orderReport) {
		try {
			// Attempt to retrieve the user
			Optional<User> user = userRepository.findById(orderReport.getUserId());

			// Check if user exists
			if (user.isPresent()) {
				orderReport.setEmail(user.get().getEmail());
				orderReport.setFullName(user.get().getFullName());
			}
			// Make the Feign client call
			return feignClientCall.order(orderReport);

		} catch (RateLimitExceededException e) {
			// Handle rate limit exceeded exception if applicable
			System.out.println("Rate limit exceeded: " + e.getMessage());
			return rateLimitFallback(orderReport, e);
		} catch (Exception e) {
			// Handle other exceptions that may trigger the Circuit Breaker
			System.out.println("Exception occurred: " + e.getMessage());
			return fallBackMethod(orderReport, e); // Propagate exception to trigger Circuit Breaker
		}
	}

	public OrderReport fallBackMethod(OrderReport orderReport, Throwable throwable) {
		System.out.println("Circuit breaker activated. Error: " + throwable.getMessage());
		io.github.resilience4j.circuitbreaker.CircuitBreaker cb = breakerRegistry.circuitBreaker("userService");
		String state = cb.getState().toString();

		// Create a fallback OrderReport instance
		OrderReport fallBackOrder = new OrderReport();
		fallBackOrder.setUserId(orderReport.getUserId());
		fallBackOrder.setFullName(state);
		fallBackOrder.setEmail("Service is currently unavailable, fallback response.");

		return fallBackOrder;
	}

	public OrderReport rateLimitFallback(OrderReport orderReport, Throwable throwable) {
		// Log the exception or throwable for diagnostics (optional)
		System.out.println("Rate limit exceeded: " + throwable.getMessage());

		// Create and return a fallback OrderReport instance
		OrderReport fallbackReport = new OrderReport();
		fallbackReport.setUserId(orderReport.getUserId());
		fallbackReport.setFullName("Too many requests. Please try again later.");
		fallbackReport.setEmail("Rate limit has been exceeded.");

		return fallbackReport;
	}

}

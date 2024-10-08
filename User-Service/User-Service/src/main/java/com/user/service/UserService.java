package com.user.service;

import java.util.List;

import com.user.entity.User;
import com.user.model.OrderReport;

public interface UserService {
	public User createUser(User user);
	public User getUserById(String userId);
	public List<User> getaAllUser();
	OrderReport order(OrderReport orderReport);
}

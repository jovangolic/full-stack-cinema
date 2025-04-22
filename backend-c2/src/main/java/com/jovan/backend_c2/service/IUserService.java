package com.jovan.backend_c2.service;

import java.util.List;


import com.jovan.backend_c2.model.User;


public interface IUserService {

	User registerUser(User user);
	List<User> getUsers();
	void deleteUser(String email);
	User getUser(String email);

    public User saveUser(User user);

    public User getUserByIdentifier(String identifier);
    public User getUserByEmail(String email);
}

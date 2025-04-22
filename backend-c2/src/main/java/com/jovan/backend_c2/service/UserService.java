package com.jovan.backend_c2.service;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jovan.backend_c2.exception.NoSuchElementException;
import com.jovan.backend_c2.exception.UserAlreadyExistsException;
import com.jovan.backend_c2.exception.UserInvalidException;
import com.jovan.backend_c2.model.Role;
import com.jovan.backend_c2.model.User;
import com.jovan.backend_c2.repository.RoleRepository;
import com.jovan.backend_c2.repository.UserRepository;
import com.jovan.backend_c2.util.ApiMessages;
import com.jovan.backend_c2.util.ValidationUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private final UserRepository userRepository;
	
	private final RoleRepository roleRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final ValidationUtil validationUtil;
	
	/*@Override
	public User registerUser(User user) {
		//da li postoji korisnik
		if (userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(userRole));
        return userRepository.save(user);
	}*/

	@Override
	public User registerUser(User user) {
		logger.info("Registering user with email: {}", user.getEmail());
		// Da li postoji korisnik
		if (userRepository.existsByEmail(user.getEmail())){
            logger.warn("User with email {} already exists", user.getEmail());
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        
        // Pronađi rolu ili baci izuzetak ako ne postoji
        Role userRole = roleRepository.findByName("ROLE_USER")
            .orElseThrow(() -> {
                logger.error("Role ROLE_USER not found");
                return new NoSuchElementException("Role not found");
            });
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.debug("Encoded password for user with email: {}", user.getEmail());
        user.setRoles(Collections.singletonList(userRole));
        
        User savedUser = userRepository.save(user);
        logger.info("User with email {} successfully registered", user.getEmail());
        return savedUser;
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Transactional
	@Override
	public void deleteUser(String email) {
		User theUser = getUser(email);
		if(theUser != null) {
			userRepository.deleteByEmail(email);
		}
		
	}

	@Override
	public User getUser(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	
	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User getUserByIdentifier(String identifier) {
		User user = null;

        if (validationUtil.doesEmailExist(identifier)) {
            user = getUserByEmail(identifier);
        } 
        else {
            throw new UserInvalidException(
                    String.format(ApiMessages.USER_NOT_FOUND_BY_IDENTIFIER.getMessage(), identifier));
        }

        return user;
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(
                () -> new UserInvalidException(String.format(ApiMessages.USER_NOT_FOUND_BY_EMAIL.getMessage(), email)));
	}

}
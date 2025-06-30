package com.tcs.user.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcs.user.entity.User;
import com.tcs.user.repository.UserRepository;
import com.tcs.user.security.JwtUtil;

@Service
public class UserService {

	@Autowired
	UserRepository repo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtil jwtUtil;
	
	public User registerUser(User user) {
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);
		return repo.save(user);
	}
	
	public User updateUser(String authHeader, User user) {
		String token = authHeader.substring(7);
		String email = jwtUtil.extractUsername(token);
		User org_user = repo.findByEmail(email);
		org_user.setName(user.getName());
		org_user.setEmail(user.getEmail());
		org_user.setPhone(user.getPhone());
		org_user.setPincode(user.getPincode());
		org_user.setAddress(user.getAddress());
		org_user.setCreated_Date(org_user.getCreated_Date());
		return repo.save(org_user);
	}
	
	public Iterable<User> getallUsers(){
		return repo.findAll();
	}
}

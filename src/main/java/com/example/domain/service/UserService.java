package com.example.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.model.User;
import com.example.domain.repository.UserRepository;

@Service("userService")
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<User> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> findUserByResetToken(String resetToken) {
		return userRepository.findByResetToken(resetToken);
	}

	public void save(User user) {
		userRepository.save(user);
	}
}
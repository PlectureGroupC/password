package com.example.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	 Optional<User> findByEmail(String email);
	 Optional<User> findByResetToken(String resetToken);
}
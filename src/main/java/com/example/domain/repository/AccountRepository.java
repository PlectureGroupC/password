package com.example.domain.repository;

import com.example.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer>{
	Account findByUsername(String username);
	Optional<Account> findByMailAddress(String email);
	Optional<Account> findByResetToken(String resetToken);
}
package com.example.domain.repository;

import com.example.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer>{
	Account findByUsername(String username);
}
package com.example.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Hash;
import com.example.domain.model.Account;
import com.example.domain.repository.AccountRepository;

import java.util.Optional;

@Service
@Transactional
public class AccountService {
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
    PasswordEncoder passwordEncoder;

	public Optional<Account> findUserByEmail(String email){
		return accountRepository.findByMailAddress(email);
	}

	public Optional<Account> findUserByResetToken(String resetToken){
		return accountRepository.findByResetToken(resetToken);
	}
	
	public Account findOne(Integer id){
		return accountRepository.findOne(id);
	}
	
	public Account create(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
		    account.setImgHash(Hash.getHash(account.getImgHash()));
        return accountRepository.saveAndFlush(account);
    }
	
	public Account save(Account account) {
		return accountRepository.save(account);
	}
	
	public void delete(Integer id) {
		accountRepository.delete(id);
	}
}

package com.example.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.model.Account;
import com.example.domain.repository.AccountRepository;

@Service
@Transactional
public class AccountService {
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	public Account findOne(Integer id){
		return accountRepository.findOne(id);
	}
	
	public Account create(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setImgHash(passwordEncoder.encode(account.getImgHash()));
        return accountRepository.saveAndFlush(account);
    }
	
	public Account update(Account account) {
		return accountRepository.save(account);
	}
	
	public void delete(Integer id) {
		accountRepository.delete(id);
	}
}

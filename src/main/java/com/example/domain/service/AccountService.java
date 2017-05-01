package com.example.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.model.Account;
import com.example.domain.repository.AccountRepository;

@Service
@Transactional
public class AccountService {
	@Autowired
	AccountRepository accountRepository;
	
	public Account findOne(Integer id){
		return accountRepository.findOne(id);
	}
	
	public Account create(Account account) {
		return accountRepository.save(account);
	}
	
	public Account update(Account account) {
		return accountRepository.save(account);
	}
	
	public void delete(Integer id) {
		accountRepository.delete(id);
	}
}

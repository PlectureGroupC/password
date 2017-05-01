package com.example.domain.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import com.example.domain.model.Account;

import lombok.Data;

@Data
public class AccountUserDetails extends User{
	private static final long serialVersionUID = 1L;
	private final Account account;
	
	public AccountUserDetails(Account account) {
		super(account.getUsername(), account.getPassword(), 
				AuthorityUtils.createAuthorityList("ROLE_USER"));
		this.account = account;
	}
}

package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.domain.model.Account;
import com.example.domain.service.AccountService;
import com.example.form.AccountForm;

@Controller
@RequestMapping("account")
public class AccountController {
	@Autowired
	AccountService accountService;
	
	@ModelAttribute
	AccountForm setUpForm() {
		return new AccountForm();
	}
	
	@RequestMapping(value="create", method=RequestMethod.POST)
	String create(@Validated AccountForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "/account/create";
		}
		Account account = new Account();
		BeanUtils.copyProperties(form, account);
		accountService.create(account);
		return "redirect:/index";
	}
	
	@RequestMapping(value="create", method=RequestMethod.GET)
	String create(Model model) {
		model.addAttribute("form", new AccountForm());
		return "/account/create";
	}
}

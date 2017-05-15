package com.example.controller;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

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
		if(!form.getMailAddress().equals(form.getConfirmMail())){
			model.addAttribute("mailError", true);
			model.addAttribute("mailErrorMessage", "MailとMail(確認用)の入力が一致しません");
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
	
	@RequestMapping(value="forgotPass", method=RequestMethod.GET)
	String forgotPass(Model model) {
		model.addAttribute("checkItems", CHECK_ITEMS);
		return "/account/forgotPassword";
	}
	
	@RequestMapping(value="forgotPass", method=RequestMethod.POST)
	String forgotPass(@ModelAttribute AccountForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "/account/forgotPass";
		}
		return "";
	}
	
	final static Map<String, String> CHECK_ITEMS =
		    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
				private static final long serialVersionUID = 178167752967848875L;
				{
					put("/images/img1.png", "1");
					put("/images/img2.png", "2");
					put("/images/img3.png", "3");
					put("/images/img4.png", "4");
					put("/images/img5.png", "5");
					put("/images/img6.png", "6");
					put("/images/img7.png", "7");
					put("/images/img8.png", "8");
					put("/images/img9.png", "9");
				}
		    });
}

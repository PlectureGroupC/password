package com.example.controller;

import java.util.*;

import com.example.domain.service.ImageService;
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

	@Autowired
	ImageService imageService;
	
	@ModelAttribute
	AccountForm setUpForm() {
		return new AccountForm();
	}

	@ModelAttribute("checkItems")
	private Map<String, String> getCheckItems(){
		final Map<String, String> CHECK_ITEMS =
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
		return CHECK_ITEMS;
	}
	
	@RequestMapping(value="create", method=RequestMethod.POST)
	//account生成の時に呼び出す
	String create(@Validated AccountForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("error", true);
			model.addAttribute("errorMessage","入力に不備があります。再度入力してください。");
			return "/account/create";
		}
		if(!form.getMailAddress().equals(form.getConfirmMail())){
			model.addAttribute("error", true);
			model.addAttribute("errorMessage", "EMailとEMail(確認用)の入力が一致しません");
			return "/account/create";
		}
		else if(!form.getPassword().equals(form.getConfirmPass())){
			model.addAttribute("error", true);
			model.addAttribute("errorMessage","PasswordとPassword(確認用)の入力が一致しません");
			return "/account/create";
		}
		else if(form.getImgPath().size() != 3){
			model.addAttribute("error", true);
			model.addAttribute("errorMessage", "Password復元時に使用する画像を3枚選択してください");
			return "/account/create";
		}
		Account account = new Account();
		String hashSeed = "";
		for(String path: form.getImgPath()){
			//Image image = imageService.findImageByImgName(path);
			//hashSeed = hashSeed + image.getSeed();
			hashSeed = hashSeed + imageService.findImgSeedByImgName(path);
			//hashSeed = hashSeed + path;
		}
		BeanUtils.copyProperties(form, account);
		account.setImgHash(hashSeed);
		accountService.create(account);
		return "redirect:/index";
	}
	
	@RequestMapping(value="create", method=RequestMethod.GET)
	//初期画面からaccount生成画面への遷移
	String create(Model model) {
		model.addAttribute("form", new AccountForm());
		return "/account/create";
	}
	
	@RequestMapping(value="forgotPass", method=RequestMethod.GET)
	String forgotPass(Model model) {
		return "/account/forgotPassword";
	}
	
	@RequestMapping(value="forgotPass", method=RequestMethod.POST)
	String forgotPass(@ModelAttribute AccountForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "/account/forgotPass";
		}
		return "";
	}
}

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
		List<String> pathList = new LinkedList<String>();
		pathList.add("/images/img1.png");
		pathList.add("/images/img2.png");
		pathList.add("/images/img3.png");
		pathList.add("/images/img4.png");
		pathList.add("/images/img5.png");
		pathList.add("/images/img6.png");
		pathList.add("/images/img7.png");
		pathList.add("/images/img8.png");
		pathList.add("/images/img9.png");
		pathList.add("/images/img10.png");
		pathList.add("/images/img11.png");
		pathList.add("/images/img12.png");
		pathList.add("/images/img13.png");
		pathList.add("/images/img14.png");
		pathList.add("/images/img15.png");
		pathList.add("/images/img16.png");
		pathList.add("/images/img17.png");
		pathList.add("/images/img18.png");
		Collections.shuffle(pathList);
		final Map<String, String> CHECK_ITEMS =
				Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
					private static final long serialVersionUID = 178167752967848875L;
					{
						for(int i = 0; i < pathList.size(); i++){
							put(pathList.get(i),String.valueOf(i));
						}
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
			System.out.println(path);
			hashSeed = hashSeed + imageService.findImgSeedByImgName(path);
			//hashSeed = hashSeed + path;
		}
		BeanUtils.copyProperties(form, account);
		account.setImgHash(hashSeed);
		accountService.create(account);
		return "redirect:/home/index";
	}
	
	@RequestMapping(value="create", method=RequestMethod.GET)
	//初期画面からaccount生成画面への遷移
	String create(Model model) {
		model.addAttribute("form", new AccountForm());
		return "/account/create";
	}

	//削除の可能性start
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
  //削除の可能性end
}

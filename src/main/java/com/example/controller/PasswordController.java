package com.example.controller;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Hash;
import com.example.domain.model.Account;
import com.example.domain.model.Image;
import com.example.domain.service.AccountService;
import com.example.domain.service.EmailService;
import com.example.domain.service.ImageService;
import com.example.form.ForgotPassForm;
import com.example.form.ImageCheckForm;
import com.example.form.ResetPassForm;

@Controller
@RequestMapping("resetPass")
public class PasswordController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@ModelAttribute
	ForgotPassForm setUpForm() {
		return new ForgotPassForm();
	}
	
	@RequestMapping(value = "forgot", method = RequestMethod.GET)
	public ModelAndView displayForgotPasswordPage(ModelAndView mav) {
		mav.setViewName("/resetPass/forgot");
		return mav;
    }
    
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ModelAndView processForgotPasswordForm(@Validated ForgotPassForm form, BindingResult result, ModelAndView modelAndView, HttpServletRequest request) {
		if(result.hasErrors()){
			modelAndView.setViewName("/resetPass/forgot");
			return modelAndView;
		}
		Optional<Account> optional = accountService.findUserByEmail(form.getMailAddress());
		if (!optional.isPresent()) {
			modelAndView.addObject("accountNotFoundError", true);
			modelAndView.addObject("errorMessage", "入力したメールアドレスに該当するアカウントは見つかりません");
			modelAndView.setViewName("/reset/forgot");
		} else {
			Account user = optional.get();
			user.setResetToken(UUID.randomUUID().toString());
			accountService.save(user);

			String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
			
			// Email message
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("kszkshine@google.com");
			passwordResetEmail.setTo(user.getMailAddress());
			passwordResetEmail.setSubject("パスワード再設定のお願い");
			passwordResetEmail.setText("パスワード再設定のため, 以下のリンクをクリックしてください:\n" + appUrl
					+ "/resetPass/reset?token=" + user.getResetToken());
			
			emailService.sendEmail(passwordResetEmail);
			
			modelAndView.addObject("successMessage", "A password reset link has been sent to " + form.getMailAddress());
		}
		modelAndView.setViewName("/resetPass/confirmMail");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/reset", params = "token", method = RequestMethod.GET)
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
		Map<String, String> checkItems = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
			private static final long serialVersionUID = 178167752967848875L;
			{
				for(Image img : imageService.findAll()){
					put(img.getImgName(), String.valueOf(img.getImgId()));
				}
			}
		});
		Optional<Account> account = accountService.findUserByResetToken(token);
		if (account.isPresent()) {
			modelAndView.addObject("checkItems", checkItems);
			modelAndView.addObject("resetToken", token);
		} else { 
			modelAndView.addObject("error", true);
			modelAndView.addObject("errorMessage", "申し訳ございません。無効なリンクです。");
		}
		modelAndView.setViewName("/resetPass/resetPassFirst");
		return modelAndView;
	}

	@RequestMapping(value = "reset", method = RequestMethod.POST)
	public ModelAndView authByImage(ModelAndView modelAndView, @Validated @ModelAttribute ImageCheckForm form, BindingResult result, RedirectAttributes redir) {
		modelAndView.addObject("token", form.getToken());
		if(result.hasErrors()) {
			modelAndView.addObject("error", true);
			modelAndView.addObject("errorMessage", "不正な値が入力されました。再入力してください。");
			return modelAndView;
		}

		if(form.getImgValue().size() != 3){
			modelAndView.setViewName("redirect:/resetPass/reset");
			return modelAndView;
		}

		Optional<Account> account = accountService.findUserByResetToken(form.getToken());
		String hashSeed = "";
		for(String path: form.getImgValue()){
			hashSeed = hashSeed + imageService.findImgSeedByImgName(path);
			System.out.println(hashSeed);
		}
		if(account.isPresent() && account.get().getImgHash().equals(Hash.getHash(hashSeed))){
			modelAndView.setViewName("redirect:/resetPass/resetPass");
			return modelAndView;
		}else if(account.isPresent()) {
			modelAndView.setViewName("redirect:/resetPass/reset");
		}else {
			modelAndView.setViewName("redirect:/resetPass/reset");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/resetPass", params = "token", method = RequestMethod.GET)
	public ModelAndView displayResetPassForm(ModelAndView mav, @ModelAttribute ResetPassForm form, BindingResult result, @RequestParam("token") String token) {
		Optional<Account> account = accountService.findUserByResetToken(token);
		if(account.isPresent()){
			mav.addObject("token", token);
			mav.setViewName("/resetPass/resetPassSecond");
			return mav;
		}else {
			mav.addObject("error", true);
			mav.addObject("errorMessage", "申し訳ございません。　無効なリンクです。");
			mav.setViewName("/resetPass/resetPassFirst");
			return mav;
		}
	}

	// Process reset password form
	@RequestMapping(value = "/resetPass", method = RequestMethod.POST)
	public ModelAndView setNewPassword(ModelAndView modelAndView,@ModelAttribute @Validated ResetPassForm form, BindingResult result ,RedirectAttributes redir) {
		if(result.hasErrors()) {
			modelAndView.setViewName("forgot");
			return modelAndView;
		}
		Optional<Account> user = accountService.findUserByResetToken(form.getToken());
		if (user.isPresent()) {
			Account resetUser = user.get();

			resetUser.setPassword(bCryptPasswordEncoder.encode(form.getPassword()));
			resetUser.setResetToken(null);
			accountService.save(resetUser);
			modelAndView.addObject("successMessage", "パスワードの変更が完了しました。");
			modelAndView.setViewName("/resetPass/confirmResetPass");

			return modelAndView;
		} else {
			modelAndView.addObject("error", true);
			modelAndView.addObject("errorMessage", "申し訳ございません。無効なリンクです。");
			modelAndView.setViewName("resetPassword");
		}
		return modelAndView;
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
		return new ModelAndView("redirect:login");
	}
}
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

import com.example.domain.model.User;
import com.example.domain.service.EmailService;
import com.example.domain.service.UserService;
import com.example.form.ForgotPassForm;

@Controller
public class PasswordController {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@ModelAttribute
	ForgotPassForm setUpForm() {
		return new ForgotPassForm();
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
	
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public ModelAndView displayForgotPasswordPage(ModelAndView mav) {
		mav.addObject("checkItems", CHECK_ITEMS);
		return mav;
    }
    
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ModelAndView processForgotPasswordForm(@Validated ForgotPassForm form, BindingResult result, ModelAndView modelAndView, HttpServletRequest request) {
		Optional<User> optional = userService.findUserByEmail(form.getMailAddress());
		
		if(result.hasErrors()){
			modelAndView.addObject("checkItems", CHECK_ITEMS);
			modelAndView.setViewName("forgot");
			return modelAndView;
		}
		if(form.getImgs().length != 3 || form.getImgs().length == 0){
			modelAndView.addObject("checkboxError", true);
			modelAndView.addObject("checkboxErrorMessage", "画像は3枚選択してください");
			modelAndView.addObject("checkItems", CHECK_ITEMS);
			modelAndView.setViewName("forgot");
			return modelAndView;
		}
		if (!optional.isPresent()) {
			modelAndView.addObject("checkItems", CHECK_ITEMS);
			modelAndView.addObject("accountNotFoundError", true);
			modelAndView.addObject("errorMessage", "入力したメールアドレスに該当するアカウントは見つかりません");
		} else {
			User user = optional.get();
			user.setResetToken(UUID.randomUUID().toString());
			userService.save(user);

			String appUrl = request.getScheme() + "://" + request.getServerName();
			
			// Email message
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("kszkshine@google.com");
			passwordResetEmail.setTo(user.getEmail());
			passwordResetEmail.setSubject("Password Reset Request");
			passwordResetEmail.setText("パスワード再設定のため, 以下のリンクをクリックしてください:\n" + appUrl
					+ "/reset?token=" + user.getResetToken());
			
			emailService.sendEmail(passwordResetEmail);
			
			modelAndView.addObject("successMessage", "A password reset link has been sent to " + form.getMailAddress());
		}
		modelAndView.setViewName("forgot");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
		Optional<User> user = userService.findUserByResetToken(token);

		if (user.isPresent()) {
			modelAndView.addObject("resetToken", token);
		} else { 
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
		}
		modelAndView.setViewName("resetPassword");
		return modelAndView;
	}

	// Process reset password form
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, BindingResult result ,RedirectAttributes redir) {
		if(result.hasErrors()) {
			modelAndView.setViewName("forgot");
			return modelAndView;
		}
		Optional<User> user = userService.findUserByResetToken(requestParams.get("token"));
		
		if (user.isPresent()) {
			User resetUser = user.get(); 
                  
			resetUser.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
			resetUser.setResetToken(null);
			userService.save(resetUser);
			redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");
			modelAndView.setViewName("redirect:login");
			
			return modelAndView;
		} else {
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("resetPassword");
		}
		return modelAndView;
   }
   
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
		return new ModelAndView("redirect:login");
	}
}
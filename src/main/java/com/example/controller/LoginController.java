package com.example.controller;

import com.example.domain.model.Account;
import com.example.repository.LoginRepository;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.*;

@Controller
public class LoginController {
    @Autowired
    LoginRepository repository;

    final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping("loginForm")
    public String loginForm() {
        return "/login/login-form";
    }

    @RequestMapping(value = "/home")
    public String login(@AuthenticationPrincipal Account account, Model model) {
        model.addAttribute("webInfoForm", new WebInfoForm());
        return "page/home";
    }
}

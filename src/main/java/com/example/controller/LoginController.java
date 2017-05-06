package com.example.controller;

import com.example.repository.LoginRepository;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.*;

@Controller
public class LoginController {
    @Autowired
    LoginRepository repository;

    final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(LoginForm form, Model model){
        model.addAttribute("index", new LoginForm());
        System.out.println("LoginController index throw");
        return "page/index";
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String login(@Validated @ModelAttribute LoginForm form, BindingResult result, Model model){
        System.out.println("LoginController login throw");
        System.out.println(result);
        String mailAddress = form.getMailaddress();
        System.out.println(mailAddress);
        if(result.hasErrors()){
            model.addAttribute("validationError", "不正な値");
            return index(form, model);
        }
        model.addAttribute("success", form);
        model.addAttribute("webInfoForm", new WebInfoForm());
        return "page/home";
    }
}

package com.example.controller;

import com.example.model.WebInfo;
import com.example.repository.WebInfoRepository;
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

import javax.validation.Valid;

@Controller
public class WebInfoController {
    @Autowired
    WebInfoRepository repository;

    final static Logger logger = LoggerFactory.getLogger(WebInfoController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = "/getvalue", method = RequestMethod.GET) // valueをログイン後のページのアドレスにする
    public String get(Model model){
        model.addAttribute("data", repository.findAll());
        return "page/getvalue";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(WebInfoForm form, Model model){
        model.addAttribute("index", new WebInfoForm());
        return "page/webpageForm";
    }

    @RequestMapping(value = "/getvalue", method = RequestMethod.POST)
    public String getValue(@Validated @ModelAttribute WebInfoForm form, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("validationError", "不正な値");
            return index(form, model);
        }
        String name = form.getName();
        String url = form.getURL();
        String userID = form.getUserID();
        String password = form.getPassword();
        return "page/getvalue";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String register(@ModelAttribute @Valid WebInfoForm form, BindingResult result, Model model){
        if(!result.hasErrors()){
            String name = form.getName();
            String url = form.getURL();
            String userID = form.getUserID();
            String password = form.getPassword();
            repository.save(new WebInfo("test", name, url, userID, password));
        }
        model.addAttribute("data", repository.findAll());
        return "page/getvalue";
    }
}
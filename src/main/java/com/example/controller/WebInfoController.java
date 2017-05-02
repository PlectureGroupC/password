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
import javax.validation.Valid;

import com.example.form.*;



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
        System.out.println("WebInfoController get throw");
        return "page/getvalue";
    }

    @RequestMapping(value = "/webpageForm", method = RequestMethod.GET)
    public String index(WebInfoForm form, Model model){
        String mailaddress = form.getMailaddress();
        model.addAttribute("form", form);
        System.out.println("WebInfoController index throw");
        return "page/webpageForm";
    }

    @RequestMapping(value = "/getvalue", method = RequestMethod.POST)
    public String getValue(@Validated @ModelAttribute("webInfoForm") WebInfoForm form, BindingResult result, Model model){
        String address = form.getMailaddress();
        String name = form.getName();
        String url = form.getUrl();
        String userID = form.getUserID();
        String password = form.getPassword();
        System.out.println("WebInfoController getValue throw");

        System.out.println(result);
        if (result.hasErrors()){
            model.addAttribute("validationError", "不正な値");
            System.out.println(address);
            System.out.println(name);
            System.out.println(url);
            System.out.println(userID);
            System.out.println(password);
            return index(form, model);
        }
        return "page/getvalue";
    }

    @RequestMapping(value = "/webpageForm", method = RequestMethod.POST)
    public String register(@ModelAttribute @Valid WebInfoForm form, BindingResult result, Model model){
        System.out.println("WebInfoController register throw");
        if(!result.hasErrors()){
            String name = form.getName();
            String url = form.getUrl();
            String userID = form.getUserID();
            String password = form.getPassword();
            repository.save(new WebInfo("test", name, url, userID, password));
        }
        model.addAttribute("data", repository.findAll());
        return "page/getvalue";
    }
}
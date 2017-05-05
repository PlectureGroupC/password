package com.example.controller;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.example.form.*;
import com.example.model.WebInfo;
import com.example.service.WebInfoService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class WebInfoController {
    @Autowired
    WebInfoService service;

    final static Logger logger = LoggerFactory.getLogger(WebInfoController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = "/getvalue", method = RequestMethod.GET) // valueをログイン後のページのアドレスにする
    public String get(Model model){
        model.addAttribute("data", service.findAll());
        System.out.println("WebInfoController get throw");
        return "page/getvalue";
    }

    @RequestMapping(value = "/webpageForm", method = RequestMethod.GET)
    //homeからmailaddressを取得し、webpageFormに送信する
    public String getWebForm(WebInfoForm form, Model model){
        String mailaddress = form.getMailaddress();
        System.out.println(mailaddress);
        model.addAttribute("form", form);
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
            return "forward:index";
        }
        return "page/getvalue";
    }

    @RequestMapping(value = "/webpageForm", method = RequestMethod.POST)
    //webpageFormからWebInfoを取得、DBに保存する
    public String register(@ModelAttribute @Valid WebInfoForm form, BindingResult result, Model model){
        if(!result.hasErrors()){
            String mailaddress = form.getMailaddress();
            String name = form.getName();
            String url = form.getUrl();
            String userID = form.getUserID();
            String password = form.getPassword();
            service.save(new WebInfo(mailaddress, name, url, userID, password));
        }else{
            model.addAttribute("validationError", "不正な値");
            model.addAttribute("form", form);
            return "page/webpageForm";
        }
        model.addAttribute("data", service.findAll());
        return "page/getvalue";
    }


    @RequestMapping(value = "/webinfoList/{mailaddress}", method = RequestMethod.GET)
    //homeからmailaddressを取得し、DBからデータを取得、送信
    public String getList(@PathVariable String mailaddress, Model model){
        System.out.println(mailaddress);
        List<WebInfo> WebInfoList = service.findWebInfosByMailaddress(mailaddress);
        model.addAttribute("list", WebInfoList);
        return "page/webinfoList";
    }

    @RequestMapping(value = "/webinfo/delete/{number}", method = RequestMethod.GET)
    //webinfoListでデータの削除を要求された時
    //データを削除した後、リダイレクトしてwebinfoListを表示する
    public String delete(@ModelAttribute @Valid @PathVariable Integer number, RedirectAttributes attributes, Model model){
        System.out.println(String.valueOf(number));
        WebInfo info = service.findWebInfoByNumber(number);
        service.delete(info);
        attributes.addFlashAttribute("deletemessage", "データを削除しました");
        return "redirect:/webinfoList/" + info.mailaddress;
    }
}
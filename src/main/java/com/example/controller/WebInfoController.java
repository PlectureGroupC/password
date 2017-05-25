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
import com.example.domain.model.WebInfo;
import com.example.domain.service.WebInfoService;
import com.example.crypto.Crypto;
import com.example.crypto.FileUtil;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class WebInfoController {
    @Autowired
    WebInfoService service;

    final static Logger logger = LoggerFactory.getLogger(WebInfoController.class);

    byte[] iv = "fuckfuckfuckfuck".getBytes();
    byte[] key = "PassManaPassMana".getBytes();
    //本当はランダムがいいんだゾ

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = "/getvalue", method = RequestMethod.GET) // valueをログイン後のページのアドレスにする
    public String get(Model model){
        model.addAttribute("data", service.findAll());
        return "page/getvalue";
    }

    @RequestMapping(value = "/webpageForm", method = RequestMethod.GET)
    //homeからmailaddressを取得し、webpageFormに送信する
    public String getWebForm(WebInfoForm form, Model model){
        model.addAttribute("form", form);
        return "page/webpageForm";
    }

    @RequestMapping(value = "/getvalue", method = RequestMethod.POST)
    public String getValue(@Validated @ModelAttribute("webInfoForm") WebInfoForm form, BindingResult result, Model model){
        String username = form.getUsername();
        String name = form.getName();
        String url = form.getUrl();
        String userID = form.getUserID();
        String password = form.getPassword();

        System.out.println(result);
        if (result.hasErrors()){
            model.addAttribute("validationError", "不正な値");
            System.out.println(username);
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
            String username = form.getUsername();
            String name = form.getName();
            String url = form.getUrl();
            String userID = form.getUserID();
            String password = form.getPassword();
            String cryptoPass = "";
            //ここから暗号化かませ
            try{
                Crypto crypto = new Crypto(key, iv);
                cryptoPass = crypto.encrypto(password);
            }catch (Exception e){
                e.printStackTrace();
            }
            //ここまで暗号化かます
            service.save(new WebInfo(username, name, url, userID, cryptoPass));
        }else{
            model.addAttribute("validationError", "不正な値");
            model.addAttribute("form", form);
            return "page/webpageForm";
        }
        model.addAttribute("data", service.findAll());
        return "page/getvalue";
    }

    @RequestMapping(value = "/webinfoList/{username}", method = RequestMethod.GET)
    //homeからusernameを取得し、DBからデータを取得、送信
    public String getList(@PathVariable String username, Model model){
        List<WebInfo> WebInfoList = service.findWebInfosByUsername(username);
        model.addAttribute("list", WebInfoList);
        model.addAttribute("username", username);
        return "page/webinfoList";
    }

    @RequestMapping(value = "/webinfo/delete/{number}", method = RequestMethod.GET)
    //webinfoListでデータの削除を要求された時
    //データを削除した後、リダイレクトしてwebinfoListを表示する
    public String delete(@ModelAttribute @Valid @PathVariable Integer number, RedirectAttributes attributes, Model model){
        WebInfo info = service.findWebInfoByNumber(number);
        service.delete(info);
        attributes.addFlashAttribute("deletemessage", "データを削除しました");
        return "redirect:/webinfoList/" + info.username;
    }

    @RequestMapping(value = "/webinfo/getWebInfo", method = RequestMethod.GET)
    //webinfoListで変更が押下された時、usernameを送信し、遷移する
    public String getWebInfo(WebInfoForm form, Model model){
        Integer number = form.getNumber();
        WebInfo info = service.findWebInfoByNumber(number);
        String decryptoPass = "";
        try{
            Crypto crypto = new Crypto(key, iv);
            decryptoPass = crypto.decrypto(info.password);
        }catch(Exception e){
            e.printStackTrace();
        }
        info.password = decryptoPass;
        model.addAttribute("webinfo", info);
        model.addAttribute("form", form);
        return "page/updateForm";
    }

    @RequestMapping(value = "/webinfo/update", method = RequestMethod.POST)
    public String update(@ModelAttribute @Valid WebInfoForm form, BindingResult result, Model model){
        if(!result.hasErrors()){
            Integer number = form.getNumber();
            String username = form.getUsername();
            String name = form.getName();
            String url = form.getUrl();
            String userID = form.getUserID();
            String password = form.getPassword();
            service.update(new WebInfo(number, username, name, url, userID, password));
        }else{
            model.addAttribute("validationError", "不正な値");
            model.addAttribute("form", form);
            return "page/updateForm";
        }
        return "page/getvalue";
    }

    @RequestMapping(value = "/webinfo/detail/{number}", method = RequestMethod.GET)
    //webinfoListで詳細を押下された時、押下されたデータの詳細を送信
    public String getDetail(@ModelAttribute @Valid @PathVariable Integer number, Model model){
        WebInfo info = service.findWebInfoByNumber(number);
        String decryptoPass = "";
        try{
            Crypto crypto = new Crypto(key, iv);
            decryptoPass = crypto.decrypto(info.password);
        }catch(Exception e){
            e.printStackTrace();
        }
        info.password = decryptoPass;
        model.addAttribute("webinfo", info);
        return "page/webinfoDetail";
    }
}
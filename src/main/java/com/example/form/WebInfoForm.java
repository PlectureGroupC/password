package com.example.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WebInfoForm {
    @NotNull    //mail address
    private String mailaddress;

    private String name;

    private String url;

    private String userID;

    @Size(min = 8)  //8文字以下はなし
    private String password;

    public String getMailaddress(){
        return mailaddress;
    }
    public void setMailaddress(String mailaddress){
        this.mailaddress = mailaddress;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUserID(){
        return userID;
    }
    public void setUserID(String UserID){
        this.userID = UserID;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
}

package com.example.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WebInfoForm {
    @NotNull    //mail address
    private String username;

    private String name;

    private String url;

    private String userID;

    @Size(min = 8)  //8文字以下はなし
    private String password;

    private Integer number;

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
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
    public Integer getNumber(){
        return number;
    }
    public void setNumber(Integer number){
        this.number = number;
    }
}

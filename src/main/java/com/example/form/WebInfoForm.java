package com.example.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WebInfoForm {
    @NotNull
    private String name;

    @NotNull
    private String URL;

    @NotNull
    private String UserID;

    @NotNull
    @Size(min = 8)  //8文字以下はなし
    private String password;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getURL(){
        return URL;
    }
    public void setURL(String URL){
        this.URL = URL;
    }
    public String getUserID(){
        return UserID;
    }
    public void setUserID(String UserID){
        this.UserID = UserID;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
}

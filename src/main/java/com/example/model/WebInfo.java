package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class WebInfo {
    @Id
    @GeneratedValue
    private String mailaddress;
    private String name;
    private String url;
    private String userID;
    private String password;

    public WebInfo(){
    }

    public WebInfo(String mailaddress, String name, String url, String userID, String password){
        this.mailaddress = mailaddress;
        this.name = name;
        this.url = url;
        this.userID = userID;
        this.password = password;
    }

}

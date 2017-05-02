package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "web_info")
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

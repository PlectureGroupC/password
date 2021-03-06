package com.example.domain.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity(name = "web_info")
public class WebInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "number")
    public int number;

    @Column(name = "username")
    public String username;

    @Column(name = "web_name")
    public String name;

    @Column(name = "web_url")
    public String url;

    @Column(name = "web_userid")
    public String userID;

    @Column(name = "web_password")
    @Size(min = 8)
    public String password;

    public WebInfo(){
    }

    public WebInfo(String username, String name, String url, String userID, String password){
        this.username = username;
        this.name = name;
        this.url = url;
        this.userID = userID;
        this.password = password;
    }
    public WebInfo(Integer number, String username, String name, String url, String userID, String password){
        this.number = number;
        this.username = username;
        this.name = name;
        this.url = url;
        this.userID = userID;
        this.password = password;
    }
}

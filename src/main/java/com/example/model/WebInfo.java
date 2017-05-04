package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "web_info")
public class WebInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "number")
    private int number;

    @Column(name = "mail_address")
    private String mailaddress;

    @Column(name = "web_name")
    private String name;

    @Column(name = "web_url")
    private String url;

    @Column(name = "web_userid")
    private String userID;

    @Column(name = "web_password")
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

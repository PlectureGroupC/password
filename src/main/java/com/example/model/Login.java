package com.example.model;

import javax.persistence.*;

@Entity
public class Login {
    @Id
    @GeneratedValue
    private String address;

    Login(){

    }

    Login(String address){
        this.address = address;
    }
}

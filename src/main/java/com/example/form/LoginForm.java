package com.example.form;

import javax.validation.constraints.*;

public class LoginForm {
    @NotNull
    private String mailaddress;

    public String getMailaddress(){
        return mailaddress;
    }
    public void setMailaddress(String mailaddress){
        this.mailaddress = mailaddress;
    }
}

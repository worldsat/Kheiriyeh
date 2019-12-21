package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

public class Login extends BaseDomain {
    private String isError;
    private String message;
    private String token;
    private String fullName;
    private String code;

    public Login() {
        setApiAddress("api/Login");
    }

    public String getisError() {
        return isError;
    }

    public void setisError(String isError) {
        this.isError = isError;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public String gettoken() {
        return token;
    }

    public void settoken(String token) {
        this.token = token;
    }

    public String getfullName() {
        return fullName;
    }

    public void setfullName(String fullName) {
        this.fullName = fullName;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }
}

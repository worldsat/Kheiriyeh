package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

public class BoxApi extends BaseDomain {
    private String fullName;
    private String number;
    private String mobile;
    private String code;
    private String registerDate;
    private String id;


    public BoxApi() {
        setApiAddress("api/Box");
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getfullName() {
        return fullName;
    }

    public void setfullName(String fullName) {
        this.fullName = fullName;
    }

    public String getnumber() {
        return number;
    }

    public void setnumber(String number) {
        this.number = number;
    }

    public String getmobile() {
        return mobile;
    }

    public void setmobile(String mobile) {
        this.mobile = mobile;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String getregisterDate() {
        return registerDate;
    }

    public void setregisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
}

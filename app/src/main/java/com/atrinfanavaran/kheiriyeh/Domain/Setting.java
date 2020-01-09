package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

public class Setting  {
    private String id;
    private String logoName;
    private String logo;
    private String sherkatName;



    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getlogoName() {
        return logoName;
    }

    public void setlogoName(String logoName) {
        this.logoName = logoName;
    }

    public String getlogo() {
        return logo;
    }

    public void setlogo(String logo) {
        this.logo = logo;
    }

    public String getsherkatName() {
        return sherkatName;
    }

    public void setsherkatName(String sherkatName) {
        this.sherkatName = sherkatName;
    }
}

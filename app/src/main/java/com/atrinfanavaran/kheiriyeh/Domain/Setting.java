package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

public class Setting extends BaseDomain {
    private String id;
    private String logoName;
    private String logo;
    private String sherkatName;

    public Setting() {
        setApiAddress("api/Setting");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSherkatName() {
        return sherkatName;
    }

    public void setSherkatName(String sherkatName) {
        this.sherkatName = sherkatName;
    }
}

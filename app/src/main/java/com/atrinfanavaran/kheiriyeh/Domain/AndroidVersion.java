package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

public class AndroidVersion extends BaseDomain {
    private String id;
    private String appAndroidUrl;
    private String currVersion;

    public AndroidVersion() {
        setApiAddresss("api/AndroidVersion");
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getappAndroidUrl() {
        return appAndroidUrl;
    }

    public void seappAndroidUrl(String appAndroidUrl) {
        this.appAndroidUrl = appAndroidUrl;
    }

    public String getcurrVersion() {
        return currVersion;
    }

    public void setcurrVersion(String currVersion) {
        this.currVersion = currVersion;
    }
}

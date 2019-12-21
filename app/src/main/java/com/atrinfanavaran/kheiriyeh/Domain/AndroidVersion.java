package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

public class AndroidVersion extends BaseDomain {
    private String id;
    private String appAndroidUrl;
    private String currVersion;

    public AndroidVersion() {
        setApiAddress("AndroidVersion");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppAndroidUrl() {
        return appAndroidUrl;
    }

    public void setAppAndroidUrl(String appAndroidUrl) {
        this.appAndroidUrl = appAndroidUrl;
    }

    public String getCurrVersion() {
        return currVersion;
    }

    public void setCurrVersion(String currVersion) {
        this.currVersion = currVersion;
    }
}

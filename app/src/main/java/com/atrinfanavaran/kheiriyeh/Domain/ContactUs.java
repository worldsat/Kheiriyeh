package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

public class ContactUs  {
    private String id;
    private String phone;
    private String pageTelegramUrl;
    private String pageInstagramUrl;
    private String pageTwitterUrl;
    private String email;
    private String androidVersion;



    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public String getpageTelegramUrl() {
        return pageTelegramUrl;
    }

    public void setpageTelegramUrl(String pageTelegramUrl) {
        this.pageTelegramUrl = pageTelegramUrl;
    }

    public String getpageInstagramUrl() {
        return pageInstagramUrl;
    }

    public void setpageInstagramUrl(String pageInstagramUrl) {
        this.pageInstagramUrl = pageInstagramUrl;
    }

    public String getpageTwitterUrl() {
        return pageTwitterUrl;
    }

    public void setpageTwitterUrl(String pageTwitterUrl) {
        this.pageTwitterUrl = pageTwitterUrl;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getandroidVersion() {
        return androidVersion;
    }

    public void setandroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }
}

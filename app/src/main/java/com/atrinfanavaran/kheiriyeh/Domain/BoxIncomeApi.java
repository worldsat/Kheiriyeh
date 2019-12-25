package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

public class BoxIncomeApi extends BaseDomain {
    private String id;
    private String lon;
    private String lat;
    private String status;
    private String boxId;

    public BoxIncomeApi() {
        setApiAddress("api/BoxIncome");
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getlon() {
        return lon;
    }

    public void setlon(String lon) {
        this.lon = lon;
    }

    public String getlat() {
        return lat;
    }

    public void setlat(String lat) {
        this.lat = lat;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getboxId() {
        return boxId;
    }

    public void setboxId(String boxId) {
        this.boxId = boxId;
    }
}

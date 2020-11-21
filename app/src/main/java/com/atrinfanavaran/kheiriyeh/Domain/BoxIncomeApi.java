package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

public class BoxIncomeApi extends BaseDomain {

    private String number;
    private String price;
    private String status;
    private String lat;
    private String lon;
    private String assignmentDate;
    private String assignmentDateEn;
    private String id;

    public BoxIncomeApi() {
        setApiAddresss("api/BoxIncome");
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getnumber() {
        return number;
    }

    public void setnumber(String number) {
        this.number = number;
    }

    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getlat() {
        return lat;
    }

    public void setlat(String lat) {
        this.lat = lat;
    }

    public String getlon() {
        return lon;
    }

    public void setlon(String lon) {
        this.lon = lon;
    }

    public String getassignmentDate() {
        return assignmentDate;
    }

    public void setassignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public String getassignmentDateEn() {
        return assignmentDateEn;
    }

    public void setassignmentDateEn(String assignmentDateEn) {
        this.assignmentDateEn = assignmentDateEn;
    }
}

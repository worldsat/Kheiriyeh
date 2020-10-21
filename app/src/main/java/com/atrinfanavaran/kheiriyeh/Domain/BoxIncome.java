package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewMode;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewType;

import java.util.ArrayList;

public class BoxIncome extends BaseDomain {

    private String number;
    private String price;
    private String status;
    private String lat;
    private String lon;
    private String assignmentDate;
    private int id;

    public BoxIncome() {
        ArrayList<DomainInfo> domainInfoList = new ArrayList<>();

//        domainInfoList.add(new DomainInfo(
//                ViewMode.FILTER.name(),
//                "fullName",
//                "نام",
//                "",
//                ViewType.EDIT_TEXT.name())
//        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "number",
                "شماره",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "price",
                "مبلغ",
                "",
                ViewType.EDIT_TEXT.name())
        );

        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "assignmentDate",
                "تاریخ واگذاری",
                "",
                ViewType.EDIT_TEXT.name())
        );

        setDomainInfo(domainInfoList);
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
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
}

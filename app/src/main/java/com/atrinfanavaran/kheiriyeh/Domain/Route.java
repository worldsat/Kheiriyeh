package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewMode;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewType;

import java.util.ArrayList;

public class Route extends BaseDomain {
 private String  code;
 private String  day;
 private String  address;
 private String  lat;
 private String  lon;
 private String  guidDischargeRoute;
 private int  id;

    public Route() {
        ArrayList<DomainInfo> domainInfoList = new ArrayList<>();

        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "address",
                "آدرس مسیر",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "code",
                "کد مسیر",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "day",
                "تاریخ در ماه",
                "",
                ViewType.EDIT_TEXT.name())
        );
        setDomainInfo(domainInfoList);
    }

    public String getguidDischargeRoute() {
        return guidDischargeRoute;
    }

    public void setguidDischargeRoute(String guidDischargeRoute) {
        this.guidDischargeRoute = guidDischargeRoute;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String getday() {
        return day;
    }

    public void setday(String day) {
        this.day = day;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
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
}

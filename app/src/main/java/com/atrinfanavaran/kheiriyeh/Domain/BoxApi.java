package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

public class BoxApi extends BaseDomain {
    private String fullName;
    private String number;
    private String mobile;
    private String code;
    private String assignmentDate;
    private String id;
    private String address;
    private String lat;
    private String lon;
    private String dischargeRouteId;
    private String boxId;



    public BoxApi() {
        setApiAddress("api/Box");
    }

    public String getboxId() {
        return boxId;
    }

    public void setboxId(String boxId) {
        this.boxId = boxId;
    }

    public String getdischargeRouteId() {
        return dischargeRouteId;
    }

    public void setdischargeRouteId(String dischargeRouteId) {
        this.dischargeRouteId = dischargeRouteId;
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

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
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

    public String getassignmentDate() {
        return assignmentDate;
    }

    public void setassignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }
}

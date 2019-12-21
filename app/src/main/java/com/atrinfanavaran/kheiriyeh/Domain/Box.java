package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

public class Box extends BaseDomain {
    private String id;
    private String number;
    private String fullName;
    private String mobile;
    private String assignmentDate;
    private String dischargeRouteId;
    private String dischargeRoute;

    public Box() {
        setApiAddress("api/Box");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public String getDischargeRouteId() {
        return dischargeRouteId;
    }

    public void setDischargeRouteId(String dischargeRouteId) {
        this.dischargeRouteId = dischargeRouteId;
    }

    public String getDischargeRoute() {
        return dischargeRoute;
    }

    public void setDischargeRoute(String dischargeRoute) {
        this.dischargeRoute = dischargeRoute;
    }
}

package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewMode;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewType;

import java.util.ArrayList;

public class Box extends BaseDomain {
    private String fullName;
    private String number;
    private String mobile;
    private String code;
    private String assignmentDate;
    private String assignmentDateEn;
    private String lat;
    private String lng;
    private String address;
    private String guidDischargeRoute;
    private String guidBox;
    private String day;
    private String boxKind;
    private int id;
    private String dischargeRouteId;
    private int boxId;

    public String getboxKind() {
        return boxKind;
    }

    public void setboxKind(String boxKind) {
        this.boxKind = boxKind;
    }

    public String getday() {
        return day;
    }

    public void setday(String day) {
        this.day = day;
    }

    public String getguidBox() {
        return guidBox;
    }

    public void setguidBox(String guidBox) {
        this.guidBox = guidBox;
    }

    public String getassignmentDateEn() {
        return assignmentDateEn;
    }

    public void setassignmentDateEn(String assignmentDateEn) {
        this.assignmentDateEn = assignmentDateEn;
    }

    public String getguidDischargeRoute() {
        return guidDischargeRoute;
    }

    public void setguidDischargeRoute(String guidDischargeRoute) {
        this.guidDischargeRoute = guidDischargeRoute;
    }

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Box() {
        setApiAddresss("api/Box");

        ArrayList<DomainInfo> domainInfoList = new ArrayList<>();

        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "fullName",
                "نام",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "number",
                "شماره",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "mobile",
                "همراه",
                "",
                ViewType.EDIT_TEXT.name())
        );
//        domainInfoList.add(new DomainInfo(
//                ViewMode.FILTER.name(),
//                "code",
//                "کد",
//                "",
//                ViewType.EDIT_TEXT.name())
//        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "assignmentDateEn",
                "تاریخ واگذاری",
                "BetweenCalendar",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "address",
                "آدرس",
                "",
                ViewType.EDIT_TEXT.name())
        );
        setDomainInfo(domainInfoList);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getassignmentDate() {
        return assignmentDate;
    }

    public void setassignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }


    @Override
    public String toString() {
        return dischargeRouteId;
    }


}

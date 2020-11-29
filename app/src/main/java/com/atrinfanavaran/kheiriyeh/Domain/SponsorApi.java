package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewMode;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewType;

import java.util.ArrayList;

public class SponsorApi extends BaseDomain {

    /**
     * data : [{"id":1,"fullName":"fhfh","code":"23","address":"zdsfsdf","mobile":"09131234567","nationalcode":"1234567897","phone":"34343445","birthDate":"1398-09-23 17:39:17 PM","charityId":1,"charity":null,"opratorId":1}]
     * count : 1
     * isError : false
     * message :
     */

    private int count;
    private boolean isError;
    private String message;
    private ArrayList<Data> data;

    public SponsorApi() {
        setApiAddresss("api/Sponsor");
        ArrayList<DomainInfo> domainInfoList = new ArrayList<>();

        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "fullName",
                "نام و نام خانوادگی",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "code",
                "کد",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "address",
                "آدرس",
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
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "nationalcode",
                "کد ملی",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "phone",
                "تلفن",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "birthDate",
                "تاریخ ثبت",
                "BetweenCalendar",
                ViewType.EDIT_TEXT.name())
        );

        setDomainInfo(domainInfoList);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isIsError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public static class Data {
        /**
         * id : 1
         * fullName : fhfh
         * code : 23
         * address : zdsfsdf
         * mobile : 09131234567
         * nationalcode : 1234567897
         * phone : 34343445
         * birthDate : 1398-09-23 17:39:17 PM
         * charityId : 1
         * charity : null
         * opratorId : 1
         */

        public int id;
        public String fullName;
        public String code;
        public String address;
        public String mobile;
        public String nationalcode;
        public String phone;
        public String birthDate;
        public int charityId;
        public Object charity;
        public int opratorId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNationalcode() {
            return nationalcode;
        }

        public void setNationalcode(String nationalcode) {
            this.nationalcode = nationalcode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public int getCharityId() {
            return charityId;
        }

        public void setCharityId(int charityId) {
            this.charityId = charityId;
        }

        public Object getCharity() {
            return charity;
        }

        public void setCharity(Object charity) {
            this.charity = charity;
        }

        public int getOpratorId() {
            return opratorId;
        }

        public void setOpratorId(int opratorId) {
            this.opratorId = opratorId;
        }
    }
}

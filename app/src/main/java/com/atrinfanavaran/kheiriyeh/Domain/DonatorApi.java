package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewMode;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewType;

import java.util.ArrayList;
import java.util.List;

public class DonatorApi  extends BaseDomain {

    public DonatorApi() {
        setApiAddresss("api/Donator");
        ArrayList<DomainInfo> domainInfoList = new ArrayList<>();
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "donatorFullName",
                "نام و نام خانوادگی",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "donatorAlias",
                "نام مستعار",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "donatorMobile",
                "تلفن همراه",
                "",
                ViewType.EDIT_TEXT.name())
        );
        setDomainInfo(domainInfoList);
    }

    /**
     * data : [{"id":1,"donatorFullName":"علی احمدی","donatorAlias":"علییی","charityId":1,"donatorMobile":"09131232345","isSendMessage":true}]
     * count : 1
     * isError : false
     * message :
     */

    private int count;
    private boolean isError;
    private String message;
    private List<Data> data;

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        /**
         * id : 1
         * donatorFullName : علی احمدی
         * donatorAlias : علییی
         * charityId : 1
         * donatorMobile : 09131232345
         * isSendMessage : true
         */

        private int id;
        private String donatorFullName;
        private String donatorAlias;
        private int charityId;
        private String donatorMobile;
        private boolean isSendMessage;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDonatorFullName() {
            return donatorFullName;
        }

        public void setDonatorFullName(String donatorFullName) {
            this.donatorFullName = donatorFullName;
        }

        public String getDonatorAlias() {
            return donatorAlias;
        }

        public void setDonatorAlias(String donatorAlias) {
            this.donatorAlias = donatorAlias;
        }

        public int getCharityId() {
            return charityId;
        }

        public void setCharityId(int charityId) {
            this.charityId = charityId;
        }

        public String getDonatorMobile() {
            return donatorMobile;
        }

        public void setDonatorMobile(String donatorMobile) {
            this.donatorMobile = donatorMobile;
        }

        public boolean isIsSendMessage() {
            return isSendMessage;
        }

        public void setIsSendMessage(boolean isSendMessage) {
            this.isSendMessage = isSendMessage;
        }
    }
}

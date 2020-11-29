package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewMode;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewType;

import java.util.ArrayList;
import java.util.List;

public class FinancialAidApi extends BaseDomain {

    public FinancialAidApi() {
        setApiAddresss("api/FinancialAid");

        ArrayList<DomainInfo> domainInfoList = new ArrayList<>();
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "name",
                "نام ",
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
                "financialServiceType",
                "نوع",
                "",
                ViewType.EDIT_TEXT.name())
        );
        setDomainInfo(domainInfoList);
    }

    /**
     * data : [{"id":1,"name":"تست","price":1000,"charityId":1,"charity":null,"opratorId":1,"financialServiceTypeId":1,"financialServiceType":null}]
     * count : 1
     * isError : false
     * message :
     */

    private int count;
    private boolean isError;
    private String message;
    private ArrayList<Data> data;

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
         * name : تست
         * price : 1000
         * charityId : 1
         * charity : null
         * opratorId : 1
         * financialServiceTypeId : 1
         * financialServiceType : null
         */

        private int id;
        private String name;
        private int price;
        private int charityId;
        private Object charity;
        private int opratorId;
        private int financialServiceTypeId;
        private Object financialServiceType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
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

        public int getFinancialServiceTypeId() {
            return financialServiceTypeId;
        }

        public void setFinancialServiceTypeId(int financialServiceTypeId) {
            this.financialServiceTypeId = financialServiceTypeId;
        }

        public Object getFinancialServiceType() {
            return financialServiceType;
        }

        public void setFinancialServiceType(Object financialServiceType) {
            this.financialServiceType = financialServiceType;
        }
    }
}

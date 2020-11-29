package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewMode;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewType;

import java.util.ArrayList;

public class FlowerCrownApi extends BaseDomain {

    public FlowerCrownApi() {
        setApiAddresss("api/FlowerCrown");
        ArrayList<DomainInfo> domainInfoList = new ArrayList<>();

        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "donator",
                "نام اهدا کننده",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "deceasedName",
                "نام متوفی",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "Introduced",
                "نام معرف",
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
                "ceremonyType",
                "نوع مراسم",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "flowerCrownType",
                "نوع تاج گل",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "registerDateEn",
                "تاریخ ثبت",
                "BetweenCalendar",
                ViewType.EDIT_TEXT.name())
        );

        setDomainInfo(domainInfoList);
    }

    /**
     * data : [{"id":1,"donator":"محمد","deceasedName":"عباس","price":10000,"ceremonyType":1,"charityId":1,"charity":null,"opratorId":1,"flowerCrownTypeId":1,"flowerCrownType":null,"registerDate":"1399-09-09"}]
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
         * donator : محمد
         * deceasedName : عباس
         * price : 10000
         * ceremonyType : 1
         * charityId : 1
         * charity : null
         * opratorId : 1
         * flowerCrownTypeId : 1
         * flowerCrownType : null
         * registerDate : 1399-09-09
         */

        private int id;
        private String donator;
        private String deceasedName;
        private int price;
        private int ceremonyType;
        private int charityId;
        private String charity;
        private int opratorId;
        private int flowerCrownTypeId;
        private String flowerCrownType;
        private String registerDate;
        private int deceasedNameId;
        private int donatorId;
        private int introducedId;

        public int getDeceasedNameId() {
            return deceasedNameId;
        }

        public void setDeceasedNameId(int deceasedNameId) {
            this.deceasedNameId = deceasedNameId;
        }

        public int getDonatorId() {
            return donatorId;
        }

        public void setDonatorId(int donatorId) {
            this.donatorId = donatorId;
        }

        public int getIntroducedId() {
            return introducedId;
        }

        public void setIntroducedId(int introducedId) {
            this.introducedId = introducedId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDonator() {
            return donator;
        }

        public void setDonator(String donator) {
            this.donator = donator;
        }

        public String getDeceasedName() {
            return deceasedName;
        }

        public void setDeceasedName(String deceasedName) {
            this.deceasedName = deceasedName;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getCeremonyType() {
            return ceremonyType;
        }

        public void setCeremonyType(int ceremonyType) {
            this.ceremonyType = ceremonyType;
        }

        public int getCharityId() {
            return charityId;
        }

        public void setCharityId(int charityId) {
            this.charityId = charityId;
        }

        public String getCharity() {
            return charity;
        }

        public void setCharity(String charity) {
            this.charity = charity;
        }

        public int getOpratorId() {
            return opratorId;
        }

        public void setOpratorId(int opratorId) {
            this.opratorId = opratorId;
        }

        public int getFlowerCrownTypeId() {
            return flowerCrownTypeId;
        }

        public void setFlowerCrownTypeId(int flowerCrownTypeId) {
            this.flowerCrownTypeId = flowerCrownTypeId;
        }

        public String getFlowerCrownType() {
            return flowerCrownType;
        }

        public void setFlowerCrownType(String flowerCrownType) {
            this.flowerCrownType = flowerCrownType;
        }

        public String getRegisterDate() {
            return registerDate;
        }

        public void setRegisterDate(String registerDate) {
            this.registerDate = registerDate;
        }
    }
}

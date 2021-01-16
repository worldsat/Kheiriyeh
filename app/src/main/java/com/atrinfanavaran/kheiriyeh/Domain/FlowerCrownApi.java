package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewMode;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FlowerCrownApi extends BaseDomain {

    /**
     * data : [{"id":8,"price":4444444,"ceremonyType":4,"charityId":1,"charity":null,"opratorId":1,"flowerCrownTypeId":3,"flowerCrownType":{"id":3,"title":"تاج گل سوم","charityId":1},"deceasedNameId":8,"deceasedName":null,"donatorId":1,"introducedId":6,"registerDate":"1399/9/18"},{"id":7,"price":5464564,"ceremonyType":2,"charityId":1,"charity":null,"opratorId":1,"flowerCrownTypeId":4,"flowerCrownType":{"id":4,"title":"تاج گل چهارم","charityId":1},"deceasedNameId":1,"deceasedName":null,"donatorId":1,"introducedId":3,"registerDate":"1399/9/18"},{"id":6,"price":98989,"ceremonyType":1,"charityId":1,"charity":null,"opratorId":1,"flowerCrownTypeId":1,"flowerCrownType":{"id":1,"title":"تاج گل اول","charityId":1},"deceasedNameId":6,"deceasedName":null,"donatorId":9,"introducedId":9,"registerDate":"1399/9/17"}]
     * count : 3
     * isError : false
     * message :
     */

    private int count;
    private boolean isError;
    private String message;
    private List<Data> data;

    public FlowerCrownApi() {
        setApiAddresss("api/FlowerCrown");
        ArrayList<DomainInfo> domainInfoList = new ArrayList<>();

        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "donatorName",
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
                "introducedName",
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
//        domainInfoList.add(new DomainInfo(
//                ViewMode.FILTER.name(),
//                "registerDateEn",
//                "تاریخ ثبت",
//                "BetweenCalendar",
//                ViewType.EDIT_TEXT.name())
//        );

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data  implements Serializable {
        /**
         * id : 8
         * price : 4444444
         * ceremonyType : 4
         * charityId : 1
         * charity : null
         * opratorId : 1
         * flowerCrownTypeId : 3
         * flowerCrownType : {"id":3,"title":"تاج گل سوم","charityId":1}
         * deceasedNameId : 8
         * deceasedName : null
         * donatorId : 1
         * introducedId : 6
         * registerDate : 1399/9/18
         */

        private int id;
        private int price;
        private int ceremonyType;
        private int payType;
        private int charityId;
        private String charity;
        private int opratorId;
        private int flowerCrownTypeId;
        private String flowerCrownType;
        private int deceasedNameId;
        private String deceasedName;
        private int donatorId;
        private int introducedId;
        private String registerDate;
        private String donatorName;
        private String deceasedFullName;
        private String introducedName;

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getDonatorName() {
            return donatorName;
        }

        public void setDonatorName(String donatorName) {
            this.donatorName = donatorName;
        }

        public String getDeceasedFullName() {
            return deceasedFullName;
        }

        public void setDeceasedFullName(String deceasedFullName) {
            this.deceasedFullName = deceasedFullName;
        }

        public String getIntroducedName() {
            return introducedName;
        }

        public void setIntroducedName(String introducedName) {
            this.introducedName = introducedName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getDeceasedNameId() {
            return deceasedNameId;
        }

        public void setDeceasedNameId(int deceasedNameId) {
            this.deceasedNameId = deceasedNameId;
        }

        public String getDeceasedName() {
            return deceasedName;
        }

        public void setDeceasedName(String deceasedName) {
            this.deceasedName = deceasedName;
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

        public String getRegisterDate() {
            return registerDate;
        }

        public void setRegisterDate(String registerDate) {
            this.registerDate = registerDate;
        }

        public static class FlowerCrownType {
            /**
             * id : 3
             * title : تاج گل سوم
             * charityId : 1
             */

            private int id;
            private String title;
            private int charityId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getCharityId() {
                return charityId;
            }

            public void setCharityId(int charityId) {
                this.charityId = charityId;
            }
        }
    }
}

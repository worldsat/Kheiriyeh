package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewMode;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeceasedNameApi extends BaseDomain {

    public DeceasedNameApi() {
        setApiAddresss("api/DeceasedName/");
        ArrayList<DomainInfo> domainInfoList = new ArrayList<>();
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "deceasedFullName",
                "نام و نام خانوادگی",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "deceaseAalias",
                "نام مستعار",
                "",
                ViewType.EDIT_TEXT.name())
        );

        setDomainInfo(domainInfoList);
    }

    /**
     * data : [{"id":1,"deceasedFullName":"مهران محمدی","deceaseAalias":"مهران","deceasedSex":false,"charityId":1}]
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

    public static class Data implements Serializable {
        /**
         * id : 1
         * deceasedFullName : مهران محمدی
         * deceaseAalias : مهران
         * deceasedSex : false
         * charityId : 1
         */

        @Override
        public String toString() {
            return deceasedFullName;
        }

        public Data(int id, String deceasedFullName) {
            this.id = id;
            this.deceasedFullName = deceasedFullName;
        }


        private int id;
        private String deceasedFullName;
        private String deceaseAalias;
        private String guidDeceasedName;
        private boolean deceasedSex;
        private int charityId;

        public String getGuidDeceasedName() {
            return guidDeceasedName;
        }

        public void setGuidDeceasedName(String guidDeceasedName) {
            this.guidDeceasedName = guidDeceasedName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDeceasedFullName() {
            return deceasedFullName;
        }

        public void setDeceasedFullName(String deceasedFullName) {
            this.deceasedFullName = deceasedFullName;
        }

        public String getDeceaseAalias() {
            return deceaseAalias;
        }

        public void setDeceaseAalias(String deceaseAalias) {
            this.deceaseAalias = deceaseAalias;
        }

        public boolean isDeceasedSex() {
            return deceasedSex;
        }

        public void setDeceasedSex(boolean deceasedSex) {
            this.deceasedSex = deceasedSex;
        }

        public int getCharityId() {
            return charityId;
        }

        public void setCharityId(int charityId) {
            this.charityId = charityId;
        }
    }
}

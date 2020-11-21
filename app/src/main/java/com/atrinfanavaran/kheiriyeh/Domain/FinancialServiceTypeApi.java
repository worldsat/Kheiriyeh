package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

import java.util.ArrayList;
import java.util.List;

public class FinancialServiceTypeApi extends BaseDomain {

    /**
     * data : [{"id":1,"title":"عتبات عالیات","charityId":1},{"id":2,"title":"خیریه امام علی","charityId":1}]
     * count : 2
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
         * title : عتبات عالیات
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

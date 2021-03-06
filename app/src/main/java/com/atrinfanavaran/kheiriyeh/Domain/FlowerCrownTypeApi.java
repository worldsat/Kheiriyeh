package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

import java.util.ArrayList;
import java.util.List;

public class FlowerCrownTypeApi {

    /**
     * data : [{"id":1,"title":"تاج گل دسته ای","charityId":1},{"id":2,"title":"تاج گل شاخه ای","charityId":1}]
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
         * title : تاج گل دسته ای
         * charityId : 1
         */

        private int id;
        private String title;
        private int charityId;
        private int payType;
        private int price;

        public Data(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

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

        @Override
        public String toString() {
            return title;
        }
    }
}

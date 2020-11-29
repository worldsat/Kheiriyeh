package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

public class AndroidVersion extends BaseDomain {

    /**
     * data : {"id":1,"appAndroidUrl":"wwwroot/AndroidApp/app-debug.apk","currVersion":"20","isMandatory":true}
     * count : 1
     * isError : false
     * message :
     */

    private Data data;
    private int count;
    private boolean isError;
    private String message;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
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

    public static class Data {
        /**
         * id : 1
         * appAndroidUrl : wwwroot/AndroidApp/app-debug.apk
         * currVersion : 20
         * isMandatory : true
         */

        private int id;
        private String appAndroidUrl;
        private String currVersion;
        private boolean isMandatory;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAppAndroidUrl() {
            return appAndroidUrl;
        }

        public void setAppAndroidUrl(String appAndroidUrl) {
            this.appAndroidUrl = appAndroidUrl;
        }

        public String getCurrVersion() {
            return currVersion;
        }

        public void setCurrVersion(String currVersion) {
            this.currVersion = currVersion;
        }

        public boolean isIsMandatory() {
            return isMandatory;
        }

        public void setIsMandatory(boolean isMandatory) {
            this.isMandatory = isMandatory;
        }
    }
}

package com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain;

public class ApiResponse {
    public Integer Count;
    public Object Data;
    public Object ExtraResult;
    public String ColumnHeader;
    public String message;
    public Boolean isError;
    public String DownloadLink;


    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    public Object getExtraResult() {
        return ExtraResult;
    }

    public void setExtraResult(Object extraResult) {
        ExtraResult = extraResult;
    }

    public String getColumnHeader() {
        return ColumnHeader;
    }

    public void setColumnHeader(String columnHeader) {
        ColumnHeader = columnHeader;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        message = message;
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

    public String getDownloadLink() {
        return DownloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        DownloadLink = downloadLink;
    }
}

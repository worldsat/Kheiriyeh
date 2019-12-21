package com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain;


import android.support.annotation.NonNull;

public class FilteredDomain {
    @NonNull
    private String id;
    private String value;

    public FilteredDomain(@NonNull String id, String value) {
        this.id = id;
        this.value = value;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

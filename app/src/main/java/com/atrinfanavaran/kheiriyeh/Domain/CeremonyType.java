package com.atrinfanavaran.kheiriyeh.Domain;

import androidx.annotation.NonNull;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

public class CeremonyType extends BaseDomain {
    private String name;
    private int id;

    public CeremonyType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}

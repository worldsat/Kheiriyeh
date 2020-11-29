package com.atrinfanavaran.kheiriyeh.Room.Domian;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class FinancialAidR implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "charityId")
    public int charityId;

    @ColumnInfo(name = "charity")
    public String charity;

    @ColumnInfo(name = "opratorId")
    public int opratorId;

    @ColumnInfo(name = "financialServiceTypeId")
    public int financialServiceTypeId;

    @ColumnInfo(name = "financialServiceType")
    public String financialServiceType;

    @ColumnInfo(name = "isNew")
    public String isNew;

    public FinancialAidR() {
    }

    public FinancialAidR(int financialServiceTypeId, String financialServiceType) {
        this.financialServiceTypeId = financialServiceTypeId;
        this.financialServiceType = financialServiceType;
    }

    @Override
    public String toString() {
        return financialServiceType;
    }

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

    public int getFinancialServiceTypeId() {
        return financialServiceTypeId;
    }

    public void setFinancialServiceTypeId(int financialServiceTypeId) {
        this.financialServiceTypeId = financialServiceTypeId;
    }

    public String getFinancialServiceType() {
        return financialServiceType;
    }

    public void setFinancialServiceType(String financialServiceType) {
        this.financialServiceType = financialServiceType;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }
}

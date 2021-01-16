package com.atrinfanavaran.kheiriyeh.Room.Domian;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FinancialServiceTypeR {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "charityId")
    public int charityId;
    @ColumnInfo(name = "payType")
    public int payType;

    @ColumnInfo(name = "isNew")
    public String isNew;

    public FinancialServiceTypeR(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public FinancialServiceTypeR() {
    }

    @Override
    public String toString() {
        return title;
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

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }
}

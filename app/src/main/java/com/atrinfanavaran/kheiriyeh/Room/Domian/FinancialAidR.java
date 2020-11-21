package com.atrinfanavaran.kheiriyeh.Room.Domian;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FinancialAidR {
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

}

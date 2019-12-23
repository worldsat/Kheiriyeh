package com.atrinfanavaran.kheiriyeh.Room.Domian;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class BoxIncomeR {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "factorNumber")
    public String factorNumber;

    @ColumnInfo(name = "number")
    public String number;

    @ColumnInfo(name = "price")
    public String price;

    @ColumnInfo(name = "status")
    public String status;

    @ColumnInfo(name = "lat")
    public String lat;

    @ColumnInfo(name = "lon")
    public String lon;

    @ColumnInfo(name = "registerDate")
    public String registerDate;
}

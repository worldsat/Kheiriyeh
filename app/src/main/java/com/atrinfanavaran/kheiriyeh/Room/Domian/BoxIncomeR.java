package com.atrinfanavaran.kheiriyeh.Room.Domian;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BoxIncomeR {
    @PrimaryKey(autoGenerate = true)
    public int id;

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

    @ColumnInfo(name = "assignmentDate")
    public String assignmentDate;

    @ColumnInfo(name = "assignmentDateEn")
    public String assignmentDateEn;
    @ColumnInfo(name = "guidBox")
    public String guidBox;
}

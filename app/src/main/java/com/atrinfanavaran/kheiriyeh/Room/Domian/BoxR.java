package com.atrinfanavaran.kheiriyeh.Room.Domian;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BoxR {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "fullName")
    public String fullName;

    @ColumnInfo(name = "number")
    public String number;

    @ColumnInfo(name = "mobile")
    public String mobile;

    @ColumnInfo(name = "code")
    public String code;

    @ColumnInfo(name = "assignmentDate")
    public String assignmentDate;

    @ColumnInfo(name = "assignmentDateEn")
    public String assignmentDateEn;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "isNew")
    public String isNew;

    @ColumnInfo(name = "lat")
    public String lat;

    @ColumnInfo(name = "lon")
    public String lon;

    @ColumnInfo(name = "dischargeRouteId")
    public String dischargeRouteId;

    @ColumnInfo(name = "code2")
    public String code2;

    @ColumnInfo(name = "boxId")
    public int boxId;
}

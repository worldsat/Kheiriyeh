package com.atrinfanavaran.kheiriyeh.Room.Domian;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

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

    @ColumnInfo(name = "registerDate")
    public String registerDate;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "isNew")
    public String isNew;

    @ColumnInfo(name = "lat")
    public String lat;

    @ColumnInfo(name = "lon")
    public String lon;
}

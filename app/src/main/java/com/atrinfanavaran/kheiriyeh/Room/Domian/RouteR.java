package com.atrinfanavaran.kheiriyeh.Room.Domian;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class RouteR {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "code")
    public String code;

    @ColumnInfo(name = "day")
    public String day;

    @ColumnInfo(name = "address")
    public String address;


}

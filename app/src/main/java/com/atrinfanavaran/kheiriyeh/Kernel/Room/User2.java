package com.atrinfanavaran.kheiriyeh.Kernel.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User2 {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "city_name")
    public String city_name;

    @ColumnInfo(name = "last_name")
    public String lastName;
}

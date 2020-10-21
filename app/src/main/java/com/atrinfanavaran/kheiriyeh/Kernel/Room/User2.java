package com.atrinfanavaran.kheiriyeh.Kernel.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User2 {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "city_name")
    public String city_name;

    @ColumnInfo(name = "last_name")
    public String lastName;
}

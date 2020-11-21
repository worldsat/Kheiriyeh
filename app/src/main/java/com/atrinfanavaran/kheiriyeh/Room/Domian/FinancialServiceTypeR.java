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

    @ColumnInfo(name = "isNew")
    public String isNew;
}

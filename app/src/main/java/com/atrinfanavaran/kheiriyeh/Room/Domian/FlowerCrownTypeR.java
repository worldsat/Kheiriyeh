package com.atrinfanavaran.kheiriyeh.Room.Domian;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FlowerCrownTypeR {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "charityId")
    public int charityId;

    @ColumnInfo(name = "isNew")
    public String isNew;



    public FlowerCrownTypeR(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public FlowerCrownTypeR() {
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

    @Override
    public String toString() {
        return title;
    }
}

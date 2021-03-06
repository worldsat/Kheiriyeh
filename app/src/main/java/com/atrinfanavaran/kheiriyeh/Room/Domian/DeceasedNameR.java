package com.atrinfanavaran.kheiriyeh.Room.Domian;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class DeceasedNameR implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "deceasedFullName")
    public String deceasedFullName;

    @ColumnInfo(name = "deceaseAalias")
    public String deceaseAalias;

    @ColumnInfo(name = "deceasedSex")
    public boolean deceasedSex;
    public String deceasedSexStr;

    @ColumnInfo(name = "charityId")
    public int charityId;

    @ColumnInfo(name = "guidDeceasedName")
    public String  guidDeceasedName;



    @ColumnInfo(name = "isNew")
    public String isNew;

    public DeceasedNameR() {
    }

    public String getDeceasedSexStr() {
        return deceasedSexStr;
    }

    public String getGuidDeceasedName() {
        return guidDeceasedName;
    }

    public void setGuidDeceasedName(String guidDeceasedName) {
        this.guidDeceasedName = guidDeceasedName;
    }

    public void setDeceasedSexStr(String deceasedSexStr) {
        this.deceasedSexStr = deceasedSexStr;
    }

    public DeceasedNameR(int id, String deceasedFullName) {
        this.id = id;
        this.deceasedFullName = deceasedFullName;
    }

    public DeceasedNameR(boolean deceasedSex, String deceasedSexStr) {
        this.deceasedSex = deceasedSex;
        this.deceasedSexStr = deceasedSexStr;
    }

    public boolean isDeceasedSex() {
        return deceasedSex;
    }

    public void setDeceasedSex(boolean deceasedSex) {
        this.deceasedSex = deceasedSex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeceasedFullName() {
        return deceasedFullName;
    }

    public void setDeceasedFullName(String deceasedFullName) {
        this.deceasedFullName = deceasedFullName;
    }

    @Override
    public String toString() {
        return deceasedFullName;
    }
}

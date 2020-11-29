package com.atrinfanavaran.kheiriyeh.Room.Domian;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class DonatorR implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "donatorFullName")
    public String donatorFullName;

    @ColumnInfo(name = "charityId")
    public int charityId;

    @ColumnInfo(name = "isNew")
    public String isNew;

    @ColumnInfo(name = "donatorAlias")
    public String donatorAlias;

    @ColumnInfo(name = "donatorMobile")
    public String donatorMobile;

    @ColumnInfo(name = "isSendMessage")
    public boolean isSendMessage;

    @ColumnInfo(name = "guidDonator")
    public String guidDonator;

    @Override
    public String toString() {
        return donatorFullName;
    }

    public DonatorR() {
    }

    public DonatorR(int id, String donatorFullName) {
        this.id = id;
        this.donatorFullName = donatorFullName;
    }

    public String getGuidDonator() {
        return guidDonator;
    }

    public void setGuidDonator(String guidDonator) {
        this.guidDonator = guidDonator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDonatorFullName() {
        return donatorFullName;
    }

    public void setDonatorFullName(String donatorFullName) {
        this.donatorFullName = donatorFullName;
    }

    public int getCharityId() {
        return charityId;
    }

    public void setCharityId(int charityId) {
        this.charityId = charityId;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getDonatorAlias() {
        return donatorAlias;
    }

    public void setDonatorAlias(String donatorAlias) {
        this.donatorAlias = donatorAlias;
    }

    public String getDonatorMobile() {
        return donatorMobile;
    }

    public void setDonatorMobile(String donatorMobile) {
        this.donatorMobile = donatorMobile;
    }

    public boolean isSendMessage() {
        return isSendMessage;
    }

    public void setSendMessage(boolean sendMessage) {
        isSendMessage = sendMessage;
    }
}

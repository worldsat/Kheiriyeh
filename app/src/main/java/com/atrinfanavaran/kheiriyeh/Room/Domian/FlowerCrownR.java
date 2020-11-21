package com.atrinfanavaran.kheiriyeh.Room.Domian;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.atrinfanavaran.kheiriyeh.Domain.FlowerCrownApi;

import java.io.Serializable;

@Entity
public class FlowerCrownR implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "donator")
    public String donator;

    @ColumnInfo(name = "deceasedName")
    public String deceasedName;

    @ColumnInfo(name = "price")
    public int price;
    @ColumnInfo(name = "ceremonyType")
    public String ceremonyType;
    @ColumnInfo(name = "ceremonyTypeId")
    public int ceremonyTypeId;

    @ColumnInfo(name = "charityId")
    public int charityId;

    @ColumnInfo(name = "donatorId")
    public int donatorId;

    @ColumnInfo(name = "deceasedNameId")
    public int deceasedNameId;

    @ColumnInfo(name = "IntroducedId")
    public int IntroducedId;
    @ColumnInfo(name = "Introduced")
    public String Introduced;

    @ColumnInfo(name = "charity")
    public String charity;

    @ColumnInfo(name = "opratorId")
    public int opratorId;

    @ColumnInfo(name = "registerDate")
    public String registerDate;
    @ColumnInfo(name = "registerDateEn")
    public String registerDateEn;


    @ColumnInfo(name = "flowerCrownTypeId")
    public int flowerCrownTypeId;

    @ColumnInfo(name = "flowerCrownType")
    public String flowerCrownType;

    @ColumnInfo(name = "isNew")
    public String isNew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDonator() {
        return donator;
    }

    public void setDonator(String donator) {
        this.donator = donator;
    }

    public String getDeceasedName() {
        return deceasedName;
    }

    public void setDeceasedName(String deceasedName) {
        this.deceasedName = deceasedName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCeremonyType() {
        return ceremonyType;
    }

    public void setCeremonyType(String ceremonyType) {
        this.ceremonyType = ceremonyType;
    }

    public int getCeremonyTypeId() {
        return ceremonyTypeId;
    }

    public void setCeremonyTypeId(int ceremonyTypeId) {
        this.ceremonyTypeId = ceremonyTypeId;
    }

    public int getCharityId() {
        return charityId;
    }

    public void setCharityId(int charityId) {
        this.charityId = charityId;
    }

    public int getDonatorId() {
        return donatorId;
    }

    public void setDonatorId(int donatorId) {
        this.donatorId = donatorId;
    }

    public int getDeceasedNameId() {
        return deceasedNameId;
    }

    public void setDeceasedNameId(int deceasedNameId) {
        this.deceasedNameId = deceasedNameId;
    }

    public int getIntroducedId() {
        return IntroducedId;
    }

    public void setIntroducedId(int introducedId) {
        IntroducedId = introducedId;
    }

    public String getIntroduced() {
        return Introduced;
    }

    public void setIntroduced(String introduced) {
        Introduced = introduced;
    }

    public String getCharity() {
        return charity;
    }

    public void setCharity(String charity) {
        this.charity = charity;
    }

    public int getOpratorId() {
        return opratorId;
    }

    public void setOpratorId(int opratorId) {
        this.opratorId = opratorId;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public int getFlowerCrownTypeId() {
        return flowerCrownTypeId;
    }

    public void setFlowerCrownTypeId(int flowerCrownTypeId) {
        this.flowerCrownTypeId = flowerCrownTypeId;
    }

    public String getFlowerCrownType() {
        return flowerCrownType;
    }

    public void setFlowerCrownType(String flowerCrownType) {
        this.flowerCrownType = flowerCrownType;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }
}

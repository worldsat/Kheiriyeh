package com.atrinfanavaran.kheiriyeh.Room.Domian;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ContributionR implements Serializable {

    public ContributionR(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public ContributionR() {
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "deviceCode")
    public int deviceCode;

    @ColumnInfo(name = "terminalCode")
    public int terminalCode;

    @ColumnInfo(name = "recieverCode")
    public int recieverCode;

    @ColumnInfo(name = "fullName")
    public String fullName;

    @ColumnInfo(name = "code")
    public String code;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "mobile")
    public String mobile;

    @ColumnInfo(name = "nationalcode")
    public String nationalcode;

    @ColumnInfo(name = "phone")
    public String phone;

    @ColumnInfo(name = "birthDate")
    public String birthDate;

    @ColumnInfo(name = "charityId")
    public int charityId;

    @ColumnInfo(name = "charity")
    public String charity;

    @ColumnInfo(name = "opratorId")
    public int opratorId;

    @ColumnInfo(name = "SponsorId")
    public int SponsorId;

    @ColumnInfo(name = "isNew")
    public String isNew;

    @ColumnInfo(name = "payType")
    public int payType;


    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getSponsorId() {
        return SponsorId;
    }

    public void setSponsorId(int sponsorId) {
        SponsorId = sponsorId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(int deviceCode) {
        this.deviceCode = deviceCode;
    }

    public int getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(int terminalCode) {
        this.terminalCode = terminalCode;
    }

    public int getRecieverCode() {
        return recieverCode;
    }

    public void setRecieverCode(int recieverCode) {
        this.recieverCode = recieverCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNationalcode() {
        return nationalcode;
    }

    public void setNationalcode(String nationalcode) {
        this.nationalcode = nationalcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getCharityId() {
        return charityId;
    }

    public void setCharityId(int charityId) {
        this.charityId = charityId;
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

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }
}

package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

public class Charity  {

    /**
     * isActive : true
     * isAccessBox : false
     * isAccessFinancialAid : false
     * isAccessFlowerCrown : false
     * isAccessSponsor : false
     * id : 2
     */

    private boolean isActive;
    private boolean isAccessBox;
    private boolean isAccessFinancialAid;
    private boolean isAccessFlowerCrown;
    private boolean isAccessSponsor;
    private int id;


    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsAccessBox() {
        return isAccessBox;
    }

    public void setIsAccessBox(boolean isAccessBox) {
        this.isAccessBox = isAccessBox;
    }

    public boolean isIsAccessFinancialAid() {
        return isAccessFinancialAid;
    }

    public void setIsAccessFinancialAid(boolean isAccessFinancialAid) {
        this.isAccessFinancialAid = isAccessFinancialAid;
    }

    public boolean isIsAccessFlowerCrown() {
        return isAccessFlowerCrown;
    }

    public void setIsAccessFlowerCrown(boolean isAccessFlowerCrown) {
        this.isAccessFlowerCrown = isAccessFlowerCrown;
    }

    public boolean isIsAccessSponsor() {
        return isAccessSponsor;
    }

    public void setIsAccessSponsor(boolean isAccessSponsor) {
        this.isAccessSponsor = isAccessSponsor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

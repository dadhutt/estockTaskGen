package com.estock;

import java.util.ArrayList;

public class eStockSettings
{
    ArrayList<String> profiles;
    int priceCheck,quantity,numOfTasks;
    boolean makeNormal;

    public eStockSettings(ArrayList<String> profiles, int priceCheck, int quantity, int numOfTasks, boolean makeNormal) {
        this.profiles = profiles;
        this.priceCheck = priceCheck;
        this.quantity = quantity;
        this.numOfTasks = numOfTasks;
        this.makeNormal = makeNormal;
    }

    public int getNumOfTasks() {
        return numOfTasks;
    }

    public void setNumOfTasks(int numOfTasks) {
        this.numOfTasks = numOfTasks;
    }

    public boolean isMakeNormal() {
        return makeNormal;
    }

    public void setMakeNormal(boolean makeNormal) {
        this.makeNormal = makeNormal;
    }

    public eStockSettings(){}

    public ArrayList<String> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<String> profiles) {
        this.profiles = profiles;
    }

    public int getPriceCheck() {
        return priceCheck;
    }

    public void setPriceCheck(int priceCheck) {
        this.priceCheck = priceCheck;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

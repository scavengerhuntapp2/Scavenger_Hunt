package com.example.me5013zu.scavengerhunt;

import java.security.Timestamp;
import java.util.Date;

/**
 * Created by Alex on 11/22/16.
 */

public class ScavengerHunt {

    //Database Variables
    String mGameName;
    int id;
    String mDescription;
    Date mDateCreated;
    Timestamp startTime;
    Timestamp endTime;
    boolean checkbox1;
    boolean checkbox2;
    boolean checkbox3;


    //Constructor to set variables (helpful for coding)
    public ScavengerHunt(String gameName, String description, boolean checkbox1, boolean checkbox2, boolean checkbox3) {
        this.endTime = endTime;
        this.startTime = startTime;
        this.mDateCreated = new Date();
        this.mDescription = description;
        this.id = id;
        this.mGameName = gameName;
        this.checkbox1 = checkbox1;
        this.checkbox2 = checkbox2;
        this.checkbox3 = checkbox3;
    }

    @Override
    public String toString() {
        return "ScavengerHunt{" +
                "mGameName='" + mGameName + '\'' +
                ", id=" + id +
                ", mDescription='" + mDescription + '\'' +
                ", mDateCreated=" + mDateCreated +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", checkbox1=" + checkbox1 +
                ", checkbox2=" + checkbox2 +
                ", checkbox3=" + checkbox3 +
                '}';
    }

    //Getters and Setters created for Database items in the Firebase
    public String getGameName() {
        return mGameName;
    }

    public void setGameName(String gameName) {
        mGameName = gameName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        mDateCreated = dateCreated;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public boolean isCheckbox3() {
        return checkbox3;
    }

    public void setCheckbox3(boolean checkbox3) {
        this.checkbox3 = checkbox3;
    }

    public boolean isCheckbox2() {
        return checkbox2;
    }

    public void setCheckbox2(boolean checkbox2) {
        this.checkbox2 = checkbox2;
    }

    public boolean isCheckbox1() {
        return checkbox1;
    }

    public void setCheckbox1(boolean checkbox1) {
        this.checkbox1 = checkbox1;
    }






}

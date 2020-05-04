package com.thenneem.omnitrail.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Temple implements Serializable  {

    @SerializedName("TempleID")
    private int TempleID;

    @SerializedName("TempleName")
    private String TempleName;

    @SerializedName("ReligionName")
    private String ReligionName;

    @SerializedName("TempleStory")
    private String TempleStory;

    @SerializedName("TempleIMG")
    private String TempleIMG;


    public int getTempleID() {
        return TempleID;
    }

    public String getTempleName() {
        return TempleName;
    }

    public String getReligionName() {
        return ReligionName;
    }

    public String getTempleStory() {
        return TempleStory;
    }

    public String getTempleIMG() {
        return TempleIMG;
    }

    public Temple (int TempleID, String TempleName, String ReligionName, String TempleStory, String TempleIMG) {

        this.TempleID = TempleID;
        this.TempleName = TempleName;
        this.ReligionName = ReligionName;
        this.TempleStory = TempleStory;
        this.TempleIMG = TempleIMG;


    }
}

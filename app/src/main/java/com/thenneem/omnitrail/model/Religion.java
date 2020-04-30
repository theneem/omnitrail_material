package com.thenneem.omnitrail.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Religion {

    @SerializedName("ReligionID")
    private int ReligionID;

    @SerializedName("ReligionName")
    private String ReligionName;

    @SerializedName("ReligionDesc")
    private String ReligionDesc;

    @SerializedName("headerimg")
    private String headerimg;

    @SerializedName("primarybook")
    private String primarybook;

    @SerializedName("noofgod")
    private String noofgod;

    @SerializedName("nooftemple")
    private String nooftemple;

    @SerializedName("noofsaint")
    private String noofsaint;


    public Religion(int ReligionID, String ReligionName, String ReligionDesc, String headerimg, String primarybook, String noofgod, String nooftemple, String noofsaint){

        this.ReligionID = ReligionID;
        this.ReligionName = ReligionName;
        this.ReligionDesc = ReligionDesc;
        this.headerimg = headerimg;
        this.primarybook = primarybook;
        this.noofgod = noofgod;
        this.nooftemple = nooftemple;
        this.noofsaint = noofsaint;


    }

    public int getReligionID() {
        return ReligionID;
    }

    public String getReligionName() {
        return ReligionName;
    }

    public String getReligionDesc() {
        return ReligionDesc;
    }

    public String getHeaderimg() {
        return headerimg;
    }

    public String getPrimarybook() {
        return primarybook;
    }

    public String getNoofgod() {
        return noofgod;
    }

    public String getNooftemple() {
        return nooftemple;
    }

    public String getNoofsaint() {
        return noofsaint;
    }
}

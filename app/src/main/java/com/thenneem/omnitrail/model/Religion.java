package com.thenneem.omnitrail.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Religion implements Serializable {

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

    @SerializedName("nooftrail")
    private String nooftrail;



    public Religion(int ReligionID, String ReligionName, String ReligionDesc, String headerimg, String primarybook, String noofgod, String nooftemple, String noofsaint, String nooftrail){

        this.ReligionID = ReligionID;
        this.ReligionName = ReligionName;
        this.ReligionDesc = ReligionDesc;
        this.headerimg = headerimg;
        this.primarybook = primarybook;
        this.noofgod = noofgod;
        this.nooftemple = nooftemple;
        this.noofsaint = noofsaint;
        this.nooftrail = nooftrail ;



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

    public String getNooftrail() {
        return nooftrail;
    }


}

package com.thenneem.omnitrail.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Saint   implements Serializable {

    @SerializedName("SaintID")
    private int SaintID;

    @SerializedName("SaintName")
    private String SaintName;


    @SerializedName("SectName")
    private String SectName;


    @SerializedName("Samudai")
    private String Samudai;


    @SerializedName("SaintDesc")
    private String SaintDesc;

    @SerializedName("SaintStory")
    private String SaintStory;



    @SerializedName("BirthDate")
    private Date BirthDate;


    @SerializedName("SaintDate")
    private Date SaintDate;


    @SerializedName("DeathDate")
    private Date DeathDate;


    @SerializedName("SaintIMG")
    private String SaintIMG;


    public Saint (int SaintID, String SaintName, String SectName, String Samudai, String SaintDesc, String SaintStory,Date BirthDate, Date SaintDate, Date DeathDate, String SaintIMG) {

        this.SaintID = SaintID;
        this.SaintName = SaintName;
        this.Samudai = Samudai;
        this.SectName = SectName;
        this.SaintDesc = SaintDesc;
        this.SaintStory = SaintStory;
        this.BirthDate = BirthDate;
        this.SaintDate = SaintDate;
        this.DeathDate = DeathDate;
        this.SaintIMG = SaintIMG;


    }

    public int getSaintID() {
        return SaintID;
    }

    public String getSaintName() {
        return SaintName;
    }

    public String getSectName() {
        return SectName;
    }

    public String getSamudai() {
        return Samudai;
    }

    public String getSaintDesc() {
        return SaintDesc;
    }

    public String getSaintStory() {
        return SaintStory;
    }

    public Date getBirthDate() {
        return BirthDate;
    }

    public Date getSaintDate() {
        return SaintDate;
    }

    public Date getDeathDate() {
        return DeathDate;
    }

    public String getSaintIMG() {
        return SaintIMG;
    }
}

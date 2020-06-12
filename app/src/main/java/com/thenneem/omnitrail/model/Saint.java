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


    @SerializedName("ParentSaintName")
    private String ParentSaintName;

    @SerializedName("ReligionName")
    private String ReligionName;


    @SerializedName("SectName")
    private String SectName;


    @SerializedName("Samudai")
    private String Samudai;


    @SerializedName("SaintDesc")
    private String SaintDesc;

    @SerializedName("SaintStory")
    private String SaintStory;


    @SerializedName("BirthDate")
    private String BirthDate;


    @SerializedName("SaintDate")
    private String SaintDate;


    @SerializedName("DeathDate")
    private String DeathDate;


    @SerializedName("SaintIMG")
    private String SaintIMG;

    @SerializedName("CurrentAddress")
    private String CurrentAddress;


    @SerializedName("wiki_link")
    private String wiki_link;


    @SerializedName("ChiefFollower")
    private String ChiefFollower;


    @SerializedName("ChiefFollowerContact")
    private String ChiefFollowerContact;


    public Saint (int SaintID, String SaintName, String ParentSaintName,  String  ReligionName ,String SectName, String Samudai, String SaintDesc,
                  String SaintStory,String BirthDate, String SaintDate, String DeathDate, String SaintIMG, String CurrentAddress, String wiki_link,
                  String ChiefFollower, String ChiefFollowerContact) {


        this.SaintID = SaintID;
        this.SaintName = SaintName;
        this.ParentSaintName = ParentSaintName;
        this.ReligionName = ReligionName;
        this.Samudai = Samudai;
        this.SectName = SectName;
        this.SaintDesc = SaintDesc;
        this.SaintStory = SaintStory;
        this.BirthDate = BirthDate;
        this.SaintDate = SaintDate;
        this.DeathDate = DeathDate;
        this.SaintIMG = SaintIMG;
        this.CurrentAddress = CurrentAddress;
        this.wiki_link = wiki_link;
        this.ChiefFollower = ChiefFollower;
        this.ChiefFollowerContact = ChiefFollowerContact;


    }

    public int getSaintID() {
        return SaintID;
    }

    public String getSaintName() {
        return SaintName;
    }

    public String getParentSaintName() {
        return ParentSaintName;
    }

    public String getReligionName() {
        return ReligionName;
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

    public String getBirthDate() {
        return BirthDate;
    }

    public String getSaintDate() {
        return SaintDate;
    }

    public String getDeathDate() {
        return DeathDate;
    }

    public String getSaintIMG() {
        return SaintIMG;
    }

    public String getCurrentAddress() {
        return CurrentAddress;
    }

    public String getWiki_link() {
        return wiki_link;
    }

    public String getChiefFollower() {
        return ChiefFollower;
    }

    public String getChiefFollowerContact() {
        return ChiefFollowerContact;
    }



}

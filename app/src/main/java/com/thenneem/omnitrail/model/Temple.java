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

    @SerializedName("address")
    private String address;

    @SerializedName("zip")
    private String zip;

    @SerializedName("lang")
    private double lang;

    @SerializedName("lat")
    private double lat;

    @SerializedName("city_name")
    private String city_name;

    @SerializedName("state_name")
    private String state_name;

    @SerializedName("country_name")
    private String country_name;

    @SerializedName("wiki_link")
    private String wiki_link;


    @SerializedName("PrimaryDeity")
    private String PrimaryDeity;

    @SerializedName("GoverningBody")
    private String GoverningBody;

    @SerializedName("ContactPerson")
    private String ContactPerson;

    @SerializedName("ContactNumber")
    private String ContactNumber;

    @SerializedName("Creator")
    private String Creator;

    @SerializedName("CompletionPerios")
    private String CompletionPerios;


    @SerializedName("created_by")
    private String created_by;


    public String getPrimaryDeity() {
        return PrimaryDeity;
    }

    public String getGoverningBody() {
        return GoverningBody;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public String getCreator() {
        return Creator;
    }

    public String getCompletionPerios() {
        return CompletionPerios;
    }

    public String getWiki_link() {
        return wiki_link;
    }

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

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public double getLang() {
        return lang;
    }

    public double getLat() {
        return lat;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getState_name() {
        return state_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getCreatedBy() { return  created_by;}



    public Temple (int TempleID, String TempleName, String ReligionName, String TempleStory, String TempleIMG,
                   String address, String zip, double lang, double lat,
                   String city_name, String state_name, String country_name, String created_By

                   ) {

        this.TempleID = TempleID;
        this.TempleName = TempleName;
        this.ReligionName = ReligionName;
        this.TempleStory = TempleStory;
        this.TempleIMG = TempleIMG;
        this.address = address;
        this.zip = zip;
        this.lang = lang;
        this.lat = lat;
        this.city_name = city_name;
        this.state_name = state_name;
        this.country_name = country_name;
        this.created_by = created_By;



    }
}

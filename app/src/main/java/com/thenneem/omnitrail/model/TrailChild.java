package com.thenneem.omnitrail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrailChild {
    @SerializedName("TempleName")
    @Expose
    private String templeName;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("PrimaryDeity")
    @Expose
    private String primaryDeity;
    @SerializedName("TempleIMG")
    @Expose
    private String templeIMG;

    public String getTempleName() {
        return templeName;
    }

    public void setTempleName(String templeName) {
        this.templeName = templeName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPrimaryDeity() {
        return primaryDeity;
    }

    public void setPrimaryDeity(String primaryDeity) {
        this.primaryDeity = primaryDeity;
    }

    public String getTempleIMG() {
        return templeIMG;
    }

    public void setTempleIMG(String templeIMG) {
        this.templeIMG = templeIMG;
    }
}

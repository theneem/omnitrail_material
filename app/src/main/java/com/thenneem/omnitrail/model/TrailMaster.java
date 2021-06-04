package com.thenneem.omnitrail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrailMaster {
    @SerializedName("trailName")
    @Expose
    private String trailName;
    @SerializedName("trailPlaces")
    @Expose
    private String trailPlaces;
    @SerializedName("trailLength")
    @Expose
    private String trailLength;
    @SerializedName("avgDays")
    @Expose
    private String avgDays;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("created_by")
    @Expose
    private String createdBy;

    public String getTrailName() {
        return trailName;
    }

    public void setTrailName(String trailName) {
        this.trailName = trailName;
    }

    public String getTrailPlaces() {
        return trailPlaces;
    }

    public void setTrailPlaces(String trailPlaces) {
        this.trailPlaces = trailPlaces;
    }

    public String getTrailLength() {
        return trailLength;
    }

    public void setTrailLength(String trailLength) {
        this.trailLength = trailLength;
    }

    public String getAvgDays() {
        return avgDays;
    }

    public void setAvgDays(String avgDays) {
        this.avgDays = avgDays;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}

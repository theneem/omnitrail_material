package com.thenneem.omnitrail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TempleImages {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("objectid")
    @Expose
    private String objectid;
    @SerializedName("imgURL")
    @Expose
    private String imgURL;
    @SerializedName("created_by")
    @Expose
    private String createdBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectid;
    }

    public void setObjectId(String objectid) {
        this.objectid = objectid;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}

package com.thenneem.omnitrail.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Feature  implements Serializable {


    @SerializedName("FeatureID")
    private int FeatureID;

    @SerializedName("FeatureName")
    private String FeatureName;

    @SerializedName("FeatureDetails")
    private String FeatureDetails;

    @SerializedName("FeatureType")
    private String FeatureType;

    @SerializedName("FeatureParentID")
    private int FeatureParentID;

    @SerializedName("FeatureImg")
    private String FeatureImg;

    public int getFeatureID() {
        return FeatureID;
    }

    public String getFeatureName() {
        return FeatureName;
    }

    public String getFeatureDetails() {
        return FeatureDetails;
    }

    public String getFeatureType() {
        return FeatureType;
    }

    public int getFeatureParentID() {
        return FeatureParentID;
    }

    public String getFeatureImg() {
        return FeatureImg;
    }

    public Feature(int FeatureID, String FeatureName, String FeatureDetails, String FeatureType,int FeatureParentID, String FeatureImg ) {

        this.FeatureID = FeatureID;
        this.FeatureName = FeatureName;
        this.FeatureDetails = FeatureDetails;
        this.FeatureType = FeatureType;
        this.FeatureParentID = FeatureParentID;
        this.FeatureImg = FeatureImg;

    }
}

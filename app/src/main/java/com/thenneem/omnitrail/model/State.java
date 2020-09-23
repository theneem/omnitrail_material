package com.thenneem.omnitrail.model;

import com.google.gson.annotations.SerializedName;

public class State {
    @SerializedName("state_code")
    private String stateCode;
    @SerializedName("state_name")
    private String stateName;

    public State(String stateCode, String stateName) {
        this.stateCode = stateCode;
        this.stateName = stateName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String toString() {
        return stateName;
    }
}

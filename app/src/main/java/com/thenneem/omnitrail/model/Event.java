package com.thenneem.omnitrail.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

    @SerializedName("EventID")
    private int EventID;

    @SerializedName("EventName")
    private String EventName;

    @SerializedName("EventDetails")
    private String EventDetails;

    @SerializedName("EventType")
    private String EventType;

    @SerializedName("EventParentID")
    private String EventParentID;

    @SerializedName("EentStartDate")
    private String EentStartDate;

    @SerializedName("EventEndDate")
    private String EventEndDate;

    @SerializedName("EventImg")
    private String EventImg;

    @SerializedName("TempleName")
    private String TempleName;

    public int getEventID() {
        return EventID;
    }

    public String getEventName() {
        return EventName;
    }

    public String getEventDetails() {
        return EventDetails;
    }

    public String getEventType() {
        return EventType;
    }

    public String getEventParentID() {
        return EventParentID;
    }

    public String getEentStartDate() {
        return EentStartDate;
    }

    public String getEventEndDate() {
        return EventEndDate;
    }

    public String getEventImg() {
        return EventImg;
    }

    public String getTempleName() {
        return TempleName;
    }

public Event ( int EventID, String EventName, String EventDetails,String EventType,String EventParentID,String EentStartDate, String EventEndDate, String EventImg, String TempleName ){


        this.EventID = EventID;
        this.EventName = EventName;
        this.EventDetails = EventDetails;
        this.EventType = EventType;
        this.EventParentID = EventParentID;
        this.EentStartDate = EentStartDate;
        this.EventEndDate = EventEndDate;
        this.EventImg = EventImg;
        this.TempleName = TempleName;

    }
}

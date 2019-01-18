package com.example.andrealfarysie.warungdanaspv200.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListActivityTemplate {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mst_outlet_outlet_name")
    @Expose
    private String mstOutletOutletName;
    @SerializedName("activity_mst_type_type")
    @Expose
    private String activityMstTypeType;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMstOutletOutletName() {
        return mstOutletOutletName;
    }

    public void setMstOutletOutletName(String mstOutletOutletName) {
        this.mstOutletOutletName = mstOutletOutletName;
    }

    public String getActivityMstTypeType() {
        return activityMstTypeType;
    }

    public void setActivityMstTypeType(String activityMstTypeType) {
        this.activityMstTypeType = activityMstTypeType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}

package com.example.andrealfarysie.warungdanaspv200.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListUserReport {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("activity_mst_status_status")
    @Expose
    private String activityMstStatusStatus;
    @SerializedName("brosur")
    @Expose
    private Object brosur;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivityMstStatusStatus() {
        return activityMstStatusStatus;
    }

    public void setActivityMstStatusStatus(String activityMstStatusStatus) {
        this.activityMstStatusStatus = activityMstStatusStatus;
    }

    public Object getBrosur() {
        return brosur;
    }

    public void setBrosur(Object brosur) {
        this.brosur = brosur;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getCmsUsersName() {
        return cmsUsersName;
    }

    public void setCmsUsersName(String cmsUsersName) {
        this.cmsUsersName = cmsUsersName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}

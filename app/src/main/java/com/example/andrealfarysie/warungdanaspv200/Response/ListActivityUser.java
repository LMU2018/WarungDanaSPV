package com.example.andrealfarysie.warungdanaspv200.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListActivityUser {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_cms_users")
    @Expose
    private Integer idCmsUsers;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("id_mst_status")
    @Expose
    private Integer idMstStatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("note")
    @Expose
    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCmsUsers() {
        return idCmsUsers;
    }

    public void setIdCmsUsers(Integer idCmsUsers) {
        this.idCmsUsers = idCmsUsers;
    }

    public String getCmsUsersName() {
        return cmsUsersName;
    }

    public void setCmsUsersName(String cmsUsersName) {
        this.cmsUsersName = cmsUsersName;
    }

    public Integer getIdMstStatus() {
        return idMstStatus;
    }

    public void setIdMstStatus(Integer idMstStatus) {
        this.idMstStatus = idMstStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}

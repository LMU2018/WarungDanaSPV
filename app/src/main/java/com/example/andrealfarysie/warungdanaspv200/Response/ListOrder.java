package com.example.andrealfarysie.warungdanaspv200.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOrder {

    public ListOrder(Integer id,Integer idcontact, String outlet, String namdep,String nambel, String status,String reason,
                     String cfa, Integer idunit,String model,String tanggal,Integer plafond){
        this.id = id;
        this.mstOutletOutletName = outlet;
        this.contactFirstName = namdep;
        this.contactLastName = nambel;
        this.orderMstStatusStatus = status;
        this.orderMstReasonReason = reason;
        this.cmsUsersName = cfa;
        this.idMstUnit = idunit;
        this.model = model;
        this.createdAt = tanggal;
        this.plafond = plafond;
        this.idContact = idcontact;

    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mst_outlet_outlet_name")
    @Expose
    private String mstOutletOutletName;
    @SerializedName("id_contact")
    @Expose
    private Integer idContact;
    @SerializedName("contact_first_name")
    @Expose
    private String contactFirstName;
    @SerializedName("contact_last_name")
    @Expose
    private String contactLastName;
    @SerializedName("order_mst_status_status")
    @Expose
    private String orderMstStatusStatus;
    @SerializedName("order_mst_reason_reason")
    @Expose
    private String orderMstReasonReason;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("id_mst_unit")
    @Expose
    private Integer idMstUnit;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("plafond")
    @Expose
    private Integer plafond;

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

    public Integer getIdContact() {
        return idContact;
    }

    public void setIdContact(Integer idContact) {
        this.idContact = idContact;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getOrderMstStatusStatus() {
        return orderMstStatusStatus;
    }

    public void setOrderMstStatusStatus(String orderMstStatusStatus) {
        this.orderMstStatusStatus = orderMstStatusStatus;
    }

    public String getCmsUsersName() {
        return cmsUsersName;
    }

    public void setCmsUsersName(String cmsUsersName) {
        this.cmsUsersName = cmsUsersName;
    }

    public Integer getIdMstUnit() {
        return idMstUnit;
    }

    public void setIdMstUnit(Integer idMstUnit) {
        this.idMstUnit = idMstUnit;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOrderMstReasonReason() {
        return orderMstReasonReason;
    }

    public void setOrderMstReasonReason(String orderMstReasonReason) {
        this.orderMstReasonReason = orderMstReasonReason;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getPlafond() {
        return plafond;
    }

    public void setPlafond(Integer plafond) {
        this.plafond = plafond;
    }
}

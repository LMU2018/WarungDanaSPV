package com.example.andrealfarysie.warungdanaspv200.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetail {
    @SerializedName("api_status")
    @Expose
    private Integer apiStatus;
    @SerializedName("api_message")
    @Expose
    private String apiMessage;
    @SerializedName("api_response_fields")
    @Expose
    private List<String> apiResponseFields = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mst_data_source_datasource")
    @Expose
    private String mstDataSourceDatasource;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("id_mst_outlet")
    @Expose
    private Integer idMstOutlet;
    @SerializedName("mst_outlet_outlet_name")
    @Expose
    private String mstOutletOutletName;
    @SerializedName("mst_outlet_outlet_fif_code")
    @Expose
    private String mstOutletOutletFifCode;
    @SerializedName("mst_outlet_outlet_desc")
    @Expose
    private String mstOutletOutletDesc;
    @SerializedName("mst_outlet_outlet_status")
    @Expose
    private String mstOutletOutletStatus;
    @SerializedName("mst_outlet_outlet_lat")
    @Expose
    private Object mstOutletOutletLat;
    @SerializedName("mst_outlet_outlet_lng")
    @Expose
    private Object mstOutletOutletLng;
    @SerializedName("mst_outlet_updated_by")
    @Expose
    private Object mstOutletUpdatedBy;
    @SerializedName("id_mst_cabang_fif")
    @Expose
    private Integer idMstCabangFif;
    @SerializedName("mst_cabang_fif_code_cab_fif")
    @Expose
    private Integer mstCabangFifCodeCabFif;
    @SerializedName("mst_cabang_fif_branch_name")
    @Expose
    private String mstCabangFifBranchName;
    @SerializedName("mst_cabang_fif_code_pos_fif")
    @Expose
    private Integer mstCabangFifCodePosFif;
    @SerializedName("mst_cabang_fif_pos_name")
    @Expose
    private String mstCabangFifPosName;
    @SerializedName("id_contact")
    @Expose
    private Integer idContact;
    @SerializedName("id_mst_product")
    @Expose
    private Integer idMstProduct;
    @SerializedName("order_mst_status_status")
    @Expose
    private String orderMstStatusStatus;
    @SerializedName("order_mst_reason_reason")
    @Expose
    private String orderMstReasonReason;
    @SerializedName("survey")
    @Expose
    private String survey;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(Integer apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getApiMessage() {
        return apiMessage;
    }

    public void setApiMessage(String apiMessage) {
        this.apiMessage = apiMessage;
    }

    public List<String> getApiResponseFields() {
        return apiResponseFields;
    }

    public void setApiResponseFields(List<String> apiResponseFields) {
        this.apiResponseFields = apiResponseFields;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMstDataSourceDatasource() {
        return mstDataSourceDatasource;
    }

    public void setMstDataSourceDatasource(String mstDataSourceDatasource) {
        this.mstDataSourceDatasource = mstDataSourceDatasource;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getIdMstOutlet() {
        return idMstOutlet;
    }

    public void setIdMstOutlet(Integer idMstOutlet) {
        this.idMstOutlet = idMstOutlet;
    }

    public String getMstOutletOutletName() {
        return mstOutletOutletName;
    }

    public void setMstOutletOutletName(String mstOutletOutletName) {
        this.mstOutletOutletName = mstOutletOutletName;
    }

    public String getMstOutletOutletFifCode() {
        return mstOutletOutletFifCode;
    }

    public void setMstOutletOutletFifCode(String mstOutletOutletFifCode) {
        this.mstOutletOutletFifCode = mstOutletOutletFifCode;
    }

    public String getMstOutletOutletDesc() {
        return mstOutletOutletDesc;
    }

    public void setMstOutletOutletDesc(String mstOutletOutletDesc) {
        this.mstOutletOutletDesc = mstOutletOutletDesc;
    }

    public String getMstOutletOutletStatus() {
        return mstOutletOutletStatus;
    }

    public void setMstOutletOutletStatus(String mstOutletOutletStatus) {
        this.mstOutletOutletStatus = mstOutletOutletStatus;
    }

    public Object getMstOutletOutletLat() {
        return mstOutletOutletLat;
    }

    public void setMstOutletOutletLat(Object mstOutletOutletLat) {
        this.mstOutletOutletLat = mstOutletOutletLat;
    }

    public Object getMstOutletOutletLng() {
        return mstOutletOutletLng;
    }

    public void setMstOutletOutletLng(Object mstOutletOutletLng) {
        this.mstOutletOutletLng = mstOutletOutletLng;
    }

    public Object getMstOutletUpdatedBy() {
        return mstOutletUpdatedBy;
    }

    public void setMstOutletUpdatedBy(Object mstOutletUpdatedBy) {
        this.mstOutletUpdatedBy = mstOutletUpdatedBy;
    }

    public Integer getIdMstCabangFif() {
        return idMstCabangFif;
    }

    public void setIdMstCabangFif(Integer idMstCabangFif) {
        this.idMstCabangFif = idMstCabangFif;
    }

    public Integer getMstCabangFifCodeCabFif() {
        return mstCabangFifCodeCabFif;
    }

    public void setMstCabangFifCodeCabFif(Integer mstCabangFifCodeCabFif) {
        this.mstCabangFifCodeCabFif = mstCabangFifCodeCabFif;
    }

    public String getMstCabangFifBranchName() {
        return mstCabangFifBranchName;
    }

    public void setMstCabangFifBranchName(String mstCabangFifBranchName) {
        this.mstCabangFifBranchName = mstCabangFifBranchName;
    }

    public Integer getMstCabangFifCodePosFif() {
        return mstCabangFifCodePosFif;
    }

    public void setMstCabangFifCodePosFif(Integer mstCabangFifCodePosFif) {
        this.mstCabangFifCodePosFif = mstCabangFifCodePosFif;
    }

    public String getMstCabangFifPosName() {
        return mstCabangFifPosName;
    }

    public void setMstCabangFifPosName(String mstCabangFifPosName) {
        this.mstCabangFifPosName = mstCabangFifPosName;
    }

    public Integer getIdContact() {
        return idContact;
    }

    public void setIdContact(Integer idContact) {
        this.idContact = idContact;
    }

    public Integer getIdMstProduct() {
        return idMstProduct;
    }

    public void setIdMstProduct(Integer idMstProduct) {
        this.idMstProduct = idMstProduct;
    }

    public String getOrderMstStatusStatus() {
        return orderMstStatusStatus;
    }

    public void setOrderMstStatusStatus(String orderMstStatusStatus) {
        this.orderMstStatusStatus = orderMstStatusStatus;
    }

    public String getOrderMstReasonReason() {
        return orderMstReasonReason;
    }

    public void setOrderMstReasonReason(String orderMstReasonReason) {
        this.orderMstReasonReason = orderMstReasonReason;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
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

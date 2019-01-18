package com.example.andrealfarysie.warungdanaspv200.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactDetail {
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
    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("birth_place")
    @Expose
    private String birthPlace;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("id_mst_religion")
    @Expose
    private Integer idMstReligion;
    @SerializedName("mst_religion_agama")
    @Expose
    private String mstReligionAgama;
    @SerializedName("contact_mst_status_marital_status")
    @Expose
    private String contactMstStatusMaritalStatus;
    @SerializedName("mst_job_job")
    @Expose
    private String mstJobJob;
    @SerializedName("mst_data_source_datasource")
    @Expose
    private String mstDataSourceDatasource;

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

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getIdMstReligion() {
        return idMstReligion;
    }

    public void setIdMstReligion(Integer idMstReligion) {
        this.idMstReligion = idMstReligion;
    }

    public String getMstReligionAgama() {
        return mstReligionAgama;
    }

    public void setMstReligionAgama(String mstReligionAgama) {
        this.mstReligionAgama = mstReligionAgama;
    }

    public String getContactMstStatusMaritalStatus() {
        return contactMstStatusMaritalStatus;
    }

    public void setContactMstStatusMaritalStatus(String contactMstStatusMaritalStatus) {
        this.contactMstStatusMaritalStatus = contactMstStatusMaritalStatus;
    }

    public String getMstJobJob() {
        return mstJobJob;
    }

    public void setMstJobJob(String mstJobJob) {
        this.mstJobJob = mstJobJob;
    }

    public String getMstDataSourceDatasource() {
        return mstDataSourceDatasource;
    }

    public void setMstDataSourceDatasource(String mstDataSourceDatasource) {
        this.mstDataSourceDatasource = mstDataSourceDatasource;
    }
}

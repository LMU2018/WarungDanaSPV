package com.example.andrealfarysie.warungdanaspv200.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdditionalContactDetail {
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
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("mother")
    @Expose
    private String mother;
    @SerializedName("family")
    @Expose
    private Integer family;
    @SerializedName("contact_mst_status_place_status")
    @Expose
    private String contactMstStatusPlaceStatus;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("contact_mst_status_employee_status")
    @Expose
    private String contactMstStatusEmployeeStatus;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("working_time")
    @Expose
    private Integer workingTime;
    @SerializedName("income")
    @Expose
    private Integer income;
    @SerializedName("outlay")
    @Expose
    private Integer outlay;

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

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public Integer getFamily() {
        return family;
    }

    public void setFamily(Integer family) {
        this.family = family;
    }

    public String getContactMstStatusPlaceStatus() {
        return contactMstStatusPlaceStatus;
    }

    public void setContactMstStatusPlaceStatus(String contactMstStatusPlaceStatus) {
        this.contactMstStatusPlaceStatus = contactMstStatusPlaceStatus;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContactMstStatusEmployeeStatus() {
        return contactMstStatusEmployeeStatus;
    }

    public void setContactMstStatusEmployeeStatus(String contactMstStatusEmployeeStatus) {
        this.contactMstStatusEmployeeStatus = contactMstStatusEmployeeStatus;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(Integer workingTime) {
        this.workingTime = workingTime;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Integer getOutlay() {
        return outlay;
    }

    public void setOutlay(Integer outlay) {
        this.outlay = outlay;
    }
}

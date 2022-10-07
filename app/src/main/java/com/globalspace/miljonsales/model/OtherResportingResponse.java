package com.globalspace.miljonsales.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OtherResportingResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("error")
    private String error;

    @SerializedName("errorMessage")
    private String errorMessage;

    @SerializedName("data")
    private ArrayList<OtherReporting> otherReoprting;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ArrayList<OtherReporting> getOtherReoprting() {
        return otherReoprting;
    }

    public void setOtherReoprting(ArrayList<OtherReporting> otherReoprting) {
        this.otherReoprting = otherReoprting;
    }

}

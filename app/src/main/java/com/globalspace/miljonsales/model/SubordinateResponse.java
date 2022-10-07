package com.globalspace.miljonsales.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SubordinateResponse implements Serializable {

    @SerializedName("status")
    private String status;

    @SerializedName("error")
    private String error;

    @SerializedName("errorMessage")
    private String errorMessage;

    @SerializedName("data")
    private ArrayList<SubordinateData> subordinateData;


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

    public ArrayList<SubordinateData> getSubordinateData() {
        return subordinateData;
    }

    public void setSubordinateData(ArrayList<SubordinateData> subordinateData) {
        this.subordinateData = subordinateData;
    }

    public class SubordinateData {

        @SerializedName("TABLE_NAME")
        private String tableName;

        @SerializedName("MEMPLOYEE_ID")
        private String subordinateId;

        @SerializedName("EMPLOYEE_NAME")
        private String subordinateName;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getSubordinateId() {
            return subordinateId;
        }

        public void setSubordinateId(String subordinateId) {
            this.subordinateId = subordinateId;
        }

        public String getSubordinateName() {
            return subordinateName;
        }

        public void setSubordinateName(String subordinateName) {
            this.subordinateName = subordinateName;
        }
    }
}

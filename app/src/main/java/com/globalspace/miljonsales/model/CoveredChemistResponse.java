package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoveredChemistResponse {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
    public class Datum {

        @SerializedName("CHEMIST_ID")
        @Expose
        private String cHEMISTID;
        @SerializedName("FST_NAME")
        @Expose
        private String fSTNAME;
        @SerializedName("CONTACT_NAME")
        @Expose
        private String cONTACTNAME;

        public String getCHEMISTID() {
            return cHEMISTID;
        }

        public void setCHEMISTID(String cHEMISTID) {
            this.cHEMISTID = cHEMISTID;
        }

        public String getFSTNAME() {
            return fSTNAME;
        }

        public void setFSTNAME(String fSTNAME) {
            this.fSTNAME = fSTNAME;
        }

        public String getCONTACTNAME() {
            return cONTACTNAME;
        }

        public void setCONTACTNAME(String cONTACTNAME) {
            this.cONTACTNAME = cONTACTNAME;
        }

    }
}

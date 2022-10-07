package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyPobResponse  {

    @SerializedName("0")
    @Expose
    private List<_0> _0 = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    public List<_0> get0() {
        return _0;
    }

    public void set0(List<_0> _0) {
        this._0 = _0;
    }

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

    public class _0 {

        @SerializedName("TABLE_NAME")
        @Expose
        private String tABLENAME;
        @SerializedName("ORDER_ID")
        @Expose
        private String oRDERID;
        @SerializedName("CHEMIST_ID")
        @Expose
        private String cHEMISTID;
        @SerializedName("STOCKIEST_ORG_ID")
        @Expose
        private String sTOCKIESTORGID;
        @SerializedName("STOCKIEST_NAME")
        @Expose
        private String sTOCKIESTNAME;
        @SerializedName("CHEMIST_ORG_ID")
        @Expose
        private String cHEMISTORGID;
        @SerializedName("CHEMIST_NAME")
        @Expose
        private String cHEMISTNAME;
        @SerializedName("TRANSACTION_DATE")
        @Expose
        private String tRANSACTIONDATE;
        @SerializedName("PURCHASE_ORDER_BOOKING")
        @Expose
        private String pURCHASEORDERBOOKING;
        @SerializedName("TRANSACTION_STATUS")
        @Expose
        private String tRANSACTIONSTATUS;
        @SerializedName("STATE")
        @Expose
        private String sTATE;
        @SerializedName("CITY")
        @Expose
        private Object cITY;
        @SerializedName("POSTAL_CODE")
        @Expose
        private String pOSTALCODE;
        @SerializedName("LANDMARK")
        @Expose
        private Object lANDMARK;
        @SerializedName("ADDRESS")
        @Expose
        private String aDDRESS;
        @SerializedName("LAT_LONG_ADDRESS")
        @Expose
        private String lATLONGADDRESS;
        @SerializedName("CREATED")
        @Expose
        private String cREATED;

        public String getTABLENAME() {
            return tABLENAME;
        }

        public void setTABLENAME(String tABLENAME) {
            this.tABLENAME = tABLENAME;
        }

        public String getORDERID() {
            return oRDERID;
        }

        public void setORDERID(String oRDERID) {
            this.oRDERID = oRDERID;
        }

        public String getCHEMISTID() {
            return cHEMISTID;
        }

        public void setCHEMISTID(String cHEMISTID) {
            this.cHEMISTID = cHEMISTID;
        }

        public String getSTOCKIESTORGID() {
            return sTOCKIESTORGID;
        }

        public void setSTOCKIESTORGID(String sTOCKIESTORGID) {
            this.sTOCKIESTORGID = sTOCKIESTORGID;
        }

        public String getSTOCKIESTNAME() {
            return sTOCKIESTNAME;
        }

        public void setSTOCKIESTNAME(String sTOCKIESTNAME) {
            this.sTOCKIESTNAME = sTOCKIESTNAME;
        }

        public String getCHEMISTORGID() {
            return cHEMISTORGID;
        }

        public void setCHEMISTORGID(String cHEMISTORGID) {
            this.cHEMISTORGID = cHEMISTORGID;
        }

        public String getCHEMISTNAME() {
            return cHEMISTNAME;
        }

        public void setCHEMISTNAME(String cHEMISTNAME) {
            this.cHEMISTNAME = cHEMISTNAME;
        }

        public String getTRANSACTIONDATE() {
            return tRANSACTIONDATE;
        }

        public void setTRANSACTIONDATE(String tRANSACTIONDATE) {
            this.tRANSACTIONDATE = tRANSACTIONDATE;
        }

        public String getPURCHASEORDERBOOKING() {
            return pURCHASEORDERBOOKING;
        }

        public void setPURCHASEORDERBOOKING(String pURCHASEORDERBOOKING) {
            this.pURCHASEORDERBOOKING = pURCHASEORDERBOOKING;
        }

        public String getTRANSACTIONSTATUS() {
            return tRANSACTIONSTATUS;
        }

        public void setTRANSACTIONSTATUS(String tRANSACTIONSTATUS) {
            this.tRANSACTIONSTATUS = tRANSACTIONSTATUS;
        }

        public String getSTATE() {
            return sTATE;
        }

        public void setSTATE(String sTATE) {
            this.sTATE = sTATE;
        }

        public Object getCITY() {
            return cITY;
        }

        public void setCITY(Object cITY) {
            this.cITY = cITY;
        }

        public String getPOSTALCODE() {
            return pOSTALCODE;
        }

        public void setPOSTALCODE(String pOSTALCODE) {
            this.pOSTALCODE = pOSTALCODE;
        }

        public Object getLANDMARK() {
            return lANDMARK;
        }

        public void setLANDMARK(Object lANDMARK) {
            this.lANDMARK = lANDMARK;
        }

        public String getADDRESS() {
            return aDDRESS;
        }

        public void setADDRESS(String aDDRESS) {
            this.aDDRESS = aDDRESS;
        }

        public String getLATLONGADDRESS() {
            return lATLONGADDRESS;
        }

        public void setLATLONGADDRESS(String lATLONGADDRESS) {
            this.lATLONGADDRESS = lATLONGADDRESS;
        }

        public String getCREATED() {
            return cREATED;
        }

        public void setCREATED(String cREATED) {
            this.cREATED = cREATED;
        }

    }
}

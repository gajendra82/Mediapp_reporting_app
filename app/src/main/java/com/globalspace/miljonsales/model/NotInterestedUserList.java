package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aravind on 9/8/18.
 */

public class NotInterestedUserList {
    @SerializedName("STATUS")
    @Expose
    private String sTATUS;
    @SerializedName("CHEMIST_NAME")
    @Expose
    private String cHEMISTNAME;
    @SerializedName("EMAIL")
    @Expose
    private String eMAIL;
    @SerializedName("MOBILE")
    @Expose
    private String mOBILE;
    @SerializedName("P_LAT")
    @Expose
    private String pLAT;
    @SerializedName("P_LONG")
    @Expose
    private String pLONG;

    @SerializedName("CHEMIST_ID")
    @Expose
    private String ChemID;

    public String getChemID() {
        return ChemID;
    }

    public void setChemID(String chemID) {
        ChemID = chemID;
    }

    public String getSTATUS() {
        return sTATUS;
    }

    public void setSTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }

    public NotInterestedUserList withSTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
        return this;
    }

    public String getCHEMISTNAME() {
        return cHEMISTNAME;
    }

    public void setCHEMISTNAME(String cHEMISTNAME) {
        this.cHEMISTNAME = cHEMISTNAME;
    }

    public NotInterestedUserList withCHEMISTNAME(String cHEMISTNAME) {
        this.cHEMISTNAME = cHEMISTNAME;
        return this;
    }

    public String getEMAIL() {
        return eMAIL;
    }

    public void setEMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
    }

    public NotInterestedUserList withEMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
        return this;
    }

    public String getMOBILE() {
        return mOBILE;
    }

    public void setMOBILE(String mOBILE) {
        this.mOBILE = mOBILE;
    }

    public NotInterestedUserList withMOBILE(String mOBILE) {
        this.mOBILE = mOBILE;
        return this;
    }

    public Object getPLAT() {
        return pLAT;
    }

    public void setPLAT(String pLAT) {
        this.pLAT = pLAT;
    }

    public NotInterestedUserList withPLAT(String pLAT) {
        this.pLAT = pLAT;
        return this;
    }

    public Object getPLONG() {
        return pLONG;
    }

    public void setPLONG(String pLONG) {
        this.pLONG = pLONG;
    }

    public NotInterestedUserList withPLONG(String pLONG) {
        this.pLONG = pLONG;
        return this;
    }
}

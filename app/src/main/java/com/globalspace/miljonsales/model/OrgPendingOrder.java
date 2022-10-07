package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrgPendingOrder {

    @SerializedName("stockistPendingOrderFromChm")
    public List<Stockist_response.getbasicstockistInfo> lststockist_array = null;
    @SerializedName("TABLE_NAME")

    @Expose
    private String tABLENAME;
    @SerializedName("ORDER_ID")
    @Expose
    private String oRDERID;
    @SerializedName("S_ORG_ID")
    @Expose
    private String sORGID;
    @SerializedName("ORDER_DATE")
    @Expose
    private String oRDERDATE;
    @SerializedName("DELIVERY_PERSON_ID")
    @Expose
    private Object dELIVERYPERSONID;
    @SerializedName("SALES_INVOICE_NO")
    @Expose
    private String sALESINVOICENO;

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

    public String getSORGID() {
        return sORGID;
    }

    public void setSORGID(String sORGID) {
        this.sORGID = sORGID;
    }

    public String getORDERDATE() {
        return oRDERDATE;
    }

    public void setORDERDATE(String oRDERDATE) {
        this.oRDERDATE = oRDERDATE;
    }

    public Object getDELIVERYPERSONID() {
        return dELIVERYPERSONID;
    }

    public void setDELIVERYPERSONID(Object dELIVERYPERSONID) {
        this.dELIVERYPERSONID = dELIVERYPERSONID;
    }

    public String getSALESINVOICENO() {
        return sALESINVOICENO;
    }

    public void setSALESINVOICENO(String sALESINVOICENO) {
        this.sALESINVOICENO = sALESINVOICENO;
    }
    public List<Stockist_response.getbasicstockistInfo> getLststockist_array() {
        return lststockist_array;
    }

    public void setLststockist_array(List<Stockist_response.getbasicstockistInfo> lststockist_array) {
        this.lststockist_array = lststockist_array;
    }
}

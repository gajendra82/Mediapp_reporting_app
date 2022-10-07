package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ManufactureInfo implements Serializable {

    @SerializedName("TABLE_NAME")
    @Expose
    private String tableName;
    @SerializedName("MANUFCTR_ID")
    @Expose
    private String manufctrId;
    @SerializedName("COMPANY_NAME")
    @Expose
    private String companyName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getManufctrId() {
        return manufctrId;
    }

    public void setManufctrId(String manufctrId) {
        this.manufctrId = manufctrId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}

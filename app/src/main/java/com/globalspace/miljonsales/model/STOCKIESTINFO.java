package com.globalspace.miljonsales.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aravind on 12/7/18.
 */

public class STOCKIESTINFO implements Parcelable {
    @SerializedName("TABLE_NAME")
    @Expose
    private String tABLENAME;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("ORG_ID")
    @Expose
    private String oRGID;
    @SerializedName("EMPLOYEE_ID")
    @Expose
    private String eMPLOYEEID;

    @SerializedName("ADDRESS")
    @Expose
    private String aDDRESS;
    
    @SerializedName("EMAIL")
    @Expose
    private String emailId;
    
    @SerializedName("LATITUDE")
    @Expose
    private String latitude;
    
    @SerializedName("LONGITUDE")
    @Expose
    private String longitude;
    
    @SerializedName("VERSION_FLAG")
    @Expose
    private String version_flag;
    
    public String getLatitude() {
        return latitude;
    }
    
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    public String getLongitude() {
        return longitude;
    }
    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    public String getVersion_flag() {
        return version_flag;
    }
    
    public void setVersion_flag(String version_flag) {
        this.version_flag = version_flag;
    }
    
    public String getEmailId() {
        return emailId;
    }
    
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    
    public STOCKIESTINFO(String str_name, String id) {
        this.nAME = str_name;
        this.eMPLOYEEID = id;
    }

    public STOCKIESTINFO(String str_table_name, String str_name,
                         String str_org_id, String str_emp_id, String address,String emailId) {

        this.tABLENAME = str_table_name;
        this.nAME = str_name;
        this.oRGID = str_org_id;
        this.eMPLOYEEID = str_emp_id;
        this.aDDRESS = address;
        this.emailId = emailId;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tABLENAME);
        dest.writeString(nAME);
        dest.writeString(oRGID);
        dest.writeString(eMPLOYEEID);
        dest.writeString(aDDRESS);
        dest.writeString(emailId);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(version_flag);

    }

    private STOCKIESTINFO(Parcel in) {
        this.tABLENAME = in.readString();
        this.nAME = in.readString();
        this.oRGID = in.readString();
        this.eMPLOYEEID = in.readString();
        this.aDDRESS = in.readString();
        this.emailId = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.version_flag = in.readString();
    }

    public static final Creator<STOCKIESTINFO> CREATOR =
            new Creator<STOCKIESTINFO>() {

                @Override
                public STOCKIESTINFO createFromParcel(Parcel source) {
                    return new STOCKIESTINFO(source);
                }

                @Override
                public STOCKIESTINFO[] newArray(int size) {
                    return new STOCKIESTINFO[size];
                }
            };

    public String getTABLENAME() {
        return tABLENAME;
    }

    public void setTABLENAME(String tABLENAME) {
        this.tABLENAME = tABLENAME;
    }

    public STOCKIESTINFO withTABLENAME(String tABLENAME) {
        this.tABLENAME = tABLENAME;
        return this;
    }

    public String getNAME() {
        return nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    public STOCKIESTINFO withNAME(String nAME) {
        this.nAME = nAME;
        return this;
    }

    public String getORGID() {
        return oRGID;
    }

    public void setORGID(String oRGID) {
        this.oRGID = oRGID;
    }

    public STOCKIESTINFO withORGID(String oRGID) {
        this.oRGID = oRGID;
        return this;
    }

    public String getEMPLOYEEID() {
        return eMPLOYEEID;
    }

    public void setEMPLOYEEID(String eMPLOYEEID) {
        this.eMPLOYEEID = eMPLOYEEID;
    }

    public STOCKIESTINFO withEMPLOYEEID(String eMPLOYEEID) {
        this.eMPLOYEEID = eMPLOYEEID;
        return this;
    }

    public String getaDDRESS() {
        return aDDRESS;
    }

    public void setaDDRESS(String aDDRESS) {
        this.aDDRESS = aDDRESS;
    }

}

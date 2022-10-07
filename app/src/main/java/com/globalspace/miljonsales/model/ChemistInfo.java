package com.globalspace.miljonsales.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aravind on 12/7/18.
 */

public class ChemistInfo implements Parcelable {

    @SerializedName("TABLE_NAME")
    @Expose
    private String tABLENAME;
    @SerializedName("CHEMIST_ID")
    @Expose
    private String cHEMISTID;
    @SerializedName("FST_NAME")
    @Expose
    private String fSTNAME;
    @SerializedName("ERR_ID")
    @Expose
    private String eRRID;

    @SerializedName("MEMPLOYEE_ID")
    @Expose
    private String mEMPLOYEEID;
    
    @SerializedName("EMAIL")
    @Expose
    private String emailId;


    @SerializedName("ADDRESS")
    @Expose
    private String aDDRESS;
    
    @SerializedName("LATITUDE")
    @Expose
    private String latitude;
    
    @SerializedName("LONGITUDE")
    @Expose
    private String longitude;
    
    
    @SerializedName("VERSION_FLAG")
    @Expose
    private String version_flag;

    @SerializedName("LAT_LONG_STATUS")
    @Expose
    private String lat_long_flag;

    public String getLat_long_flag() {
        return lat_long_flag;
    }

    public void setLat_long_flag(String lat_long_flag) {
        this.lat_long_flag = lat_long_flag;
    }

    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getVersion_flag() {
        return version_flag;
    }
    
    public void setVersion_flag(String version_flag) {
        this.version_flag = version_flag;
    }
    
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
    
    public String getEmailId() {
        return emailId;
    }
    
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    
    public ChemistInfo(String str_name, String id) {
        this.fSTNAME = str_name;
        this.cHEMISTID = id;
    }

    public ChemistInfo(String str_table_name, String str_chem_id,
                       String str_name, String str_error_id, String str_emp_id, String Address, String emailId) {

        this.tABLENAME = str_table_name;
        this.cHEMISTID = str_chem_id;
        this.fSTNAME = str_name;
        this.eRRID = str_error_id;
        this.mEMPLOYEEID = str_emp_id;
        this.aDDRESS=Address;
        this.emailId = emailId;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tABLENAME);
        dest.writeString(cHEMISTID);
        dest.writeString(fSTNAME);
        dest.writeString(eRRID);
        dest.writeString(mEMPLOYEEID);
        dest.writeString(aDDRESS);
        dest.writeString(emailId);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(version_flag);
    }

    private ChemistInfo(Parcel in) {
        this.tABLENAME = in.readString();
        this.cHEMISTID = in.readString();
        this.fSTNAME = in.readString();
        this.eRRID = in.readString();
        this.mEMPLOYEEID = in.readString();
        this.aDDRESS=in.readString();
        this.emailId=in.readString();
        this.latitude=in.readString();
        this.longitude=in.readString();
        this.version_flag = in.readString();
    }

    public static final Creator<ChemistInfo> CREATOR =
            new Creator<ChemistInfo>() {

        @Override
        public ChemistInfo createFromParcel(Parcel source) {
            return new ChemistInfo(source);
        }

        @Override
        public ChemistInfo[] newArray(int size) {
            return new ChemistInfo[size];
        }
    };

    public String getTABLENAME() {
        return tABLENAME;
    }

    public void setTABLENAME(String tABLENAME) {
        this.tABLENAME = tABLENAME;
    }

    public ChemistInfo withTABLENAME(String tABLENAME) {
        this.tABLENAME = tABLENAME;
        return this;
    }

    public String getCHEMISTID() {
        return cHEMISTID;
    }

    public void setCHEMISTID(String cHEMISTID) {
        this.cHEMISTID = cHEMISTID;
    }

    public ChemistInfo withCHEMISTID(String cHEMISTID) {
        this.cHEMISTID = cHEMISTID;
        return this;
    }

    public String getFSTNAME() {
        return fSTNAME;
    }

    public void setFSTNAME(String fSTNAME) {
        this.fSTNAME = fSTNAME;
    }

    public ChemistInfo withFSTNAME(String fSTNAME) {
        this.fSTNAME = fSTNAME;
        return this;
    }

    public String getERRID() {
        return eRRID;
    }

    public void setERRID(String eRRID) {
        this.eRRID = eRRID;
    }

    public ChemistInfo withERRID(String eRRID) {
        this.eRRID = eRRID;
        return this;
    }

    public String getMEMPLOYEEID() {
        return mEMPLOYEEID;
    }

    public void setMEMPLOYEEID(String mEMPLOYEEID) {
        this.mEMPLOYEEID = mEMPLOYEEID;
    }

    public String getaDDRESS() {
        return aDDRESS;
    }

    public void setaDDRESS(String aDDRESS) {
        this.aDDRESS = aDDRESS;
    }


    public ChemistInfo withMEMPLOYEEID(String mEMPLOYEEID) {
        this.mEMPLOYEEID = mEMPLOYEEID;
        return this;
    }


}



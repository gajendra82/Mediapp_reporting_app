package com.globalspace.miljonsales.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getcoveredpincode {

@SerializedName("COVERED_PIN")
@Expose
private String coveredPin;
@SerializedName("status")
@Expose
private String status;
@SerializedName("error")
@Expose
private String error;

public String getCoveredPin() {
return coveredPin;
}

public void setCoveredPin(String coveredPin) {
this.coveredPin = coveredPin;
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

}
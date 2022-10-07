package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.Field;

/**
 * Created by aravind on 10/7/18.
 */

public class Response {
	
	@SerializedName("Status")
	@Expose
	private String Status;
	
	@SerializedName("error")
	@Expose
	private String error;
	
	
	@SerializedName("Message")
	@Expose
	private String message;
	
	public String getStatus() {
		return Status;
	}
	
	public void setStatus(String status) {
		Status = status;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}

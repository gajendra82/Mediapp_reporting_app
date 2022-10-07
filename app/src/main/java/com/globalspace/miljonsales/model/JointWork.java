package com.globalspace.miljonsales.model;

import com.google.gson.annotations.SerializedName;

public class JointWork {
	
	@SerializedName("MEMPLOYEE_ID")
	private String jointWorkId;
	@SerializedName("manager_name")
	private String jointWorkName;
	
	public String getJointWorkId() {
		return jointWorkId;
	}
	
	public void setJointWorkId(String jointWorkId) {
		this.jointWorkId = jointWorkId;
	}
	
	public String getJointWorkName() {
		return jointWorkName;
	}
	
	public void setJointWorkName(String jointWorkName) {
		this.jointWorkName = jointWorkName;
	}
}

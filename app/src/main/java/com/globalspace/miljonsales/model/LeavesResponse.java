package com.globalspace.miljonsales.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LeavesResponse {
	@SerializedName("status")
	private String status;
	
	@SerializedName("error")
	private String error;
	
	@SerializedName("0")
	private ArrayList<LeavesData> data;
	
	
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
	
	public ArrayList<LeavesData> getData() {
		return data;
	}
	
	public void setData(ArrayList<LeavesData> data) {
		this.data = data;
	}
	
	public class LeavesData {
		@SerializedName("TABLE_NAME")
		private String tableName;
		
		@SerializedName("APPLIED_DATE")
		private String appliedDate;
		
		@SerializedName("LEAVE_REASON")
		private String leaveReason;
		
		@SerializedName("LEAVE_FROM_DATE")
		private String leaveFromDate;
		
		@SerializedName("LEAVE_TO_DATE")
		private String leaveTODate;
		
		public String getTableName() {
			return tableName;
		}
		
		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		
		public String getAppliedDate() {
			return appliedDate;
		}
		
		public void setAppliedDate(String appliedDate) {
			this.appliedDate = appliedDate;
		}
		
		public String getLeaveReason() {
			return leaveReason;
		}
		
		public void setLeaveReason(String leaveReason) {
			this.leaveReason = leaveReason;
		}
		
		public String getLeaveFromDate() {
			return leaveFromDate;
		}
		
		public void setLeaveFromDate(String leaveFromDate) {
			this.leaveFromDate = leaveFromDate;
		}
		
		public String getLeaveTODate() {
			return leaveTODate;
		}
		
		public void setLeaveTODate(String leaveTODate) {
			this.leaveTODate = leaveTODate;
		}
	}
	
}

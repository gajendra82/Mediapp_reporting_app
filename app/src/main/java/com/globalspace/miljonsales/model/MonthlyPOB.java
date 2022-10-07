package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonthlyPOB {

	@SerializedName("TABLE_NAME")
	@Expose
	private String tableName;
	
	@SerializedName("MEMPLOYEE_ID")
	private String empId;
	
	@SerializedName("CHEMIST_ID")
	private String chemistId;
	
	@SerializedName("STOCKIEST_ORG_ID")
	private String stockiestId;
	
	@SerializedName("STOCKIEST_NAME")
	private String stockiestName;

	@SerializedName("ORDER_ID")
	private String orderId;

	@SerializedName("CHEMIST_ORG_ID")
	private String chemistOrgId;
	@SerializedName("CHEMIST_NAME")
	private String chemistName;
	@SerializedName("TRANSACTION_DATE")
	private String transactionDate;
	@SerializedName("PURCHASE_ORDER_BOOKING")
	private String purchaseOrderBooking;
	@SerializedName("TRANSACTION_STATUS")
	private String transactionStatus;
	@SerializedName("STATE")
	private String state;
	@SerializedName("CITY")
	private String city;
	
	@SerializedName("POSTAL_CODE")
	private String postalCode;
	@SerializedName("LANDMARK")
	private String landmark;
	
	@SerializedName("ADDRESS")
	private String address;
	
	@SerializedName("LAT_LONG_ADDRESS")
	private String latLongAdd;
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getEmpId() {
		return empId;
	}
	
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
	public String getChemistId() {
		return chemistId;
	}
	
	public void setChemistId(String chemistId) {
		this.chemistId = chemistId;
	}
	
	public String getStockiestId() {
		return stockiestId;
	}
	
	public void setStockiestId(String stockiestId) {
		this.stockiestId = stockiestId;
	}
	
	public String getStockiestName() {
		return stockiestName;
	}
	
	public void setStockiestName(String stockiestName) {
		this.stockiestName = stockiestName;
	}
	
	public String getChemistOrgId() {
		return chemistOrgId;
	}
	
	public void setChemistOrgId(String chemistOrgId) {
		this.chemistOrgId = chemistOrgId;
	}
	
	public String getChemistName() {
		return chemistName;
	}
	
	public void setChemistName(String chemistName) {
		this.chemistName = chemistName;
	}
	
	public String getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public String getPurchaseOrderBooking() {
		return purchaseOrderBooking;
	}
	
	public void setPurchaseOrderBooking(String purchaseOrderBooking) {
		this.purchaseOrderBooking = purchaseOrderBooking;
	}
	
	public String getTransactionStatus() {
		return transactionStatus;
	}
	
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getLandmark() {
		return landmark;
	}
	
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLatLongAdd() {
		return latLongAdd;
	}
	
	public void setLatLongAdd(String latLongAdd) {
		this.latLongAdd = latLongAdd;
	}
}

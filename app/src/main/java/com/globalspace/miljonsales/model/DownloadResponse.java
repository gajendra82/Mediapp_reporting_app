package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DownloadResponse {
	@SerializedName("status")
	private String status;
	@SerializedName("error")
	private String error;
	@SerializedName("errorMessage")
	private String errorMessage;
	@SerializedName("0")
	private ArrayList<DownloadData> data;
	
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
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public ArrayList<DownloadData> getData() {
		return data;
	}
	
	public void setData(ArrayList<DownloadData> data) {
		this.data = data;
	}
	
	public class DownloadData{
		@SerializedName("TABLE_NAME")
		@Expose
		private String tABLENAME;
		@SerializedName("ORDER_ID")
		@Expose
		private String oRDERID;
		@SerializedName("CHEMIST_ID")
		@Expose
		private String cHEMISTID;
		@SerializedName("STOCKIEST_ORG_ID")
		@Expose
		private String sTOCKIESTORGID;
		@SerializedName("STOCKIEST_NAME")
		@Expose
		private String sTOCKIESTNAME;
		@SerializedName("CHEMIST_ORG_ID")
		@Expose
		private String cHEMISTORGID;
		@SerializedName("CHEMIST_NAME")
		@Expose
		private String cHEMISTNAME;
		@SerializedName("TRANSACTION_DATE")
		@Expose
		private String tRANSACTIONDATE;
		@SerializedName("REG_DATE")
		@Expose
		private String REG_DATE;
		@SerializedName("PURCHASE_ORDER_BOOKING")
		@Expose
		private String pURCHASEORDERBOOKING;
		@SerializedName("TRANSACTION_STATUS")
		@Expose
		private String tRANSACTIONSTATUS;
		@SerializedName("STATE")
		@Expose
		private String sTATE;
		@SerializedName("CITY")
		@Expose
		private Object cITY;
		@SerializedName("POSTAL_CODE")
		@Expose
		private String pOSTALCODE;
		@SerializedName("LANDMARK")
		@Expose
		private Object lANDMARK;
		@SerializedName("ADDRESS")
		@Expose
		private String aDDRESS;
		@SerializedName("LAT_LONG_ADDRESS")
		@Expose
		private Object lATLONGADDRESS;
		@SerializedName("CREATED")
		@Expose
		private String cREATED;

		public String gettABLENAME() {
			return tABLENAME;
		}

		public void settABLENAME(String tABLENAME) {
			this.tABLENAME = tABLENAME;
		}

		public String getREG_DATE() {
			return REG_DATE;
		}

		public void setREG_DATE(String REG_DATE) {
			this.REG_DATE = REG_DATE;
		}

		public String getoRDERID() {
			return oRDERID;
		}

		public void setoRDERID(String oRDERID) {
			this.oRDERID = oRDERID;
		}

		public String getcHEMISTID() {
			return cHEMISTID;
		}

		public void setcHEMISTID(String cHEMISTID) {
			this.cHEMISTID = cHEMISTID;
		}

		public String getsTOCKIESTORGID() {
			return sTOCKIESTORGID;
		}

		public void setsTOCKIESTORGID(String sTOCKIESTORGID) {
			this.sTOCKIESTORGID = sTOCKIESTORGID;
		}

		public String getsTOCKIESTNAME() {
			return sTOCKIESTNAME;
		}

		public void setsTOCKIESTNAME(String sTOCKIESTNAME) {
			this.sTOCKIESTNAME = sTOCKIESTNAME;
		}

		public String getcHEMISTORGID() {
			return cHEMISTORGID;
		}

		public void setcHEMISTORGID(String cHEMISTORGID) {
			this.cHEMISTORGID = cHEMISTORGID;
		}

		public String getcHEMISTNAME() {
			return cHEMISTNAME;
		}

		public void setcHEMISTNAME(String cHEMISTNAME) {
			this.cHEMISTNAME = cHEMISTNAME;
		}

		public String gettRANSACTIONDATE() {
			return tRANSACTIONDATE;
		}

		public void settRANSACTIONDATE(String tRANSACTIONDATE) {
			this.tRANSACTIONDATE = tRANSACTIONDATE;
		}

		public String getpURCHASEORDERBOOKING() {
			return pURCHASEORDERBOOKING;
		}

		public void setpURCHASEORDERBOOKING(String pURCHASEORDERBOOKING) {
			this.pURCHASEORDERBOOKING = pURCHASEORDERBOOKING;
		}

		public String gettRANSACTIONSTATUS() {
			return tRANSACTIONSTATUS;
		}

		public void settRANSACTIONSTATUS(String tRANSACTIONSTATUS) {
			this.tRANSACTIONSTATUS = tRANSACTIONSTATUS;
		}

		public String getsTATE() {
			return sTATE;
		}

		public void setsTATE(String sTATE) {
			this.sTATE = sTATE;
		}

		public Object getcITY() {
			return cITY;
		}

		public void setcITY(Object cITY) {
			this.cITY = cITY;
		}

		public String getpOSTALCODE() {
			return pOSTALCODE;
		}

		public void setpOSTALCODE(String pOSTALCODE) {
			this.pOSTALCODE = pOSTALCODE;
		}

		public Object getlANDMARK() {
			return lANDMARK;
		}

		public void setlANDMARK(Object lANDMARK) {
			this.lANDMARK = lANDMARK;
		}

		public String getaDDRESS() {
			return aDDRESS;
		}

		public void setaDDRESS(String aDDRESS) {
			this.aDDRESS = aDDRESS;
		}

		public Object getlATLONGADDRESS() {
			return lATLONGADDRESS;
		}

		public void setlATLONGADDRESS(Object lATLONGADDRESS) {
			this.lATLONGADDRESS = lATLONGADDRESS;
		}

		public String getcREATED() {
			return cREATED;
		}

		public void setcREATED(String cREATED) {
			this.cREATED = cREATED;
		}
	}
}

package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class POBResponse {
	
	@SerializedName("status")
	private String status;
	
	@SerializedName("error")
	private String error;
	
	@SerializedName("0")
	private ArrayList<MonthlyPOB> data;

	@SerializedName("MONTHLY_DELIVERED_POB")
	@Expose
	private List<MONTHLY> mONTHLYDELIVEREDPOB = null;

	@SerializedName("MONTHLY_PENDING_POB")
	@Expose
	private List<MONTHLY> mONTHLYPENDINGPOB = null;

	@SerializedName("MONTHLY_POSTPONE_POB")
	@Expose
	private List<MONTHLY> mONTHLYPOSTPONEPOB = null;

    @SerializedName("MONTHLY_APPROVED_POB")
	@Expose
	private List<MONTHLY> mONTHLYAPPROVED = null;

    @SerializedName("MONTHLY_CANCEL_POB")
	@Expose
	private List<MONTHLY> mONTHLYCANCEL = null;


	public List<MONTHLY> getmONTHLYCANCEL() {
		return mONTHLYCANCEL;
	}

	public void setmONTHLYCANCEL(List<MONTHLY> mONTHLYCANCEL) {
		this.mONTHLYCANCEL = mONTHLYCANCEL;
	}

    public List<MONTHLY> getmONTHLYPENDINGPOB() {
        return mONTHLYPENDINGPOB;
    }

    public void setmONTHLYPENDINGPOB(List<MONTHLY> mONTHLYPENDINGPOB) {
        this.mONTHLYPENDINGPOB = mONTHLYPENDINGPOB;
    }

    public List<MONTHLY> getmONTHLYPOSTPONEPOB() {
        return mONTHLYPOSTPONEPOB;
    }

    public void setmONTHLYPOSTPONEPOB(List<MONTHLY> mONTHLYPOSTPONEPOB) {
        this.mONTHLYPOSTPONEPOB = mONTHLYPOSTPONEPOB;
    }

    public List<MONTHLY> getmONTHLYAPPROVED() {
		return mONTHLYAPPROVED;
	}

	public void setmONTHLYAPPROVED(List<MONTHLY> mONTHLYAPPROVED) {
		this.mONTHLYAPPROVED = mONTHLYAPPROVED;
	}

	public List<MONTHLY> getMONTHLYDELIVEREDPOB() {
		return mONTHLYDELIVEREDPOB;
	}

	public void setMONTHLYDELIVEREDPOB(List<MONTHLY> mONTHLYDELIVEREDPOB) {
		this.mONTHLYDELIVEREDPOB = mONTHLYDELIVEREDPOB;
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

	public class MONTHLY {

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
		private String lATLONGADDRESS;
		@SerializedName("CREATED")
		@Expose
		private String cREATED;

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

		public String getCHEMISTID() {
			return cHEMISTID;
		}

		public void setCHEMISTID(String cHEMISTID) {
			this.cHEMISTID = cHEMISTID;
		}

		public String getSTOCKIESTORGID() {
			return sTOCKIESTORGID;
		}

		public void setSTOCKIESTORGID(String sTOCKIESTORGID) {
			this.sTOCKIESTORGID = sTOCKIESTORGID;
		}

		public String getSTOCKIESTNAME() {
			return sTOCKIESTNAME;
		}

		public void setSTOCKIESTNAME(String sTOCKIESTNAME) {
			this.sTOCKIESTNAME = sTOCKIESTNAME;
		}

		public String getCHEMISTORGID() {
			return cHEMISTORGID;
		}

		public void setCHEMISTORGID(String cHEMISTORGID) {
			this.cHEMISTORGID = cHEMISTORGID;
		}

		public String getCHEMISTNAME() {
			return cHEMISTNAME;
		}

		public void setCHEMISTNAME(String cHEMISTNAME) {
			this.cHEMISTNAME = cHEMISTNAME;
		}

		public String getTRANSACTIONDATE() {
			return tRANSACTIONDATE;
		}

		public void setTRANSACTIONDATE(String tRANSACTIONDATE) {
			this.tRANSACTIONDATE = tRANSACTIONDATE;
		}

		public String getPURCHASEORDERBOOKING() {
			return pURCHASEORDERBOOKING;
		}

		public void setPURCHASEORDERBOOKING(String pURCHASEORDERBOOKING) {
			this.pURCHASEORDERBOOKING = pURCHASEORDERBOOKING;
		}

		public String getTRANSACTIONSTATUS() {
			return tRANSACTIONSTATUS;
		}

		public void setTRANSACTIONSTATUS(String tRANSACTIONSTATUS) {
			this.tRANSACTIONSTATUS = tRANSACTIONSTATUS;
		}

		public String getSTATE() {
			return sTATE;
		}

		public void setSTATE(String sTATE) {
			this.sTATE = sTATE;
		}

		public Object getCITY() {
			return cITY;
		}

		public void setCITY(Object cITY) {
			this.cITY = cITY;
		}

		public String getPOSTALCODE() {
			return pOSTALCODE;
		}

		public void setPOSTALCODE(String pOSTALCODE) {
			this.pOSTALCODE = pOSTALCODE;
		}

		public Object getLANDMARK() {
			return lANDMARK;
		}

		public void setLANDMARK(Object lANDMARK) {
			this.lANDMARK = lANDMARK;
		}

		public String getADDRESS() {
			return aDDRESS;
		}

		public void setADDRESS(String aDDRESS) {
			this.aDDRESS = aDDRESS;
		}

		public String getLATLONGADDRESS() {
			return lATLONGADDRESS;
		}

		public void setLATLONGADDRESS(String lATLONGADDRESS) {
			this.lATLONGADDRESS = lATLONGADDRESS;
		}

		public String getCREATED() {
			return cREATED;
		}

		public void setCREATED(String cREATED) {
			this.cREATED = cREATED;
		}

	}


}

package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aravind on 10/7/18.
 */

public class Login_Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    @SerializedName("Message")
    @Expose
    private String Message;

    @SerializedName("message")
    @Expose
    private String msg;

    @SerializedName("out")
    private out out;

    @SerializedName("EMPLOYEE_NAME")
    @Expose
    private String Name;
    
    @SerializedName("REPORTING_PERSON_NAME")
    @Expose
    private String ReportingPersonName;
    
    @SerializedName("DESIGNATION_NAME")
    @Expose
    private String designation;

    @SerializedName("SUB_ORDINATE_FLAG")
    @Expose
    private String sub_ordinate_flag;


    @SerializedName("emp_status")
    @Expose
    private String empStatus;

    @SerializedName("employee_id")
    @Expose
    private Object employeeId;
    @SerializedName("flag")
    @Expose
    private String flag;
    
    @SerializedName("EMP_STATUS")
    private String leaveStatus;
    
    @SerializedName("EMP_MESSAGE")
    private String leaveMessage;
    
    public String getLeaveMessage() {
        return leaveMessage;
    }
    
    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }
    
    @SerializedName("placeName")
    @Expose
    private String placeName;

    @SerializedName("MEMPLOYEE_ID")
    @Expose
    private String mEMPLOYEEID;
    @SerializedName("CHMIEST_INFO")
    @Expose
    private List<ChemistInfo> cHMIESTINFO = null;
    @SerializedName("STOCKIEST_INFO")
    @Expose
    private ArrayList<STOCKIESTINFO> sTOCKIESTINFO = null;
    @SerializedName("Stockist_List")
    @Expose
    private ArrayList<STOCKIESTINFO> stockistData = null;
    
    @SerializedName("JOINT_WORK")
    private List<JointWork> jointWorks;
    
    public List<JointWork> getJointWorks() {
        return jointWorks;
    }
    
    public void setJointWorks(List<JointWork> jointWorks) {
        this.jointWorks = jointWorks;
    }
    
    @SerializedName("notInterestedUserList")
    @Expose
    private List<NotInterestedUserList> notInterestedUserList = null;

    public Login_Response.out getOut() {
        return out;
    }

    public void setOut(Login_Response.out out) {
        this.out = out;
    }

    public String getmEMPLOYEEID() {
        return mEMPLOYEEID;
    }

    public void setmEMPLOYEEID(String mEMPLOYEEID) {
        this.mEMPLOYEEID = mEMPLOYEEID;
    }

    public List<ChemistInfo> getcHMIESTINFO() {
        return cHMIESTINFO;
    }

    public void setcHMIESTINFO(List<ChemistInfo> cHMIESTINFO) {
        this.cHMIESTINFO = cHMIESTINFO;
    }

    public ArrayList<STOCKIESTINFO> getsTOCKIESTINFO() {
        return sTOCKIESTINFO;
    }

    public void setsTOCKIESTINFO(ArrayList<STOCKIESTINFO> sTOCKIESTINFO) {
        this.sTOCKIESTINFO = sTOCKIESTINFO;
    }

    public ArrayList<STOCKIESTINFO> getStockistData() {
        return stockistData;
    }

    public void setStockistData(ArrayList<STOCKIESTINFO> stockistData) {
        this.stockistData = stockistData;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    
    
    public String getLeaveStatus() {
        return leaveStatus;
    }
    
    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }
    
    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Login_Response withStatus(String status) {
        this.status = status;
        return this;
    }
    
    public String getReportingPersonName() {
        return ReportingPersonName;
    }
    
    public void setReportingPersonName(String reportingPersonName) {
        ReportingPersonName = reportingPersonName;
    }
    
    public String getDesignation() {
        return designation;
    }
    
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Login_Response withError(String error) {
        this.error = error;
        return this;
    }

    public String getSub_ordinate_flag() {
        return sub_ordinate_flag;
    }

    public void setSub_ordinate_flag(String sub_ordinate_flag) {
        this.sub_ordinate_flag = sub_ordinate_flag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Login_Response withErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public String getMEMPLOYEEID() {
        return mEMPLOYEEID;
    }

    public void setMEMPLOYEEID(String mEMPLOYEEID) {
        this.mEMPLOYEEID = mEMPLOYEEID;
    }

    public Login_Response withMEMPLOYEEID(String mEMPLOYEEID) {
        this.mEMPLOYEEID = mEMPLOYEEID;
        return this;
    }

    public List<ChemistInfo> getCHMIESTINFO() {
        return cHMIESTINFO;
    }

    public void setCHMIESTINFO(List<ChemistInfo> cHMIESTINFO) {
        this.cHMIESTINFO = cHMIESTINFO;
    }

    public Login_Response withCHMIESTINFO(List<ChemistInfo> cHMIESTINFO) {
        this.cHMIESTINFO = cHMIESTINFO;
        return this;
    }

    public ArrayList<STOCKIESTINFO> getSTOCKIESTINFO() {
        return sTOCKIESTINFO;
    }

    public void setSTOCKIESTINFO(ArrayList<STOCKIESTINFO> sTOCKIESTINFO) {
        this.sTOCKIESTINFO = sTOCKIESTINFO;
    }

    public Login_Response withSTOCKIESTINFO(ArrayList<STOCKIESTINFO> sTOCKIESTINFO) {
        this.sTOCKIESTINFO = sTOCKIESTINFO;
        return this;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Login_Response withPlaceName(String placeName) {
        this.placeName = placeName;
        return this;
    }

    public String getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    }

    public Login_Response withEmpStatus(String empStatus) {
        this.empStatus = empStatus;
        return this;
    }


    public Object getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Object employeeId) {
        this.employeeId = employeeId;
    }

    public Login_Response withEmployeeId(Object employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Login_Response withFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public List<NotInterestedUserList> getNotInterestedUserList() {
        return notInterestedUserList;
    }

    public void setNotInterestedUserList(List<NotInterestedUserList> notInterestedUserList) {
        this.notInterestedUserList = notInterestedUserList;
    }


    private class out {
        @SerializedName("@A")
        private String message;


        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }


}

package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DashboardData implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;


    @SerializedName("EMPLOYEE_TOTAL_REPORTING")
    @Expose
    private ArrayList<TotalReporting> totalReportings;
    @SerializedName("EMPLOYEE_TOTAL_MONTHLY_REPORTING")
    @Expose
    private ArrayList<TotalReporting> monthlyReporting;


    @SerializedName("REPORTING_DAILY_TARGET_CALLS")
    @Expose
    private ArrayList<DailyTargetCall> dailyTargetCalls;

    @SerializedName("REPORTING_DETAILS")
    @Expose
    private ArrayList<ReportingDetails> reportingDetails;
    @SerializedName("TOTAL_MONTHLY_REPORTING")
    @Expose
    private ArrayList<ReportingDetails> monthlyReportingDetails;

    @SerializedName("REPORTING_CALL_AVG")
    @Expose
    private ArrayList<ReportingCallAvg> reportingCallAvg;

    @SerializedName("TODAYS_DOWNLOAD")
    @Expose
    private ArrayList<TodayDownload> todayDownloads;

    @SerializedName("MONTHLY_DOWNLOAD")
    @Expose
    private ArrayList<MonthlyDownload> monthlyDownload;

    @SerializedName("TODAYS_POB")
    @Expose
    private ArrayList<TodayPOB> todayPOBS;

    @SerializedName("MONTHLY_POB")
    @Expose
    private ArrayList<MonthlyPOB> monthlyPOBS;

    @SerializedName("MONTHLY_LEAVES")
    @Expose
    private ArrayList<MonthlyLeaves> monthlyLeaves;

    @SerializedName("MONTHLY_DELIVERED_POB")
    @Expose
    private ArrayList<MONTHLYDELIVEREDPOB> mONTHLYDELIVEREDPOB = null;
    @SerializedName("MONTHLY_APPROVED_POB")
    @Expose
    private ArrayList<MONTHLYAPPROVEDPOB> mONTHLYAPPROVEDPOB = null;
    @SerializedName("MONTHLY_PENDING_POB")
    @Expose
    private ArrayList<MONTHLYPENDINGPOB> mONTHLYPENDINGPOB = null;
    @SerializedName("MONTHLY_CANCEL_POB")
    @Expose
    private ArrayList<MONTHLYCANCELPOB> mONTHLYCANCELPOB = null;
    @SerializedName("MONTHLY_POSTPONE_POB")
    @Expose
    private ArrayList<MONTHLYPOSTPONEPOB> mONTHLYPOSTPONEPOB = null;

    public ArrayList<MONTHLYDELIVEREDPOB> getmONTHLYDELIVEREDPOB() {
        return mONTHLYDELIVEREDPOB;
    }

    public void setmONTHLYDELIVEREDPOB(ArrayList<MONTHLYDELIVEREDPOB> mONTHLYDELIVEREDPOB) {
        this.mONTHLYDELIVEREDPOB = mONTHLYDELIVEREDPOB;
    }

    public List<MONTHLYAPPROVEDPOB> getmONTHLYAPPROVEDPOB() {
        return mONTHLYAPPROVEDPOB;
    }

    public void setmONTHLYAPPROVEDPOB(ArrayList<MONTHLYAPPROVEDPOB> mONTHLYAPPROVEDPOB) {
        this.mONTHLYAPPROVEDPOB = mONTHLYAPPROVEDPOB;
    }

    public List<MONTHLYPENDINGPOB> getmONTHLYPENDINGPOB() {
        return mONTHLYPENDINGPOB;
    }

    public void setmONTHLYPENDINGPOB(ArrayList<MONTHLYPENDINGPOB> mONTHLYPENDINGPOB) {
        this.mONTHLYPENDINGPOB = mONTHLYPENDINGPOB;
    }

    public List<MONTHLYCANCELPOB> getmONTHLYCANCELPOB() {
        return mONTHLYCANCELPOB;
    }

    public void setmONTHLYCANCELPOB(ArrayList<MONTHLYCANCELPOB> mONTHLYCANCELPOB) {
        this.mONTHLYCANCELPOB = mONTHLYCANCELPOB;
    }

    public List<MONTHLYPOSTPONEPOB> getmONTHLYPOSTPONEPOB() {
        return mONTHLYPOSTPONEPOB;
    }

    public void setmONTHLYPOSTPONEPOB(ArrayList<MONTHLYPOSTPONEPOB> mONTHLYPOSTPONEPOB) {
        this.mONTHLYPOSTPONEPOB = mONTHLYPOSTPONEPOB;
    }

    public class DailyTargetCall implements Serializable{

        @SerializedName("TABLE_NAME")
        @Expose
        private String tableName;

        @SerializedName("DAILY_TARGET_CALLS")
        @Expose
        private String target_calls;


        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getTarget_calls() {
            return target_calls;
        }

        public void setTarget_calls(String target_calls) {
            this.target_calls = target_calls;
        }
    }

    public class TotalReporting implements Serializable{

        @SerializedName("TABLE_NAME")
        @Expose
        private String tableName;

        @SerializedName("TOTAL_REPORTING")
        @Expose
        private String totalReporting;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getTotalReporting() {
            return totalReporting;
        }

        public void setTotalReporting(String totalReporting) {
            this.totalReporting = totalReporting;
        }
    }

    public class ReportingDetails implements Serializable{

        @SerializedName("TABLE_NAME")
        @Expose
        private String tableName;

        @SerializedName("SHOP_NAME")
        @Expose
        private String shopName;

        @SerializedName("ADDRESS")
        @Expose
        private String address;

        @SerializedName("MOBILE")
        @Expose
        private String mobile;
        @SerializedName("REPORTING_DATE")
        @Expose
        private String reportingDate;

        @SerializedName("CHEMIST_CATEGORY")
        @Expose
        private String category;

        @SerializedName("ACTIVITY")
        private String ReportingType;

        @SerializedName("JOINT_WORK")
        private String jointWork;

        @SerializedName("REPORTING_ADDRESS")
        @Expose
        private String rEPORTINGADDRESS;

        public String getrEPORTINGADDRESS() {
            return rEPORTINGADDRESS;
        }

        public void setrEPORTINGADDRESS(String rEPORTINGADDRESS) {
            this.rEPORTINGADDRESS = rEPORTINGADDRESS;
        }

        public String getJointWork() {
            return jointWork;
        }

        public void setJointWork(String jointWork) {
            this.jointWork = jointWork;
        }

        public String getReportingType() {
            return ReportingType;
        }

        public void setReportingType(String reportingType) {
            ReportingType = reportingType;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getReportingDate() {
            return reportingDate;
        }

        public void setReportingDate(String reportingDate) {
            this.reportingDate = reportingDate;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

    public class ReportingCallAvg implements Serializable{

        @SerializedName("TABLE_NAME")
        @Expose
        private String tableName;

        @SerializedName("COUNT_OF_REPORTING")
        @Expose
        private String reportingCount;

        @SerializedName("DATES_COUNTS")
        @Expose
        private String datesCount;

        @SerializedName("CALL_AVG")
        @Expose
        private String callAvg;


        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getReportingCount() {
            return reportingCount;
        }

        public void setReportingCount(String reportingCount) {
            this.reportingCount = reportingCount;
        }

        public String getDatesCount() {
            return datesCount;
        }

        public void setDatesCount(String datesCount) {
            this.datesCount = datesCount;
        }

        public String getCallAvg() {
            return callAvg;
        }

        public void setCallAvg(String callAvg) {
            this.callAvg = callAvg;
        }
    }

    public class TodayDownload implements Serializable{

        @SerializedName("TABLE_NAME")
        @Expose
        private String tableName;

        @SerializedName("TODAYS_DOWNLOAD_COUNT")
        @Expose
        private String todayDownloadCount;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getTodayDownloadCount() {
            return todayDownloadCount;
        }

        public void setTodayDownloadCount(String todayDownloadCount) {
            this.todayDownloadCount = todayDownloadCount;
        }
    }

    public class MonthlyDownload implements Serializable{

        @SerializedName("TABLE_NAME")
        @Expose
        private String tableName;

        @SerializedName("MONTHLY_DOWNLOAD_COUNT")
        @Expose
        private String monthlyDownloadCount;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getMonthlyDownloadCount() {
            return monthlyDownloadCount;
        }

        public void setMonthlyDownloadCount(String monthlyDownloadCount) {
            this.monthlyDownloadCount = monthlyDownloadCount;
        }
    }

    public class TodayPOB implements Serializable{

        @SerializedName("TABLE_NAME")
        @Expose
        private String tableName;

        @SerializedName("TODAYS_POB")
        @Expose
        private String todayPOB;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getTodayPOB() {
            return todayPOB;
        }

        public void setTodayPOB(String todayPOB) {
            this.todayPOB = todayPOB;
        }
    }

    public class MonthlyPOB implements Serializable{

        @SerializedName("TABLE_NAME")
        @Expose
        private String tableName;

        @SerializedName("MONTHLY_POB")
        @Expose
        private String monthlyPOB;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getMonthlyPOB() {
            return monthlyPOB;
        }

        public void setMonthlyPOB(String monthlyPOB) {
            this.monthlyPOB = monthlyPOB;
        }
    }

    public class MonthlyLeaves implements Serializable{

        @SerializedName("TABLE_NAME")
        @Expose
        private String tableName;

        @SerializedName("MONTHLY_LEAVES_COUNT")
        @Expose
        private String monthlyLeaves;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getMonthlyLeaves() {
            return monthlyLeaves;
        }

        public void setMonthlyLeaves(String monthlyLeaves) {
            this.monthlyLeaves = monthlyLeaves;
        }
    }


    public ArrayList<TotalReporting> getMonthlyReporting() {
        return monthlyReporting;
    }

    public void setMonthlyReporting(ArrayList<TotalReporting> monthlyReporting) {
        this.monthlyReporting = monthlyReporting;
    }

    public ArrayList<ReportingDetails> getMonthlyReportingDetails() {
        return monthlyReportingDetails;
    }

    public void setMonthlyReportingDetails(ArrayList<ReportingDetails> monthlyReportingDetails) {
        this.monthlyReportingDetails = monthlyReportingDetails;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ArrayList<TotalReporting> getTotalReportings() {
        return totalReportings;
    }

    public void setTotalReportings(ArrayList<TotalReporting> totalReportings) {
        this.totalReportings = totalReportings;
    }

    public ArrayList<DailyTargetCall> getDailyTargetCalls() {
        return dailyTargetCalls;
    }

    public void setDailyTargetCalls(ArrayList<DailyTargetCall> dailyTargetCalls) {
        this.dailyTargetCalls = dailyTargetCalls;
    }

    public ArrayList<ReportingDetails> getReportingDetails() {
        return reportingDetails;
    }

    public void setReportingDetails(ArrayList<ReportingDetails> reportingDetails) {
        this.reportingDetails = reportingDetails;
    }

    public ArrayList<ReportingCallAvg> getReportingCallAvg() {
        return reportingCallAvg;
    }

    public void setReportingCallAvg(ArrayList<ReportingCallAvg> reportingCallAvg) {
        this.reportingCallAvg = reportingCallAvg;
    }

    public ArrayList<TodayDownload> getTodayDownloads() {
        return todayDownloads;
    }

    public void setTodayDownloads(ArrayList<TodayDownload> todayDownloads) {
        this.todayDownloads = todayDownloads;
    }

    public ArrayList<MonthlyDownload> getMonthlyDownload() {
        return monthlyDownload;
    }

    public void setMonthlyDownload(ArrayList<MonthlyDownload> monthlyDownload) {
        this.monthlyDownload = monthlyDownload;
    }

    public ArrayList<TodayPOB> getTodayPOBS() {
        return todayPOBS;
    }

    public void setTodayPOBS(ArrayList<TodayPOB> todayPOBS) {
        this.todayPOBS = todayPOBS;
    }

    public ArrayList<MonthlyPOB> getMonthlyPOBS() {
        return monthlyPOBS;
    }

    public void setMonthlyPOBS(ArrayList<MonthlyPOB> monthlyPOBS) {
        this.monthlyPOBS = monthlyPOBS;
    }

    public ArrayList<MonthlyLeaves> getMonthlyLeaves() {
        return monthlyLeaves;
    }

    public void setMonthlyLeaves(ArrayList<MonthlyLeaves> monthlyLeaves) {
        this.monthlyLeaves = monthlyLeaves;
    }
    public class MONTHLYAPPROVEDPOB {

        @SerializedName("TABLE_NAME")
        @Expose
        private String tABLENAME;
        @SerializedName("TRANSACTION_STATUS")
        @Expose
        private String tRANSACTIONSTATUS;
        @SerializedName("MONTHLY_POB")
        @Expose
        private String mONTHLYPOB;

        public String getTABLENAME() {
            return tABLENAME;
        }

        public void setTABLENAME(String tABLENAME) {
            this.tABLENAME = tABLENAME;
        }

        public String getTRANSACTIONSTATUS() {
            return tRANSACTIONSTATUS;
        }

        public void setTRANSACTIONSTATUS(String tRANSACTIONSTATUS) {
            this.tRANSACTIONSTATUS = tRANSACTIONSTATUS;
        }

        public String getMONTHLYPOB() {
            return mONTHLYPOB;
        }

        public void setMONTHLYPOB(String mONTHLYPOB) {
            this.mONTHLYPOB = mONTHLYPOB;
        }

    }

    public class MONTHLYCANCELPOB {

        @SerializedName("TABLE_NAME")
        @Expose
        private String tABLENAME;
        @SerializedName("TRANSACTION_STATUS")
        @Expose
        private String tRANSACTIONSTATUS;
        @SerializedName("MONTHLY_POB")
        @Expose
        private String mONTHLYPOB;

        public String getTABLENAME() {
            return tABLENAME;
        }

        public void setTABLENAME(String tABLENAME) {
            this.tABLENAME = tABLENAME;
        }

        public String getTRANSACTIONSTATUS() {
            return tRANSACTIONSTATUS;
        }

        public void setTRANSACTIONSTATUS(String tRANSACTIONSTATUS) {
            this.tRANSACTIONSTATUS = tRANSACTIONSTATUS;
        }

        public String getMONTHLYPOB() {
            return mONTHLYPOB;
        }

        public void setMONTHLYPOB(String mONTHLYPOB) {
            this.mONTHLYPOB = mONTHLYPOB;
        }

    }

    public class MONTHLYDELIVEREDPOB {

        @SerializedName("TABLE_NAME")
        @Expose
        private String tABLENAME;
        @SerializedName("TRANSACTION_STATUS")
        @Expose
        private String tRANSACTIONSTATUS;
        @SerializedName("MONTHLY_POB")
        @Expose
        private String mONTHLYPOB;

        public String getTABLENAME() {
            return tABLENAME;
        }

        public void setTABLENAME(String tABLENAME) {
            this.tABLENAME = tABLENAME;
        }

        public String getTRANSACTIONSTATUS() {
            return tRANSACTIONSTATUS;
        }

        public void setTRANSACTIONSTATUS(String tRANSACTIONSTATUS) {
            this.tRANSACTIONSTATUS = tRANSACTIONSTATUS;
        }

        public String getMONTHLYPOB() {
            return mONTHLYPOB;
        }

        public void setMONTHLYPOB(String mONTHLYPOB) {
            this.mONTHLYPOB = mONTHLYPOB;
        }

    }

    public class MONTHLYPENDINGPOB {

        @SerializedName("TABLE_NAME")
        @Expose
        private String tABLENAME;
        @SerializedName("TRANSACTION_STATUS")
        @Expose
        private String tRANSACTIONSTATUS;
        @SerializedName("MONTHLY_POB")
        @Expose
        private String mONTHLYPOB;

        public String getTABLENAME() {
            return tABLENAME;
        }

        public void setTABLENAME(String tABLENAME) {
            this.tABLENAME = tABLENAME;
        }

        public String getTRANSACTIONSTATUS() {
            return tRANSACTIONSTATUS;
        }

        public void setTRANSACTIONSTATUS(String tRANSACTIONSTATUS) {
            this.tRANSACTIONSTATUS = tRANSACTIONSTATUS;
        }

        public String getMONTHLYPOB() {
            return mONTHLYPOB;
        }

        public void setMONTHLYPOB(String mONTHLYPOB) {
            this.mONTHLYPOB = mONTHLYPOB;
        }

    }

    public class MONTHLYPOSTPONEPOB {

        @SerializedName("TABLE_NAME")
        @Expose
        private String tABLENAME;
        @SerializedName("TRANSACTION_STATUS")
        @Expose
        private String tRANSACTIONSTATUS;
        @SerializedName("MONTHLY_POB")
        @Expose
        private String mONTHLYPOB;

        public String getTABLENAME() {
            return tABLENAME;
        }

        public void setTABLENAME(String tABLENAME) {
            this.tABLENAME = tABLENAME;
        }

        public String getTRANSACTIONSTATUS() {
            return tRANSACTIONSTATUS;
        }

        public void setTRANSACTIONSTATUS(String tRANSACTIONSTATUS) {
            this.tRANSACTIONSTATUS = tRANSACTIONSTATUS;
        }

        public String getMONTHLYPOB() {
            return mONTHLYPOB;
        }

        public void setMONTHLYPOB(String mONTHLYPOB) {
            this.mONTHLYPOB = mONTHLYPOB;
        }

    }

}
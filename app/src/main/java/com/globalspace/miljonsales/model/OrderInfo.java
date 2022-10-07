package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aravind on 11/8/18.
 */

public class OrderInfo {

    @SerializedName("pending_order_details")
    List<ProductList> lstorderinfo = null;
    @SerializedName("MESSAGE")
    String str_Order_Accept_Reject_msg;
    public class ProductList implements Serializable {
        @SerializedName("table_name")
        @Expose
        private String tableName;
        @SerializedName("CHEMIST_ID")
        @Expose
        private String CHEMISTID;
        @SerializedName("ORDER_ID")
        @Expose
        private String ORDERID;
        @SerializedName("DELIVERY_PERSON_ID")
        @Expose
        private Object DELIVERYPERSONID;
        @SerializedName("DELIVERY_PERSON_NAME")
        @Expose
        private Object DELIVERYPERSONNAME;
        @SerializedName("ORG_ID")
        @Expose
        private String ORGID;
        @SerializedName("NAME")
        @Expose
        private String NAME;
        @SerializedName("SHOP_NAME")
        @Expose
        private String SHOPNAME;
        @SerializedName("SPS_ID")
        @Expose
        private Object SPSID;
        @SerializedName("ORDL")
        @Expose
        private String ORDL;
        @SerializedName("SKU_ID")
        @Expose
        private String SKUID;
        @SerializedName("ORDER_QUANTITY")
        @Expose
        private String ORDERQUANTITY;
        @SerializedName("APPROVED_QUANTITY")
        @Expose
        private String APPROVEDQUANTITY;
        @SerializedName("UNIT_PRICE")
        @Expose
        private String UNITPRICE;
        @SerializedName("PRICE")
        @Expose
        private String PRICE;
        @SerializedName("ADJUSTMENT")
        @Expose
        private String ADJUSTMENT;
        @SerializedName("ADJUSTMENT_DESC")
        @Expose
        private String ADJUSTMENTDESC;
        @SerializedName("SUB_TOTAL")
        @Expose
        private String SUBTOTAL;
        @SerializedName("TOTAL_PRICE")
        @Expose
        private String TOTALPRICE;
        @SerializedName("PRODUCT_NAME")
        @Expose
        private String PRODUCTNAME;
        @SerializedName("DELIVERY_DATE")
        @Expose
        private Object DELIVERYDATE;
        @SerializedName("SALES_INVOICE_NO")
        @Expose
        private Object SALESINVOICENO;
        @SerializedName("DISCOUNT")
        @Expose
        private String DISCOUNT;
        @SerializedName("TAX_ID")
        @Expose
        private Object TAXID;
        @SerializedName("SCHEME_ID")
        @Expose
        private String SCHEMEID;
        @SerializedName("TAX_NAME")
        @Expose
        private Object TAXNAME;
        @SerializedName("TAX_VALUE")
        @Expose
        private Object TAXVALUE;
        @SerializedName("SCHEME")
        @Expose
        private String SCHEME;
        @SerializedName("SCHEME_DESCRIPTION")
        @Expose
        private String SCHEMEDESCRIPTION;
        @SerializedName("SKU_ID_WITH_QUANT")
        @Expose
        private Object SKUIDWITHQUANT;
        @SerializedName("UNITS_AVAILABLE")
        @Expose
        private Object UNITSAVAILABLE;
        @SerializedName("UNPAID_AMOUNT")
        @Expose
        private Object UNPAIDAMOUNT;
        @SerializedName("CREDIT_LIMIT")
        @Expose
        private Object CREDITLIMIT;
        @SerializedName("OFFER_AMOUNT")
        @Expose
        private String OFFERAMOUNT;
        @SerializedName("SESSION_H_ID")
        @Expose
        private String SESSIONHID;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getCHEMISTID() {
            return CHEMISTID;
        }

        public void setCHEMISTID(String CHEMISTID) {
            this.CHEMISTID = CHEMISTID;
        }

        public String getORDERID() {
            return ORDERID;
        }

        public void setORDERID(String ORDERID) {
            this.ORDERID = ORDERID;
        }

        public Object getDELIVERYPERSONID() {
            return DELIVERYPERSONID;
        }

        public void setDELIVERYPERSONID(Object DELIVERYPERSONID) {
            this.DELIVERYPERSONID = DELIVERYPERSONID;
        }

        public Object getDELIVERYPERSONNAME() {
            return DELIVERYPERSONNAME;
        }

        public void setDELIVERYPERSONNAME(Object DELIVERYPERSONNAME) {
            this.DELIVERYPERSONNAME = DELIVERYPERSONNAME;
        }

        public String getORGID() {
            return ORGID;
        }

        public void setORGID(String ORGID) {
            this.ORGID = ORGID;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getSHOPNAME() {
            return SHOPNAME;
        }

        public void setSHOPNAME(String SHOPNAME) {
            this.SHOPNAME = SHOPNAME;
        }

        public Object getSPSID() {
            return SPSID;
        }

        public void setSPSID(Object SPSID) {
            this.SPSID = SPSID;
        }

        public String getORDL() {
            return ORDL;
        }

        public void setORDL(String ORDL) {
            this.ORDL = ORDL;
        }

        public String getSKUID() {
            return SKUID;
        }

        public void setSKUID(String SKUID) {
            this.SKUID = SKUID;
        }

        public String getORDERQUANTITY() {
            return ORDERQUANTITY;
        }

        public void setORDERQUANTITY(String ORDERQUANTITY) {
            this.ORDERQUANTITY = ORDERQUANTITY;
        }

        public String getAPPROVEDQUANTITY() {
            return APPROVEDQUANTITY;
        }

        public void setAPPROVEDQUANTITY(String APPROVEDQUANTITY) {
            this.APPROVEDQUANTITY = APPROVEDQUANTITY;
        }

        public String getUNITPRICE() {
            return UNITPRICE;
        }

        public void setUNITPRICE(String UNITPRICE) {
            this.UNITPRICE = UNITPRICE;
        }

        public String getPRICE() {
            return PRICE;
        }

        public void setPRICE(String PRICE) {
            this.PRICE = PRICE;
        }

        public String getADJUSTMENT() {
            return ADJUSTMENT;
        }

        public void setADJUSTMENT(String ADJUSTMENT) {
            this.ADJUSTMENT = ADJUSTMENT;
        }

        public String getADJUSTMENTDESC() {
            return ADJUSTMENTDESC;
        }

        public void setADJUSTMENTDESC(String ADJUSTMENTDESC) {
            this.ADJUSTMENTDESC = ADJUSTMENTDESC;
        }

        public String getSUBTOTAL() {
            return SUBTOTAL;
        }

        public void setSUBTOTAL(String SUBTOTAL) {
            this.SUBTOTAL = SUBTOTAL;
        }

        public String getTOTALPRICE() {
            return TOTALPRICE;
        }

        public void setTOTALPRICE(String TOTALPRICE) {
            this.TOTALPRICE = TOTALPRICE;
        }

        public String getPRODUCTNAME() {
            return PRODUCTNAME;
        }

        public void setPRODUCTNAME(String PRODUCTNAME) {
            this.PRODUCTNAME = PRODUCTNAME;
        }

        public Object getDELIVERYDATE() {
            return DELIVERYDATE;
        }

        public void setDELIVERYDATE(Object DELIVERYDATE) {
            this.DELIVERYDATE = DELIVERYDATE;
        }

        public Object getSALESINVOICENO() {
            return SALESINVOICENO;
        }

        public void setSALESINVOICENO(Object SALESINVOICENO) {
            this.SALESINVOICENO = SALESINVOICENO;
        }

        public String getDISCOUNT() {
            return DISCOUNT;
        }

        public void setDISCOUNT(String DISCOUNT) {
            this.DISCOUNT = DISCOUNT;
        }

        public Object getTAXID() {
            return TAXID;
        }

        public void setTAXID(Object TAXID) {
            this.TAXID = TAXID;
        }

        public String getSCHEMEID() {
            return SCHEMEID;
        }

        public void setSCHEMEID(String SCHEMEID) {
            this.SCHEMEID = SCHEMEID;
        }

        public Object getTAXNAME() {
            return TAXNAME;
        }

        public void setTAXNAME(Object TAXNAME) {
            this.TAXNAME = TAXNAME;
        }

        public Object getTAXVALUE() {
            return TAXVALUE;
        }

        public void setTAXVALUE(Object TAXVALUE) {
            this.TAXVALUE = TAXVALUE;
        }

        public String getSCHEME() {
            return SCHEME;
        }

        public void setSCHEME(String SCHEME) {
            this.SCHEME = SCHEME;
        }

        public String getSCHEMEDESCRIPTION() {
            return SCHEMEDESCRIPTION;
        }

        public void setSCHEMEDESCRIPTION(String SCHEMEDESCRIPTION) {
            this.SCHEMEDESCRIPTION = SCHEMEDESCRIPTION;
        }

        public Object getSKUIDWITHQUANT() {
            return SKUIDWITHQUANT;
        }

        public void setSKUIDWITHQUANT(Object SKUIDWITHQUANT) {
            this.SKUIDWITHQUANT = SKUIDWITHQUANT;
        }

        public Object getUNITSAVAILABLE() {
            return UNITSAVAILABLE;
        }

        public void setUNITSAVAILABLE(Object UNITSAVAILABLE) {
            this.UNITSAVAILABLE = UNITSAVAILABLE;
        }

        public Object getUNPAIDAMOUNT() {
            return UNPAIDAMOUNT;
        }

        public void setUNPAIDAMOUNT(Object UNPAIDAMOUNT) {
            this.UNPAIDAMOUNT = UNPAIDAMOUNT;
        }

        public Object getCREDITLIMIT() {
            return CREDITLIMIT;
        }

        public void setCREDITLIMIT(Object CREDITLIMIT) {
            this.CREDITLIMIT = CREDITLIMIT;
        }

        public String getOFFERAMOUNT() {
            return OFFERAMOUNT;
        }

        public void setOFFERAMOUNT(String OFFERAMOUNT) {
            this.OFFERAMOUNT = OFFERAMOUNT;
        }

        public String getSESSIONHID() {
            return SESSIONHID;
        }

        public void setSESSIONHID(String SESSIONHID) {
            this.SESSIONHID = SESSIONHID;
        }
    }

    public List<ProductList> getLstorderinfo() {
        return lstorderinfo;
    }

    public void setLstorderinfo(List<ProductList> lstorderinfo) {
        this.lstorderinfo = lstorderinfo;
    }

    public String getStr_Order_Accept_Reject_msg() {
        return str_Order_Accept_Reject_msg;
    }
}

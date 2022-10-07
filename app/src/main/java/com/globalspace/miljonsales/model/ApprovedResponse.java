package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aravind on 13/8/18.
 */

public class ApprovedResponse {
    /*@SerializedName("stockistApprovedOrders")
    public List<getbasicApprovedOrderInfo> lstApproved_array = null;*/

    @SerializedName("stockistApprovedOrders")
    public List<getApprovedOrder> lstApproved_array = null;

    @SerializedName("org_approved_order_details")
    public List<getOrgApprovedOrder> lstorg_approvedorders = null;

    @SerializedName("status")
    public String status;
    @SerializedName("error_message")
    public String Error_message;

    public class getbasicApprovedOrderInfo implements Serializable {
        @SerializedName("TABLE_NAME")
        public String TABLE_NAME;
        @SerializedName("ORG_ID")
        public String ORG_ID;
        @SerializedName("NAME")
        public String NAME;
        @SerializedName("SHOP_NAME")
        public String SHOP_NAME;
        @SerializedName("org_approved_order")
        public List<getApprovedOrder> lstapprovedorder = null;

        public String getTABLE_NAME() {
            return TABLE_NAME;
        }

        public void setTABLE_NAME(String TABLE_NAME) {
            this.TABLE_NAME = TABLE_NAME;
        }

        public String getORG_ID() {
            return ORG_ID;
        }

        public void setORG_ID(String ORG_ID) {
            this.ORG_ID = ORG_ID;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getSHOP_NAME() {
            return SHOP_NAME;
        }

        public void setSHOP_NAME(String SHOP_NAME) {
            this.SHOP_NAME = SHOP_NAME;
        }

        public List<getApprovedOrder> getLstapprovedorder() {
            return lstapprovedorder;
        }

        public void setLstapprovedorder(List<getApprovedOrder> lstapprovedorder) {
            this.lstapprovedorder = lstapprovedorder;
        }
    }

    public class getApprovedOrder implements Serializable {
        @SerializedName("TABLE_NAME")
        public String TABLE_NAME;
        @SerializedName("ORDER_ID")
        public String ORDER_ID;
        @SerializedName("ORG_ID")
        public String S_ORG_ID;
        @SerializedName("ORDER_DATE")
        public String ORDER_DATE;
        @SerializedName("DELIVERY_PERSON_ID")
        public String DELIVERY_PERSON_ID;
        @SerializedName("FST_NAME")
        public String FST_NAME;
        @SerializedName("SALES_INVOICE_NO")
        public String SALES_INVOICE_NO;

        public String getTABLE_NAME() {
            return TABLE_NAME;
        }

        public void setTABLE_NAME(String TABLE_NAME) {
            this.TABLE_NAME = TABLE_NAME;
        }

        public String getORDER_ID() {
            return ORDER_ID;
        }

        public void setORDER_ID(String ORDER_ID) {
            this.ORDER_ID = ORDER_ID;
        }

        public String getS_ORG_ID() {
            return S_ORG_ID;
        }

        public void setS_ORG_ID(String s_ORG_ID) {
            S_ORG_ID = s_ORG_ID;
        }

        public String getORDER_DATE() {
            return ORDER_DATE;
        }

        public void setORDER_DATE(String ORDER_DATE) {
            this.ORDER_DATE = ORDER_DATE;
        }

        public String getDELIVERY_PERSON_ID() {
            return DELIVERY_PERSON_ID;
        }

        public void setDELIVERY_PERSON_ID(String DELIVERY_PERSON_ID) {
            this.DELIVERY_PERSON_ID = DELIVERY_PERSON_ID;
        }

        public String getSALES_INVOICE_NO() {
            return SALES_INVOICE_NO;
        }

        public void setSALES_INVOICE_NO(String SALES_INVOICE_NO) {
            this.SALES_INVOICE_NO = SALES_INVOICE_NO;
        }

        public String getFST_NAME() {
            return FST_NAME;
        }

        public void setFST_NAME(String FST_NAME) {
            this.FST_NAME = FST_NAME;
        }
    }

    public class getOrgApprovedOrder implements Serializable {
        @SerializedName("TABLE_NAME")
        @Expose
        private String TABLENAME;
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
        @SerializedName("FST_NAME")
        @Expose
        private String FSTNAME;
        @SerializedName("SHOP_NAME")
        @Expose
        private String SHOPNAME;
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
        private String DELIVERYDATE;
        @SerializedName("SALES_INVOICE_NO")
        @Expose
        private String SALESINVOICENO;
        @SerializedName("DISCOUNT")
        @Expose
        private String DISCOUNT;
        @SerializedName("TAX_ID")
        @Expose
        private Object TAXID;
        @SerializedName("SCHEME")
        @Expose
        private String SCHEME;
        @SerializedName("SCHEME_ID")
        @Expose
        private String SCHEMEID;
        @SerializedName("SCHEME_DESCRIPTION")
        @Expose
        private String SCHEMEDESCRIPTION;
        @SerializedName("TAX_NAME")
        @Expose
        private Object TAXNAME;
        @SerializedName("TAX_VALUE")
        @Expose
        private Object TAXVALUE;
        @SerializedName("OFFER_AMOUNT")
        @Expose
        private String OFFERAMOUNT;

        public String getTABLENAME() {
            return TABLENAME;
        }

        public void setTABLENAME(String TABLENAME) {
            this.TABLENAME = TABLENAME;
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

        public String getFSTNAME() {
            return FSTNAME;
        }

        public void setFSTNAME(String FSTNAME) {
            this.FSTNAME = FSTNAME;
        }

        public String getSHOPNAME() {
            return SHOPNAME;
        }

        public void setSHOPNAME(String SHOPNAME) {
            this.SHOPNAME = SHOPNAME;
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

        public String getDELIVERYDATE() {
            return DELIVERYDATE;
        }

        public String getSALESINVOICENO() {
            return SALESINVOICENO;
        }

        public void setSALESINVOICENO(String SALESINVOICENO) {
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

        public String getSCHEME() {
            return SCHEME;
        }

        public void setSCHEME(String SCHEME) {
            this.SCHEME = SCHEME;
        }

        public String getSCHEMEID() {
            return SCHEMEID;
        }

        public void setSCHEMEID(String SCHEMEID) {
            this.SCHEMEID = SCHEMEID;
        }

        public String getSCHEMEDESCRIPTION() {
            return SCHEMEDESCRIPTION;
        }

        public void setSCHEMEDESCRIPTION(String SCHEMEDESCRIPTION) {
            this.SCHEMEDESCRIPTION = SCHEMEDESCRIPTION;
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

        public String getOFFERAMOUNT() {
            return OFFERAMOUNT;
        }

        public void setOFFERAMOUNT(String OFFERAMOUNT) {
            this.OFFERAMOUNT = OFFERAMOUNT;
        }
    }

    public List<getApprovedOrder> getLstApproved_array() {
        return lstApproved_array;
    }

    public List<getOrgApprovedOrder> getLstorg_approvedorders() {
        return lstorg_approvedorders;
    }

    public String getStatus() {
        return status;
    }

    public String getError_message() {
        return Error_message;
    }
}

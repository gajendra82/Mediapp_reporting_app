package com.globalspace.miljonsales.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Stockist_response {

    @SerializedName("stockistPendingOrdersFromChm")
    public List<getpendingstockistInfo> lststockist_array = null;

    @SerializedName("status")
    public String status;
    @SerializedName("error_message")
    public String Error_message;

    //region unused class
    public class getbasicstockistInfo implements Serializable {
        @SerializedName("TABLE_NAME")
        public String TABLE_NAME;
        @SerializedName("ORG_ID")
        public String ORG_ID;
        @SerializedName("NAME")
        public String NAME;
        @SerializedName("SHOP_NAME")
        public String SHOP_NAME;
        @SerializedName("FLAG")
        public String FLAG;
        @SerializedName("org_pending_order")
        public List<getpendingstockistInfo> lstpendingarray = null;

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

        public String getFLAG() {
            return FLAG;
        }

        public void setFLAG(String FLAG) {
            this.FLAG = FLAG;
        }

        public List<getpendingstockistInfo> getLstpendingarray() {
            return lstpendingarray;
        }

        public void setLstpendingarray(List<getpendingstockistInfo> lstpendingarray) {
            this.lstpendingarray = lstpendingarray;
        }
    }
    //endregion

    public class getpendingstockistInfo implements Serializable {
        @SerializedName("TABLE_NAME")
        public String TABLE_NAME;
        @SerializedName("ORDER_ID")
        public String ORDER_ID;
        @SerializedName("S_ORG_ID")
        public String S_ORG_ID;
        @SerializedName("ORDER_DATE")
        public String ORDER_DATE;
        @SerializedName("DELIVERY_PERSON_ID")
        public String DELIVERY_PERSON_ID;
        @SerializedName("SALES_INVOICE_NO")
        public String SALES_INVOICE_NO;
        @SerializedName("ORG_ID")
        public String ORG_ID;
        @SerializedName("Mark_as_Delivered")
        public String Mark_as_Delivered;

        public String getTABLE_NAME() {
            return TABLE_NAME;
        }


        public String getORDER_ID() {
            return ORDER_ID;
        }


        public String getS_ORG_ID() {
            return S_ORG_ID;
        }


        public String getORDER_DATE() {
            return ORDER_DATE;
        }


        public String getDELIVERY_PERSON_ID() {
            return DELIVERY_PERSON_ID;
        }


        public String getSALES_INVOICE_NO() {
            return SALES_INVOICE_NO;
        }


        public String getORG_ID() {
            return ORG_ID;
        }

        public String getMark_as_Delivered() {
            return Mark_as_Delivered;
        }
    }

    public List<getpendingstockistInfo> getLststockist_array() {
        return lststockist_array;
    }

    public String getStatus() {
        return status;
    }

    public String getError_message() {
        return Error_message;
    }
}

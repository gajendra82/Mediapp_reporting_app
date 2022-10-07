package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderDetailsResponse implements Serializable {

    @SerializedName("status")
    private String status;

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    @SerializedName("data")
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @SerializedName("PRODUCT_ORDER_INFO")
        private ArrayList<OrderInfo> orderInfos;

        @SerializedName("TOTAL_PRODUCT_INFO")
        private ArrayList<TotalProductInfo> totalProductInfo;

        @SerializedName("PRODUCT_INFO")
        private ArrayList<ProductInfo> productInfos;

        public ArrayList<OrderInfo> getOrderInfos() {
            return orderInfos;
        }

        public void setOrderInfos(ArrayList<OrderInfo> orderInfos) {
            this.orderInfos = orderInfos;
        }

        public ArrayList<TotalProductInfo> getTotalProductInfo() {
            return totalProductInfo;
        }

        public void setTotalProductInfo(ArrayList<TotalProductInfo> totalProductInfo) {
            this.totalProductInfo = totalProductInfo;
        }

        public ArrayList<ProductInfo> getProductInfos() {
            return productInfos;
        }

        public void setProductInfos(ArrayList<ProductInfo> productInfos) {
            this.productInfos = productInfos;
        }
    }


    public class OrderInfo {

        @SerializedName("TABLE_NAME")
        private String tableName;

        @SerializedName("CHEMIST_SHOP_NAME")
        private String chemistName;

        @SerializedName("TRANSACTION_DATE")
        private String transactionDate;

        @SerializedName("STOCKIEST_SHOP_NAME")
        private String stockiestName;

        @SerializedName("TOTAL_AMOUNT")
        private String totalAmount;

        @SerializedName("TRANSACTION_STATUS")
        private String transactionStatus;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
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

        public String getStockiestName() {
            return stockiestName;
        }

        public void setStockiestName(String stockiestName) {
            this.stockiestName = stockiestName;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getTransactionStatus() {
            return transactionStatus;
        }

        public void setTransactionStatus(String transactionStatus) {
            this.transactionStatus = transactionStatus;
        }
    }

    public class ProductInfo {

        @SerializedName("TABLE_NAME")
        private String tableName;

        @SerializedName("PRODUCT_NAME")
        private String productName;

        @SerializedName("PRODUCT_QUANTITY")
        private String productQuantity;

        @SerializedName("PRODUCT_PRICE")
        private String productPrice;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(String productQuantity) {
            this.productQuantity = productQuantity;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }
    }

    public class TotalProductInfo {

        @SerializedName("TABLE_NAME")
        private String tableName;

        @SerializedName("TOTAL_PRODUCT_QUANTITY")
        private String totalQuantity;

        @SerializedName("TOTAL_PRODUCT_PRICE")
        private String totalPrice;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getTotalQuantity() {
            return totalQuantity;
        }

        public void setTotalQuantity(String totalQuantity) {
            this.totalQuantity = totalQuantity;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }
    }

}

package com.globalspace.miljonsales.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductResponse implements Serializable {

    @SerializedName("status")
    private String status;
    @SerializedName("error")
    private String error;
    @SerializedName("errorMessage")
    private String errorMessage;

    @SerializedName("data")
    private ArrayList<Product> data;

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

    public ArrayList<Product> getData() {
        return data;
    }

    public void setData(ArrayList<Product> data) {
        this.data = data;
    }

    public class Product {

        @SerializedName("IMAGES")
        private String prodImage;

        @SerializedName("PRODUCT_ID")
        private String productId;

        @SerializedName("PROD_NAME")
        private String prodName;

        @SerializedName("PROD_DESC")
        private String prodDesc;

        @SerializedName("PACK")
        private String pack;

        @SerializedName("MSP_PER")
        private String pts;

        @SerializedName("MRP")
        private String mrp;

        private String status;
        private String quantity;

        @SerializedName("CATEGORY")
        private String prodCategory;

        @SerializedName("COMPANY_NAME")
        private String companyName;

        @SerializedName("MANUFCTR_ID")
        private String manufacture_id;


        @SerializedName("PROD_COMPOSITION")
        private String prodComposition;

        private String available;


        public String getProdComposition() {
            return prodComposition;
        }

        public void setProdComposition(String prodComposition) {
            this.prodComposition = prodComposition;
        }

        public String getAvailable() {
            return available;
        }

        public void setAvailable(String available) {
            this.available = available;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProdCategory() {
            return prodCategory;
        }

        public void setProdCategory(String prodCategory) {
            this.prodCategory = prodCategory;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getManufacture_id() {
            return manufacture_id;
        }

        public void setManufacture_id(String manufacture_id) {
            this.manufacture_id = manufacture_id;
        }


        public String getProdImage() {
            return prodImage;
        }

        public void setProdImage(String prodImage) {
            this.prodImage = prodImage;
        }

        public String getProdName() {
            return prodName;
        }

        public void setProdName(String prodName) {
            this.prodName = prodName;
        }

        public String getProdDesc() {
            return prodDesc;
        }

        public void setProdDesc(String prodDesc) {
            this.prodDesc = prodDesc;
        }

        public String getPack() {
            return pack;
        }

        public void setPack(String pack) {
            this.pack = pack;
        }

        public String getPts() {
            return pts;
        }

        public void setPts(String pts) {
            this.pts = pts;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }
}

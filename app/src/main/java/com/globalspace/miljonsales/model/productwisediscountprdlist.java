package com.globalspace.miljonsales.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class productwisediscountprdlist {

        @SerializedName("MANUFACTURE_INFO")
        @Expose
        private List<ManufactureInfo> manufactureInfo = null;
        @SerializedName("TOTAL_COUNT")
        @Expose
        private String totalCount;
        @SerializedName("PRODUCT_INFO")
        @Expose
        private List<discountProductInfo> productInfo = null;
        @SerializedName("CATEGORY_INFO")
        @Expose
        private List<CategoryInfo> categoryInfo = null;
        @SerializedName("STATUS")
        @Expose
        private String status;
        @SerializedName("ERROR")
        @Expose
        private String error;

        public List<ManufactureInfo> getManufactureInfo() {
            return manufactureInfo;
        }

        public void setManufactureInfo(List<ManufactureInfo> manufactureInfo) {
            this.manufactureInfo = manufactureInfo;
        }

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public List<discountProductInfo> getProductInfo() {
            return productInfo;
        }

        public void setProductInfo(List<discountProductInfo> productInfo) {
            this.productInfo = productInfo;
        }

        public List<CategoryInfo> getCategoryInfo() {
            return categoryInfo;
        }

        public void setCategoryInfo(List<CategoryInfo> categoryInfo) {
            this.categoryInfo = categoryInfo;
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

    }



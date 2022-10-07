package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryInfo {

        @SerializedName("TABLE_NAME")
        @Expose
        private String tableName;
        @SerializedName("CATEGORY_ID")
        @Expose
        private String categoryId;
        @SerializedName("PROD_CATEGORY")
        @Expose
        private String prodCategory;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getProdCategory() {
            return prodCategory;
        }

        public void setProdCategory(String prodCategory) {
            this.prodCategory = prodCategory;
        }

    }

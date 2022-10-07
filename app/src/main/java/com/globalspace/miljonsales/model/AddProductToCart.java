package com.globalspace.miljonsales.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aravind on 19/3/19.
 */

public class AddProductToCart implements Serializable {
    public String productId;
    public String productName;
    public String selectedQuantity;
    public String mrp;
    public String pts;
    public String taxValue;
    public String schemeId;
    public String schemeQuantity;
    public String schemeValue;
    public String discount;


    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getSchemeQuantity() {
        return schemeQuantity;
    }

    public void setSchemeQuantity(String schemeQuantity) {
        this.schemeQuantity = schemeQuantity;
    }

    public String getSchemeValue() {
        return schemeValue;
    }

    public void setSchemeValue(String schemeValue) {
        this.schemeValue = schemeValue;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(String selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getPts() {
        return pts;
    }

    public void setPts(String pts) {
        this.pts = pts;
    }

    public String getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(String taxValue) {
        this.taxValue = taxValue;
    }
}

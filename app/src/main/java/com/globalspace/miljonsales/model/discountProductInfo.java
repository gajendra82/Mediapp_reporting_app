package com.globalspace.miljonsales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class discountProductInfo {

    @SerializedName("TABLE_NAME")
    @Expose
    private String tableName;
    @SerializedName("PRODUCT_ID")
    @Expose
    private String productId;
    @SerializedName("PROD_NAME")
    @Expose
    private String prodName;
    @SerializedName("PROD_COMPOSITION")
    @Expose
    private String prodComposition;


    @SerializedName("MSP")
    @Expose
    private String prodMRP;
    @SerializedName("PROD_PTR")
    @Expose
    private String prodPTR;
    @SerializedName("PTS")
    @Expose
    private String prodPTS;
    @SerializedName("TAX_NAME")
    @Expose
    private String taxName;
    @SerializedName("GSTN_VALUE")
    @Expose
    private String taxValue;
    @SerializedName("DISCOUNT")
    @Expose
    private String discount;
    @SerializedName("SCHEME_ID")
    @Expose
    private String scheme_id;
    @SerializedName("SCHEME")
    @Expose
    private String scheme;
    @SerializedName("SCHEME_DESCRIPTION")
    @Expose
    private String schemeDescription;
    @SerializedName("SCHEME_QUANTITY")
    @Expose
    private String schemeQuantity;
    @SerializedName("SCHEME_VALUE")
    @Expose
    private String schemeValue;
    @SerializedName("IS_SELECTED")
    @Expose
    private boolean Is_selected;
    @Expose
    private String selectedQty;


    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getSchemeDescription() {
        return schemeDescription;
    }

    public void setSchemeDescription(String schemeDescription) {
        this.schemeDescription = schemeDescription;
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

    public String getScheme_id() {
        return scheme_id;
    }

    public void setScheme_id(String scheme_id) {
        this.scheme_id = scheme_id;
    }

    public String getSelectedQty() {
        return selectedQty;
    }

    public void setSelectedQty(String selectedQty) {
        this.selectedQty = selectedQty;
    }

    public boolean isIs_selected() {
        return Is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        Is_selected = is_selected;
    }


    public String getProdMRP() {
        return prodMRP;
    }

    public void setProdMRP(String prodMRP) {
        this.prodMRP = prodMRP;
    }

    public String getProdPTR() {
        return prodPTR;
    }

    public void setProdPTR(String prodPTR) {
        this.prodPTR = prodPTR;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public String getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(String taxValue) {
        this.taxValue = taxValue;
    }

    public String getProdPTS() {
        return prodPTS;
    }

    public void setProdPTS(String prodPTS) {
        this.prodPTS = prodPTS;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdComposition() {
        return prodComposition;
    }

    public void setProdComposition(String prodComposition) {
        this.prodComposition = prodComposition;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

}

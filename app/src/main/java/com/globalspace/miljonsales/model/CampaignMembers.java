package com.globalspace.miljonsales.model;

import com.google.gson.annotations.SerializedName;

public class CampaignMembers {

    @SerializedName("C_ORG_ID")
    private String chemistId;

    @SerializedName("EMPLOYEE_ID")
    private String empId;

    @SerializedName("CHEMIST_SHOP_NAME")
    private String chemistShopName;

    @SerializedName("EMAIL")
    private String email;

    @SerializedName("CHEMIST_ADDRESS")
    private String chemistAddress;
}

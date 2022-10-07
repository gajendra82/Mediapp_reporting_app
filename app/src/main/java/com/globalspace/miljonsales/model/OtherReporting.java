package com.globalspace.miljonsales.model;

import com.google.gson.annotations.SerializedName;

public class OtherReporting {
    @SerializedName("ACTIVITY_ID")
    private String activityId;

    @SerializedName("ACTIVITY_TYPE")
    private String activityType;

    @SerializedName("STATUS")
    private String status;

    @SerializedName("Message")
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
}

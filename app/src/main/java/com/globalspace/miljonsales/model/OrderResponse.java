package com.globalspace.miljonsales.model;

public class OrderResponse {

   // private String txn_message;
    private String order_id;
    private String status;
    private String error;
    private String MESSAGE;

//    public String getTxn_message() {
//        return txn_message;
//    }
//
//    public void setTxn_message(String txn_message) {
//        this.txn_message = txn_message;
//    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }
}

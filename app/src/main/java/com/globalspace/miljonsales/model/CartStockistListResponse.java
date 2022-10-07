package com.globalspace.miljonsales.model;

import java.util.ArrayList;

public class CartStockistListResponse {

    private String status;
    private String error;
    private ArrayList<StockistData> Stockist_List;

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

    public ArrayList<StockistData> getStockist_List() {
        return Stockist_List;
    }

    public void setStockist_List(ArrayList<StockistData> stockist_List) {
        Stockist_List = stockist_List;
    }
}

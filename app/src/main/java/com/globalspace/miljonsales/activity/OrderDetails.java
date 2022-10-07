package com.globalspace.miljonsales.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.adapter.OrderProductAdapter;
import com.globalspace.miljonsales.model.OrderDetailsResponse;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetails extends Activity {

    private TextView orderNo, transactionDate, chemistName, stockiestName, totalQty, totalAmount, orderStatus;
    private RecyclerView productRecycler;
    private ImageView backBtn;
    private String orderId, status;
    private ApiInterface apiInterface;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        setComponents();
        Bundle b = getIntent().getExtras();
        orderId = b.getString("OrderId");
        status = b.getString("Status");
        getData("getMiljonOrderData", orderId, status);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        OrderDetails.this.finish();
    }

    private void setComponents() {

        backBtn = (ImageView) findViewById(R.id.iv_back);
        showProgressDialog();
        orderNo = (TextView) findViewById(R.id.orderNo);
        transactionDate = (TextView) findViewById(R.id.transactionDate);
        chemistName = (TextView) findViewById(R.id.chemistName);
        stockiestName = (TextView) findViewById(R.id.stockiestName);
        totalAmount = (TextView) findViewById(R.id.tv_totalprice);
        totalQty = (TextView) findViewById(R.id.tv_totalqty);
        orderStatus = (TextView) findViewById(R.id.orderStatus);
        productRecycler = (RecyclerView) findViewById(R.id.productRecycler);
        productRecycler.setLayoutManager(new LinearLayoutManager(OrderDetails.this, LinearLayoutManager.VERTICAL, false));
        apiInterface = ApiUtils.getDashboardData();

    }

    private void showProgressDialog() {
        progress = new android.app.ProgressDialog(OrderDetails.this);
        progress.setMax(100);
        //progress.setTitle("Progress Dialog");
        progress.setMessage("Your Data is loading Please Wait.......");
        progress.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);

    }

    private void getData(String function_name, String OrderId, String Status) {
        if (Internet.checkConnection(OrderDetails.this)) {
            progress.show();
            Call<OrderDetailsResponse> call = apiInterface.getOrderDetails(function_name, OrderId, Status);

            call.enqueue(new Callback<OrderDetailsResponse>() {
                @Override
                public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {
                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                    if (response.body().getStatus().equals("200")) {
                        orderNo.setText(orderId);
                        chemistName.setText(response.body().getData().getOrderInfos().get(0).getChemistName());
                        transactionDate.setText(response.body().getData().getOrderInfos().get(0).getTransactionDate());
                        orderStatus.setText(response.body().getData().getOrderInfos().get(0).getTransactionStatus());
                        stockiestName.setText(response.body().getData().getOrderInfos().get(0).getStockiestName());
                        totalQty.setText(response.body().getData().getTotalProductInfo().get(0).getTotalQuantity());
                        totalAmount.setText(response.body().getData().getTotalProductInfo().get(0).getTotalPrice());
                        productRecycler.setAdapter(new OrderProductAdapter(OrderDetails.this,response.body().getData().getProductInfos()));
                    } else {
                        Toast.makeText(OrderDetails.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                   // Toast.makeText(OrderDetails.this, "Error Occurred !", Toast.LENGTH_SHORT).show();
                    Toast.makeText(OrderDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(OrderDetails.this, "No internet Connection ! ", Toast.LENGTH_SHORT).show();
        }
    }
}

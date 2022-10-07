package com.globalspace.miljonsales.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.MiljonOffer;
import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.adapter.StockUpdateAdapter;
import com.globalspace.miljonsales.model.ProductResponse;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockUpdate extends Activity {

    private ImageView backBtn;
    private TextView title, checkBtn;
    private RecyclerView recyclerView;
    private StockUpdateAdapter adapter;
    private String org_id;
    private ApiInterface apiInterface;
    private ProgressDialog progress;
    public static ArrayList<ProductResponse.Product> productList;
    private SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_update);
        setComponents();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    private void setComponents() {
        backBtn = (ImageView) findViewById(R.id.backBtn);
        sPref = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        showProgressDialog();
        title = (TextView) findViewById(R.id.title);
        checkBtn = (TextView) findViewById(R.id.checkBtn);
        checkBtn.setTypeface(MiljonOffer.solidFont);
        checkBtn.setVisibility(View.VISIBLE);
        title.setText("Stock Update");
        recyclerView = (RecyclerView) findViewById(R.id.productList);
        apiInterface = ApiUtils.getDashboardData();
        recyclerView.setLayoutManager(new GridLayoutManager(StockUpdate.this, 2, LinearLayoutManager.VERTICAL, false));
        org_id = getIntent().getExtras().getString("StockId");
        getData("getStockistProductForOrder", org_id);

    }


    private void showProgressDialog() {
        progress = new android.app.ProgressDialog(StockUpdate.this);
        progress.setMax(100);
        //progress.setTitle("Progress Dialog");
        progress.setMessage("Your Data is loading Please Wait.......");
        progress.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
    }


    private void getData(String function_name, String org_id) {
        if (Internet.checkConnection(StockUpdate.this)) {
            progress.show();
            Call<ProductResponse> call = apiInterface.getProductList(function_name,
                    org_id);

            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                    if (response.body().getStatus().equals("200")) {
                        productList = response.body().getData();
                        adapter = new StockUpdateAdapter(StockUpdate.this);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(StockUpdate.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                    Toast.makeText(StockUpdate.this, "Error Occurred !", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "No internet Connection ! ", Toast.LENGTH_SHORT).show();
        }
    }

    private JSONObject setData() {

        JSONObject productObject = new JSONObject();
        try {
            productObject.put("Emp_id", sPref.getString(getResources().getString(R.string.employee_id), ""));
            productObject.put("Stockist_id", org_id);
            JSONArray array = new JSONArray();

            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getAvailable() != null) {
                    if (!productList.get(i).getAvailable().equals("")) {
                        JSONObject productData = new JSONObject();
                        productData.put("ProductId", productList.get(i).getProductId());
                        if (productList.get(i).equals("Available"))
                            productData.put("Availability", "ACTIVE");
                        else
                            productData.put("Availability", "DEACTIVE");
                        array.put(productData);
                    }
                }
            }
            productObject.put("ProductData", array);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return productObject;
    }

    private void sendData() {
        if (Internet.checkConnection(StockUpdate.this)) {
            progress.show();
            Call<com.globalspace.miljonsales.model.Response> call = apiInterface.setProductAvailStockiest("setProductAvailStockiest", setData());
            call.enqueue(new Callback<com.globalspace.miljonsales.model.Response>() {
                @Override
                public void onResponse(Call<com.globalspace.miljonsales.model.Response> call, Response<com.globalspace.miljonsales.model.Response> response) {
                    if (response.body().getStatus().equals("200")) {
                        progress.dismiss();
                        Toast.makeText(StockUpdate.this, "Stock Updated Successfully", Toast.LENGTH_SHORT).show();
                        StockUpdate.this.finish();
                    } else {
                        progress.dismiss();
                        Toast.makeText(StockUpdate.this, "Something Went Wrong !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<com.globalspace.miljonsales.model.Response> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(StockUpdate.this, "Something Went Wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        StockUpdate.this.finish();
    }
}

package com.globalspace.miljonsales.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.adapter.LeavesAdapter;
import com.globalspace.miljonsales.model.LeavesResponse;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeavesActivity extends Activity {

    private TextView toolbarTitle;
    private ImageView backBtn;
    private RecyclerView recyclerView;
    private ProgressDialog progress;
    private ApiInterface apiInterface;
    private SharedPreferences sPref;
    private String selected_date, EmpId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pob);
        setContent();
        if (getIntent().getExtras() != null) {
            selected_date = getIntent().getExtras().getString("date");
            EmpId = getIntent().getExtras().getString("EmpId");
            getData("getEmpLeaveDetail", selected_date, EmpId);
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void setContent() {
        toolbarTitle = (TextView) findViewById(R.id.title);
        toolbarTitle.setText("Leaves");

        backBtn = (ImageView) findViewById(R.id.backBtn);
        recyclerView = (RecyclerView) findViewById(R.id.pobList);
        apiInterface = ApiUtils.getDashboardData();
        sPref = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
        showProgressDialog();
        recyclerView.setLayoutManager(new LinearLayoutManager(LeavesActivity.this, LinearLayoutManager.VERTICAL, false));
    }


    private void showProgressDialog() {
        progress = new android.app.ProgressDialog(LeavesActivity.this);
        progress.setMax(100);
        //progress.setTitle("Progress Dialog");
        progress.setMessage("Your Data is loading Please Wait.......");
        progress.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LeavesActivity.this.finish();
    }


    private void getData(String function_name, String date, String EmpId) {
        if (com.globalspace.miljonsales.utils.Internet.checkConnection(LeavesActivity.this)) {
            progress.show();
            Call<LeavesResponse> call = apiInterface.getEmpLeaveDetail(function_name,
                    EmpId, date);

            call.enqueue(new Callback<LeavesResponse>() {
                @Override
                public void onResponse(Call<LeavesResponse> call, Response<LeavesResponse> response) {
                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                    if (response.body().getStatus().equals("200")) {
                        recyclerView.setAdapter(new LeavesAdapter(LeavesActivity.this, response.body().getData()));
                    } else {
                        Toast.makeText(LeavesActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LeavesResponse> call, Throwable t) {
                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                    Toast.makeText(LeavesActivity.this, "Error Occurred ! ", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "No internet Connection ! ", Toast.LENGTH_SHORT).show();
        }
    }


}

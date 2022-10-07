package com.globalspace.miljonsales.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.MiljonOffer;
import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.adapter.DownloadsAdapter;
import com.globalspace.miljonsales.model.DownloadResponse;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadsActivity extends Activity {

    private TextView toolbarTitle, selectDate, calIcon, closeIcon;
    private LinearLayout selectDateLL, filterLL;
    private CardView filtersCV;
    private ImageView backBtn;
    private RecyclerView recyclerView;
    private ProgressDialog progress;
    private SharedPreferences sPref;
    private ApiInterface apiInterface;
    private String function_name;
    private String selected_date;
    private String EmpId, Type;
    private View view;
    private Date date;
    private DownloadsAdapter downloadsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pob);
        setContent();
        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            function_name = b.getString("function_name");
            selected_date = b.getString("date");
            EmpId = b.getString("EmpId");
            Type = b.getString("Type");
            if (Type.equals("Monthly")) {
                filtersCV.setVisibility(View.VISIBLE);
            }
            getData(function_name, selected_date, EmpId);
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region Date Picker

                final Calendar c = Calendar.getInstance();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                try {
                    date = format.parse(selected_date + "T09:27:37Z");
                    c.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = 1;
                c.set(mYear, mMonth, mDay);


                DatePickerDialog datePickerDialog = new DatePickerDialog(DownloadsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                downloadsAdapter.getFilter().filter("");
                                int month = monthOfYear + 1;
                                int day = dayOfMonth;
                                String monthStr, dayStr;
                                if (month < 10) {
                                    monthStr = "0" + month;
                                } else {
                                    monthStr = String.valueOf(month);
                                }

                                if (day < 10) {
                                    dayStr = "0" + day;
                                } else {
                                    dayStr = String.valueOf(day);
                                }
                                String date = year + "-" + monthStr + "-" + dayStr;
                                selectDate.setText(date);
                                closeIcon.setVisibility(View.VISIBLE);
                                downloadsAdapter.getFilter().filter(date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                c.set(mYear, mMonth, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                datePickerDialog.show();

                //endregion
            }
        });

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadsAdapter.getFilter().filter("");
                selectDate.setText("SELECT DATE");
                closeIcon.setVisibility(View.GONE);
            }
        });
    }


    private void setContent() {
        toolbarTitle = (TextView) findViewById(R.id.title);
        filtersCV = (CardView) findViewById(R.id.filterCardView);
        filterLL = (LinearLayout) findViewById(R.id.filterIconLL);
        filterLL.setVisibility(View.GONE);
        selectDateLL = (LinearLayout) findViewById(R.id.selectDateLL);
        selectDate = (TextView) findViewById(R.id.selectDate);
        calIcon = (TextView) findViewById(R.id.calIcon);
        calIcon.setTypeface(MiljonOffer.solidFont);

        closeIcon = (TextView) findViewById(R.id.closeIcon);
        closeIcon.setTypeface(MiljonOffer.solidFont);
        closeIcon.setVisibility(View.GONE);

        view = (View) findViewById(R.id.view);
        view.setVisibility(View.GONE);
        toolbarTitle.setText("Downloads");
        apiInterface = ApiUtils.getDashboardData();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        recyclerView = (RecyclerView) findViewById(R.id.pobList);
        sPref = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
        showProgressDialog();
        recyclerView.setLayoutManager(new LinearLayoutManager(DownloadsActivity.this, LinearLayoutManager.VERTICAL, false));
    }


    private void showProgressDialog() {
        progress = new android.app.ProgressDialog(DownloadsActivity.this);
        progress.setMax(100);
        //progress.setTitle("Progress Dialog");
        progress.setMessage("Your Data is loading Please Wait.......");
        progress.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);

    }

    private void getData(String function_name, String date, String EmpId) {
        if (Internet.checkConnection(DownloadsActivity.this)) {
            progress.show();
            Call<DownloadResponse> call = apiInterface.getDownloadDetail(function_name,
                    EmpId, date);

            call.enqueue(new Callback<DownloadResponse>() {
                @Override
                public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                    if (response.body().getStatus().equals("200")) {
                        downloadsAdapter = new DownloadsAdapter(DownloadsActivity.this, response.body().getData());
                        recyclerView.setAdapter(downloadsAdapter);
                    } else {
                        Toast.makeText(DownloadsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DownloadResponse> call, Throwable t) {
                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                    Toast.makeText(DownloadsActivity.this, "Could not connect to server", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "No internet Connection ! ", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DownloadsActivity.this.finish();
    }
}

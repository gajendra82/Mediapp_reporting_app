package com.globalspace.miljonsales.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.MiljonOffer;
import com.globalspace.miljonsales.POBActivity;
import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.model.DashboardData;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubordinateDashboardActivity extends Activity {

    private String EmpId;
    private String SubordinateName;
    private String SelectedDate = "";
    private ImageView backBtn;
    private TextView toolbartitle;
    private TextView selectDate, downarrow, callsPanned, targetCall, deviation, expectedAverage, actuallAverage,
            pob, tv_totaldeliverycount,monthlyCalls, downloads, tv_monthpob, monthlyDownloads, monthlyLeaves, tvapproved, tvpending, tvdecline, tvpostpone;

    private LinearLayout callsPlannedLL, actualAvgLL, pobLL, monthlyCallLL, downloadsLL,deliveredLL, monthpobLL, monthlyDownloadLL, monthlyLeaveLL,
    pendingLL, approvedLL, declineLL, postponeLL;;
    private int mDay, mMonth, mYear;
    private String function_name = "dashboardReport";
    private int callsPlannedValue, targetCallsValue;
    private ArrayList<DashboardData.ReportingDetails> reportingDetails;
    private ArrayList<DashboardData.ReportingDetails> monthlyreportingDetails;
    private ArrayList<DashboardData.ReportingCallAvg> reportingCallAvgs;
    private List<DashboardData.MONTHLYAPPROVEDPOB> approvedob;
    private List<DashboardData.MONTHLYPENDINGPOB> pendingpob;
    private List<DashboardData.MONTHLYCANCELPOB> cancelpob;
    private List<DashboardData.MONTHLYPOSTPONEPOB> postponepob;
    private ArrayList<DashboardData.MONTHLYDELIVEREDPOB> deliverpob;
    private ApiInterface apiInterface;
    private Float totalpob;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subordinate_dashboard);
        setContent();
        callsPlannedLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region Calls Planned Click
                if (!callsPanned.getText().toString().equals("0")) {
                    if (reportingDetails != null) {
                        Intent i = new Intent(SubordinateDashboardActivity.this, CallDetails.class);
                        i.putExtra("Type", "Daily");
                        i.putExtra("ReportingData", reportingDetails);
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(SubordinateDashboardActivity.this, "No Reporting done", Toast.LENGTH_SHORT).show();
                }
                //endregion
            }
        });

        monthlyCallLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region Calls Planned Click
                if (!monthlyCalls.getText().toString().equals("0")) {
                    if (monthlyreportingDetails != null) {
                        Intent i = new Intent(SubordinateDashboardActivity.this, CallDetails.class);
                        i.putExtra("Type", "Monthly");
                        i.putExtra("ReportingData", monthlyreportingDetails);
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(SubordinateDashboardActivity.this, "No Reporting done", Toast.LENGTH_SHORT).show();
                }
                //endregion
            }
        });

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region Date Picker
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(SubordinateDashboardActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                selectDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                getData(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                SelectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

                //endregion
            }
        });

        actualAvgLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region Open Dialog
                if (reportingCallAvgs != null) {
                    final Dialog d = new Dialog(SubordinateDashboardActivity.this);
                    d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    d.setContentView(R.layout.dialog_actual_average);

                    TextView totalReporting, noOfDays, average;
                    totalReporting = (TextView) d.findViewById(R.id.totalReporting);
                    noOfDays = (TextView) d.findViewById(R.id.noODays);
                    average = (TextView) d.findViewById(R.id.average);
                    Button closeBtn = (Button) d.findViewById(R.id.closeBtn);

                    totalReporting.setText(reportingCallAvgs.get(0).getReportingCount());
                    noOfDays.setText(reportingCallAvgs.get(0).getDatesCount());
                    average.setText(reportingCallAvgs.get(0).getCallAvg());

                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.dismiss();
                        }
                    });

                    d.show();
                }
                //endregion
            }
        });

        pobLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pob.getText().toString().equals("0")) {
                    Intent i = new Intent(SubordinateDashboardActivity.this, POBActivity.class);
                    i.putExtra("function_name", "getEmpTodayPOBDetail");
                    i.putExtra("Type", "Daily");
                    i.putExtra("date", SelectedDate);
                    i.putExtra("EmpId", EmpId);
                    startActivity(i);
                } else {
                    Toast.makeText(SubordinateDashboardActivity.this, "No pob placed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deliveredLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tv_totaldeliverycount.getText().toString().equals("0")) {
                    Intent i = new Intent(SubordinateDashboardActivity.this, POBActivity.class);
                    i.putExtra("function_name", "getEmpMonthPOBDetail");
                    i.putExtra("Type", "Monthly");
                    i.putExtra("date", SelectedDate);
                    i.putExtra("EmpId", EmpId);
                    startActivity(i);
                } else {
                    Toast.makeText(SubordinateDashboardActivity.this, "No pob placed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        approvedLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvapproved.getText().toString().equals("0")) {
                    Intent i = new Intent(SubordinateDashboardActivity.this, POBActivity.class);
                    i.putExtra("function_name", "getEmpMonthPOBDetail");
                    i.putExtra("date", SelectedDate);
                    i.putExtra("Type", "Approved");
                    i.putExtra("EmpId", EmpId);
                    startActivity(i);
                } else {
                    Toast.makeText(SubordinateDashboardActivity.this, "No pob placed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        pendingLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvpending.getText().toString().equals("0")) {
                    Intent i = new Intent(SubordinateDashboardActivity.this, POBActivity.class);
                    i.putExtra("function_name", "getEmpMonthPOBDetail");
                    i.putExtra("date", SelectedDate);
                    i.putExtra("Type", "Pending");
                    i.putExtra("EmpId", EmpId);
                    startActivity(i);
                } else {
                    Toast.makeText(SubordinateDashboardActivity.this, "No pob placed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        declineLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvdecline.getText().toString().equals("0")) {
                    Intent i = new Intent(SubordinateDashboardActivity.this, POBActivity.class);
                    i.putExtra("function_name", "getEmpMonthPOBDetail");
                    i.putExtra("date", SelectedDate);
                    i.putExtra("Type", "Decline");
                    i.putExtra("EmpId", EmpId);
                    startActivity(i);
                } else {
                    Toast.makeText(SubordinateDashboardActivity.this, "No pob placed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        postponeLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvpostpone.getText().toString().equals("0")) {
                    Intent i = new Intent(SubordinateDashboardActivity.this, POBActivity.class);
                    i.putExtra("function_name", "getEmpMonthPOBDetail");
                    i.putExtra("date", SelectedDate);
                    i.putExtra("Type", "Postpone");
                    i.putExtra("EmpId", EmpId);
                    startActivity(i);
                } else {
                    Toast.makeText(SubordinateDashboardActivity.this, "No pob placed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        downloadsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!downloads.getText().toString().equals("0")) {
                    Intent i = new Intent(SubordinateDashboardActivity.this, DownloadsActivity.class);
                    i.putExtra("function_name", "getEmpTodayDownloadDetail");
                    i.putExtra("date", SelectedDate);
                    i.putExtra("Type", "Daily");
                    i.putExtra("EmpId", EmpId);
                    startActivity(i);
                } else {
                    Toast.makeText(SubordinateDashboardActivity.this, "No downloads for today", Toast.LENGTH_SHORT).show();
                }
            }
        });
        monthlyDownloadLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!monthlyDownloads.getText().toString().equals("0")) {
                    Intent i = new Intent(SubordinateDashboardActivity.this, DownloadsActivity.class);
                    i.putExtra("function_name", "getEmpMonthDownloadDetail");
                    i.putExtra("date", SelectedDate);
                    i.putExtra("Type", "Monthly");
                    i.putExtra("EmpId", EmpId);
                    startActivity(i);
                } else {
                    Toast.makeText(SubordinateDashboardActivity.this, "No downloads", Toast.LENGTH_SHORT).show();
                }
            }
        });

        monthlyLeaveLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!monthlyLeaves.getText().toString().equals("0")) {
                    Intent i = new Intent(SubordinateDashboardActivity.this, LeavesActivity.class);
                    i.putExtra("date", SelectedDate);
                    i.putExtra("EmpId", EmpId);
                    startActivity(i);
                } else {
                    Toast.makeText(SubordinateDashboardActivity.this, "No leaves applied", Toast.LENGTH_SHORT).show();
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setContent() {
        Bundle b = getIntent().getExtras();
        EmpId = b.getString("EmpId");
        showProgressDialog();
        SubordinateName = b.getString("SubordinateName");

        toolbartitle = (TextView) findViewById(R.id.title);
        toolbartitle.setText("" + SubordinateName);
        backBtn = (ImageView) findViewById(R.id.backBtn);


        callsPlannedLL = (LinearLayout) findViewById(R.id.callsPlannedLL);
        actualAvgLL = (LinearLayout) findViewById(R.id.actualAvgLL);
        callsPanned = (TextView) findViewById(R.id.tvCallPlanned1);
        targetCall = (TextView) findViewById(R.id.targetCall);
        deviation = (TextView) findViewById(R.id.tvDeviation1);
        expectedAverage = (TextView) findViewById(R.id.tvExpectedAvg1);
        expectedAverage.setText("30.00");
        actuallAverage = (TextView) findViewById(R.id.tvActualAvg1);
        selectDate = (TextView) findViewById(R.id.date);
        downarrow = (TextView) findViewById(R.id.arrow_btn);
        downarrow.setTypeface(MiljonOffer.solidFont);

        monthlyCalls = (TextView) findViewById(R.id.tvMonthlyCall);
        monthlyCallLL = (LinearLayout) findViewById(R.id.monthlyCallLL);

        downloads = (TextView) findViewById(R.id.tvDownload);
        downloadsLL = (LinearLayout) findViewById(R.id.downloadsLL);

        monthpobLL=(LinearLayout) findViewById(R.id.monthpobLL);
        tv_monthpob = (TextView) findViewById(R.id.tv_monthpob);


        monthlyDownloads = (TextView) findViewById(R.id.tvDownloadMonthly);
        monthlyDownloadLL = (LinearLayout) findViewById(R.id.downloadsMonthlyLL);

        pob = (TextView) findViewById(R.id.tvPOB);
        pobLL = (LinearLayout) findViewById(R.id.pobLL);
       /* monthlyPOB = (TextView) findViewById(R.id.tvPOBMonthly);
        monthlyPOBLL = (LinearLayout) findViewById(R.id.pobMonthlyLL);
*/
        deliveredLL = (LinearLayout) findViewById(R.id.deliveredLL);
        tv_totaldeliverycount = (TextView) findViewById(R.id.tv_totaldeliverycount);
        monthlyLeaveLL = (LinearLayout) findViewById(R.id.leavesLL);
        monthlyLeaves = (TextView) findViewById(R.id.tvLeaves);

        tvapproved = (TextView) findViewById(R.id.tvapproved);
        tvpending = (TextView) findViewById(R.id.tvpending);
        tvdecline = (TextView) findViewById(R.id.tvdecline);
        tvpostpone = (TextView) findViewById(R.id.tvpostpone);

        approvedLL = (LinearLayout) findViewById(R.id.approvedLL);
        pendingLL = (LinearLayout) findViewById(R.id.pendingLL);
        declineLL = (LinearLayout) findViewById(R.id.declineLL);
        postponeLL = (LinearLayout) findViewById(R.id.postponeLL);
        apiInterface = ApiUtils.getDashboardData();
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);
        selectDate.setText(formattedDate);
        df = new SimpleDateFormat("yyyy-MM-dd");
        getData(df.format(c));
        SelectedDate = (df.format(c));

    }


    private void getData(String Date) {
        if (Internet.checkConnection(SubordinateDashboardActivity.this)) {
            progress.show();
            Call<DashboardData> call = apiInterface.getDashboardData(function_name,
                    EmpId, Date);
            call.enqueue(new Callback<DashboardData>() {
                @Override
                public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {
                    if (response.isSuccessful()) {
                        if (progress.isShowing()) {
                            progress.dismiss();
                            if (response.body().getError().toString().equals("true")) {
                                Toast.makeText(SubordinateDashboardActivity.this, "Error Occurred ! ", Toast.LENGTH_SHORT).show();
                            } else {

                                //Calls Planned
                                if (response.body().getTotalReportings().size() > 0) {
                                    callsPlannedValue = Integer.valueOf(response.body().getTotalReportings().get(0).getTotalReporting());
                                    callsPanned.setText(response.body().getTotalReportings().get(0).getTotalReporting());
                                    callsPanned.setPaintFlags(callsPanned.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                } else {
                                    callsPlannedValue = 0;
                                }

                                //Daily Target
                                if (response.body().getDailyTargetCalls().size() > 0) {
                                    targetCallsValue = Integer.valueOf(response.body().getDailyTargetCalls().get(0).getTarget_calls());
                                    targetCall.setText(response.body().getDailyTargetCalls().get(0).getTarget_calls());
                                } else {
                                    targetCallsValue = 0;
                                }


                                //Deviation
                                int deviationValue = targetCallsValue - callsPlannedValue;
                                if (deviationValue < 0 || deviationValue == 0) {
                                    deviation.setText("00");
                                } else {
                                    deviation.setText("" + deviationValue);
                                }


                                //Reporting Call
                                if (response.body().getReportingCallAvg() != null) {
                                    if (response.body().getReportingCallAvg().size() > 0) {
                                        reportingCallAvgs = response.body().getReportingCallAvg();
                                        actuallAverage.setText(response.body().getReportingCallAvg().get(0).getCallAvg());
                                        actuallAverage.setPaintFlags(actuallAverage.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                    } else {
                                        reportingCallAvgs = null;
                                    }
                                } else {
                                    reportingCallAvgs = null;
                                }


                                //Reporting Details
                                if (response.body().getReportingDetails() != null) {
                                    if (response.body().getReportingDetails().size() > 0) {
                                        reportingDetails = response.body().getReportingDetails();
                                    } else {
                                        reportingDetails = null;
                                    }
                                } else {
                                    reportingDetails = null;
                                }

                                //Monthly Reporting Details
                                if (response.body().getMonthlyReportingDetails() != null) {
                                    if (response.body().getMonthlyReportingDetails().size() > 0) {
                                        monthlyreportingDetails = response.body().getMonthlyReportingDetails();
                                    } else {
                                        monthlyreportingDetails = null;
                                    }
                                } else {
                                    monthlyreportingDetails = null;
                                }


                                //Today's POB
                                if (response.body().getTodayPOBS() != null
                                        && response.body().getTodayPOBS().get(0).getTodayPOB() != null) {
                                    pob.setText(response.body().getTodayPOBS().get(0).getTodayPOB());
                                    pob.setPaintFlags(pob.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                } else {
                                    pob.setText("0");
                                }

                                //Today's Download
                                if (response.body().getTodayDownloads() != null &&
                                        response.body().getTodayDownloads().get(0).getTodayDownloadCount() != null) {
                                    downloads.setText(response.body().getTodayDownloads().get(0).getTodayDownloadCount());
                                    downloads.setPaintFlags(downloads.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                } else {
                                    downloads.setText("0");
                                }

                                //Monthly Download
                                if (response.body().getMonthlyDownload() != null &&
                                        response.body().getMonthlyDownload().get(0).getMonthlyDownloadCount() != null) {
                                    monthlyDownloads.setText(response.body().getMonthlyDownload().get(0).getMonthlyDownloadCount());
                                    monthlyDownloads.setPaintFlags(monthlyDownloads.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                } else {
                                    monthlyDownloads.setText("0");
                                }
/*
                                //Monthly POB
                                if (response.body().getMonthlyPOBS() != null &&
                                        response.body().getMonthlyPOBS().get(0).getMonthlyPOB() != null) {
                                    tv_totaldeliverycount.setText(response.body().getmONTHLYDELIVEREDPOB().get(0).getMONTHLYPOB());
                           //         monthlyPOB.setText(response.body().getMonthlyPOBS().get(0).getMonthlyPOB());
                             //       monthlyPOB.setPaintFlags(monthlyPOB.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                } else {
                               //     monthlyPOB.setText("0");
                                }*/

//OrdersPOB
                                if (response.body().getmONTHLYAPPROVEDPOB() != null) {
                                    approvedob = response.body().getmONTHLYAPPROVEDPOB();
                                    tvapproved.setText(approvedob.get(0).getMONTHLYPOB());
                                }
                                if (response.body().getmONTHLYPENDINGPOB() != null) {
                                    pendingpob = response.body().getmONTHLYPENDINGPOB();
                                    tvpending.setText(pendingpob.get(0).getMONTHLYPOB());
                                }
                                if (response.body().getmONTHLYCANCELPOB() != null) {
                                    cancelpob = response.body().getmONTHLYCANCELPOB();
                                    tvdecline.setText(cancelpob.get(0).getMONTHLYPOB());
                                }
                                if (response.body().getmONTHLYPOSTPONEPOB() != null) {
                                    postponepob = response.body().getmONTHLYPOSTPONEPOB();
                                    tvpostpone.setText(postponepob.get(0).getMONTHLYPOB());
                                }
                                if (response.body().getmONTHLYDELIVEREDPOB() != null) {
                                    deliverpob = response.body().getmONTHLYDELIVEREDPOB();
                                    tv_totaldeliverycount.setText(deliverpob.get(0).getMONTHLYPOB());
                                }
                                //Monthly Leave
                                if (response.body().getMonthlyLeaves() != null
                                        && response.body().getMonthlyLeaves().get(0).getMonthlyLeaves() != null) {
                                    monthlyLeaves.setText(response.body().getMonthlyLeaves().get(0).getMonthlyLeaves());
                                    monthlyLeaves.setPaintFlags(monthlyLeaves.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                } else {
                                    monthlyLeaves.setText("0");
                                    monthlyLeaves.setPaintFlags(monthlyLeaves.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                }
                                //total pob
                                if(response.body().getmONTHLYAPPROVEDPOB() != null && response.body().getmONTHLYPENDINGPOB() != null
                                        && response.body().getmONTHLYCANCELPOB() != null && response.body().getmONTHLYPOSTPONEPOB() != null
                                        && response.body().getmONTHLYDELIVEREDPOB() != null){

                                    totalpob= Float.valueOf(tvapproved.getText().toString())+ Float.valueOf(tvpending.getText().toString())+
                                            Float.valueOf(tvdecline.getText().toString())+ Float.valueOf(tvpostpone.getText().toString())+
                                            Float.valueOf(tv_totaldeliverycount.getText().toString());
                                    tv_monthpob.setText(String.valueOf(totalpob));
                                }
                                //Monthly Calls
                                if (response.body().getMonthlyReporting() != null
                                        && response.body().getMonthlyReporting().get(0).getTotalReporting() != null) {
                                    monthlyCalls.setText(response.body().getMonthlyReporting().get(0).getTotalReporting());

                                    monthlyCalls.setPaintFlags(monthlyCalls.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                } else {
                                    monthlyCalls.setText("0");
                                }


                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DashboardData> call, Throwable t) {
                    if (progress.isShowing())
                        progress.dismiss();
                    Toast.makeText(SubordinateDashboardActivity.this, "Error Occurred !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            progress.dismiss();
            Toast.makeText(SubordinateDashboardActivity.this, "Internet not Available", Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgressDialog() {
        progress = new ProgressDialog(SubordinateDashboardActivity.this);
        progress.setMax(100);
        progress.setMessage("Your Data is loading Please Wait.......");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SubordinateDashboardActivity.this.finish();
    }
}

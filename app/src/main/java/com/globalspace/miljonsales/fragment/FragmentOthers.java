package com.globalspace.miljonsales.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.service.lat_long_service;
import com.globalspace.miljonsales.adapter.ReportingTypeSpinnerAdapter;
import com.globalspace.miljonsales.model.OtherReporting;
import com.globalspace.miljonsales.model.OtherResportingResponse;
import com.globalspace.miljonsales.permissions.CommandWrapper;
import com.globalspace.miljonsales.permissions.EnableGpsCommand;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOthers extends Fragment {
    private View view;
    private Spinner otherSpinner;
    private EditText commentsEdt;
    private Button submitBtn;
    private SharedPreferences sPref;
    private ProgressDialog progress;
    private ApiInterface apiInterface;
    private ArrayList<OtherReporting> reportingType;
    private double latitude, longitude;
    private String address_latlong, stratitude, strlongitude;

    public FragmentOthers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_others, container, false);
        setContentView();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otherSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Please Select Reporting Type", Toast.LENGTH_SHORT).show();
                }/*else if(commentsEdt.getText().toString().equals("") || commentsEdt.getText().toString().equals(" ")){
                    Toast.makeText(getActivity(), "Please Enter Remarks", Toast.LENGTH_SHORT).show();
                }*/ else {
                    submitData();
                }
            }
        });
        return view;
    }

    private void submitData() {
        //region Item View Click
        progress.show();
        if (EnableGPSIfPossible()) {
            buildAlertMessageNoGps();
        } else {
            Intent in = new Intent(getContext(), lat_long_service.class);
            getActivity().startService(in);
            lat_long_service objects = new lat_long_service(getActivity());

            latitude = objects.getLatitude();
            longitude = objects.getLongitude();

            try {
                address_latlong = objects.getAddress();
            } catch (Exception e) {
                e.printStackTrace();
            }

            stratitude = String.valueOf(latitude);
            strlongitude = String.valueOf(longitude);

            if (!sPref.getString(getActivity().getString(R.string.leave_flag), "").equals("2")) {
                if (Internet.checkConnection(getActivity())) {
                    progress.show();
                    submitReporting(reportingType.get(otherSpinner.getSelectedItemPosition()).getActivityId(), commentsEdt.getText().toString());
                } else {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "please check your internet connection", Toast.LENGTH_LONG).show();
                }
            } else {
                progress.dismiss();
                Toast.makeText(getActivity(), "Sorry you are on leave", Toast.LENGTH_SHORT).show();
            }

        }

        //endregion
    }

    private boolean EnableGPSIfPossible() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            return true;
        }
        return false;
        /*Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        sendBroadcast(intent);*/
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(
                "Your GPS seems to be disabled, click ok to enable it?")
                .setCancelable(false)
                .setPositiveButton("OK",
                        new CommandWrapper(new EnableGpsCommand(getActivity())));
               /* .setNegativeButton("No",
                        new CommandWrapper(new CancelCommand(this)));*/

        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void setContentView() {
        sPref = getActivity().getSharedPreferences(getActivity().getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        otherSpinner = (Spinner) view.findViewById(R.id.othersSpinner);
        commentsEdt = (EditText) view.findViewById(R.id.et_comments);
        submitBtn = (Button) view.findViewById(R.id.btn_submit);
        apiInterface = ApiUtils.getDashboardData();
        setProgressDialog();

        getOtherReportingType();
    }


    private void setProgressDialog() {
        progress = new ProgressDialog(getActivity());
        progress.setMax(100);
        //progress.setTitle("Progress Dialog");
        progress.setMessage("Your Data is loading Please Wait.......");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);

    }

    private void getOtherReportingType() {
        if (Internet.checkConnection(getActivity())) {
            if (isAdded()) {
                progress.show();
                Call<OtherResportingResponse> call = apiInterface.otherThanReporting("otherThanReporting");
                call.enqueue(new Callback<OtherResportingResponse>() {
                    @Override
                    public void onResponse(Call<OtherResportingResponse> call, Response<OtherResportingResponse> response) {
                        if (response.isSuccessful()) {
                            if (progress.isShowing()) {
                                if (response.body().getError().equals("true")) {
                                    progress.dismiss();
                                    Toast.makeText(getActivity(), response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    progress.dismiss();
                                    reportingType = new ArrayList<>();
                                    OtherReporting data = new OtherReporting();
                                    data.setActivityId("0");
                                    data.setActivityType("Select Reporting Type");
                                    reportingType.add(data);
                                    reportingType.addAll(response.body().getOtherReoprting());
                                    otherSpinner.setAdapter(new ReportingTypeSpinnerAdapter(getActivity(), reportingType));
                                }
                            }
                        } else {
                            if (progress.isShowing())
                                progress.dismiss();
                            Toast.makeText(getActivity(), "Error Occurred! " + response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OtherResportingResponse> call, Throwable t) {

                    }
                });
            }
        } else {
            Toast.makeText(getActivity(), "Internet not Available", Toast.LENGTH_SHORT).show();
        }
    }


    private void submitReporting(String activityId, String remark) {
        Call<OtherResportingResponse> call = apiInterface.setOtherThanReporting("setOtherThanReporting", sPref.getString(getString(R.string.employee_id), ""),
                activityId,
                stratitude, strlongitude, address_latlong, remark);

        call.enqueue(new Callback<OtherResportingResponse>() {
            @Override
            public void onResponse(Call<OtherResportingResponse> call, Response<OtherResportingResponse> response) {
                if (response.isSuccessful()) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), response.body().getOtherReoprting().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.body().getOtherReoprting().get(0).getStatus().equals("1")){
                        otherSpinner.setSelection(0);
                        commentsEdt.setText("");
                    }
                } else {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OtherResportingResponse> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

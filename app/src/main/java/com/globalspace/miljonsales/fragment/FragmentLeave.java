package com.globalspace.miljonsales.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.MiljonOffer;
import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.model.Response;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;

public class FragmentLeave extends Fragment {
	
	private SharedPreferences sPref;
	private View v;
	private Button btn_apply;
	private EditText et_reasonforleave;
	private TextView selectfromdate, selecttodate, cal_from, cal_to;
	private LinearLayout ll_fromdate, ll_todate;
	private int mYear, mMonth, mDay, mHour, mMinute;
	private String fromDate, toDate;
	private Date StartDate, EndDate;
	private ApiInterface apiInterface;
	private String function_name = "leaveReport";
	private ProgressDialog progress;
	
	public FragmentLeave() {
		// Required empty public constructor
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		v = inflater.inflate(R.layout.fragment_leave, container, false);
		apiInterface = ApiUtils.getDashboardData();
		sPref = getActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
		selectfromdate = (TextView) v.findViewById(R.id.selectfromdate);
		selecttodate = (TextView) v.findViewById(R.id.selecttodate);
		cal_from = (TextView) v.findViewById(R.id.cal_from);
		cal_to = (TextView) v.findViewById(R.id.cal_to);
		setProgressDialog();
		
		cal_from.setTypeface(MiljonOffer.solidFont);
		cal_to.setTypeface(MiljonOffer.solidFont);
		
		et_reasonforleave = (EditText) v.findViewById(R.id.et_reasonforleave);
		btn_apply = (Button) v.findViewById(R.id.btn_apply);
		ll_fromdate = (LinearLayout) v.findViewById(R.id.ll_fromdate);
		ll_todate = (LinearLayout) v.findViewById(R.id.ll_todate);
		
		ll_fromdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// Get Current Date
				final Calendar c = Calendar.getInstance();
				mYear = c.get(Calendar.YEAR);
				mMonth = c.get(Calendar.MONTH);
				mDay = c.get(Calendar.DAY_OF_MONTH);
				
				
				DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
						new DatePickerDialog.OnDateSetListener() {
							
							@Override
							public void onDateSet(DatePicker view, int year,
							                      int monthOfYear, int dayOfMonth) {
								fromDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
								try {
									StartDate = format.parse(fromDate);
								} catch (ParseException e) {
									Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
								}
								
								selectfromdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
								ll_todate.setClickable(true);
								
							}
						}, mYear, mMonth, mDay);
				datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
				datePickerDialog.show();
				
				
			}
		});
		
		ll_todate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// Get Current Date
				final Calendar c = Calendar.getInstance();
				mYear = c.get(Calendar.YEAR);
				mMonth = c.get(Calendar.MONTH);
				mDay = c.get(Calendar.DAY_OF_MONTH);
				
				
				DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
						new DatePickerDialog.OnDateSetListener() {
							
							@Override
							public void onDateSet(DatePicker view, int year,
							                      int monthOfYear, int dayOfMonth) {
								toDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
								try {
									EndDate = format.parse(toDate);
								} catch (ParseException e) {
									Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
								}
								selecttodate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
								
							}
						}, mYear, mMonth, mDay);
				datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
				datePickerDialog.show();
			}
			
		});
		
		btn_apply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (et_reasonforleave.getText().toString().equals("")) {
					Toast.makeText(getActivity(), "Please enter the reason for leave and proceed", Toast.LENGTH_SHORT).show();
				} else if (selectfromdate.getText().toString().equals("Select From")) {
					Toast.makeText(getActivity(), "Please Select Start Date", Toast.LENGTH_SHORT).show();
					
				} else if (selecttodate.getText().toString().equals("Select To")) {
					Toast.makeText(getActivity(), "Please Select To Date", Toast.LENGTH_SHORT).show();
					
				} else {
					if (StartDate.after(EndDate)) {
						Toast.makeText(getActivity(), "Please select proper end date", Toast.LENGTH_SHORT).show();
					} else {
						setLeaveData(fromDate, toDate, et_reasonforleave.getText().toString());
					}
				}
			}
		});
		
		
		return v;
	}
	
	private void setProgressDialog() {
		progress = new ProgressDialog(getActivity());
		progress.setMax(100);
		// progress.setTitle("Progress Dialog");
		progress.setMessage("Your Data is loading Please Wait.......");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setCancelable(false);
		progress.setCanceledOnTouchOutside(false);
		
	}
	
	
	private void setLeaveData(String StartingDate, String EndDate, String Reason) {
		if (Internet.checkConnection(getActivity())) {
			progress.show();
			Call<Response> call = apiInterface.setLeaveData(function_name,
					sPref.getString(getResources().getString(R.string.employee_id), ""), StartingDate, EndDate, Reason);
			
			call.enqueue(new Callback<Response>() {
				@Override
				public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
					if (progress.isShowing())
						progress.dismiss();
					et_reasonforleave.setText("");
					Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onFailure(Call<Response> call, Throwable t) {
					if (progress.isShowing())
						progress.dismiss();
					Toast.makeText(getActivity(),"Error Occurred !", Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
		}
	}
	
	
}

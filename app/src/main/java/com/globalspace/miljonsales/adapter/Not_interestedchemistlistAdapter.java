package com.globalspace.miljonsales.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.fragment.FragmentNotInterested;
import com.globalspace.miljonsales.service.lat_long_service;
import com.globalspace.miljonsales.activity.RegisterActivity;
import com.globalspace.miljonsales.model.JointWork;
import com.globalspace.miljonsales.model.Login_Response;
import com.globalspace.miljonsales.model.NotInterestedUserList;
import com.globalspace.miljonsales.permissions.CommandWrapper;
import com.globalspace.miljonsales.permissions.EnableGpsCommand;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aravind on 9/8/18.
 */

public class Not_interestedchemistlistAdapter extends RecyclerView.Adapter<Not_interestedchemistlistAdapter.ViewHolder> implements Filterable {
	
	private static final String TAG = "location";
	public Activity mcontext;
	ViewHolder viewHolder;
	List<NotInterestedUserList> notInterestedUserList = new ArrayList<>();
	double longitude; // Longitude
	double latitude; // Latitude
	String chemist_id, address_latlong = "";
	String str_chemist_id, str_chemist_name, stratitude, strlongitude, str_employee_id;
	private FriendFilter mFriendFilter;
	private List<JointWork> jointWorks;
	private SharedPreferences sPref;
	private ProgressDialog progress;
	private CharSequence[] values = {" REPORTING ", " REGISTER CHEMIST "};
	private AlertDialog alertDialog1;
	
	
	public Not_interestedchemistlistAdapter(Activity context,
	                                        List<NotInterestedUserList> notInterestedUserList, String str_employee_id,
	                                        List<JointWork> jointWorks, ProgressDialog progress) {
		this.mcontext = context;
		this.notInterestedUserList = notInterestedUserList;
		this.str_employee_id = str_employee_id;
		this.jointWorks = jointWorks;
		sPref = mcontext.getSharedPreferences(mcontext.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
		this.progress = progress;
	}
	
	// Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// create a layout
		View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.list_item, parent, false);
		
		viewHolder = new ViewHolder(itemLayoutView);
		return viewHolder;
	}
	
	// Called by RecyclerView to display the data at the specified position.
	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
		
		viewHolder.name.setText(notInterestedUserList.get(position).getCHEMISTNAME());
		
		viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (EnableGPSIfPossible()) {
					buildAlertMessageNoGps();
				} else {
					Intent in = new Intent(v.getContext(), lat_long_service.class);
					mcontext.startService(in);
					lat_long_service objects = new lat_long_service(mcontext);
					
					latitude = objects.getLatitude();
					longitude = objects.getLongitude();
					
					try {
						address_latlong = objects.getAddress();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					stratitude = String.valueOf(latitude);
					strlongitude = String.valueOf(longitude);
					
					str_chemist_id = notInterestedUserList.get(position).getChemID();
					str_chemist_name = notInterestedUserList.get(position).getCHEMISTNAME();
					chemist_id = notInterestedUserList.get(position).getChemID();
					String function_name = "notInterestedUser";
					String stockist_id = "null";
					if (!sPref.getString(mcontext.getString(R.string.leave_flag), "").equals("2")) {
						if (Internet.checkConnection(mcontext)) {
							
							AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
							
							builder.setTitle("Select Your Choice");
							
							builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int item) {
									
									switch (item) {
										case 0:
											alertDialog1.dismiss();
											progress.show();
											callDialoguetoReport(stratitude, strlongitude, "notInterestedUser",
													str_employee_id, chemist_id, null,
													str_chemist_name, address_latlong, position);
											break;
										case 1:
											Intent i = new Intent(mcontext, RegisterActivity.class);
											i.putExtra("Identifierflag", "8");
											i.putExtra("ChemistName", notInterestedUserList.get(position).getCHEMISTNAME());
											i.putExtra("ChemistEmail", notInterestedUserList.get(position).getEMAIL());
											i.putExtra("ChemistMobile", notInterestedUserList.get(position).getMOBILE());
											mcontext.startActivity(i);
											alertDialog1.dismiss();
											
											break;
									}
									//  alertDialog1.dismiss();
								}
							});
							alertDialog1 = builder.create();
							alertDialog1.show();
							
							
						} else {
							Toast.makeText(mcontext, "please check your internet connection",
									Toast.LENGTH_LONG).show();
						}
					} else {
						Toast.makeText(mcontext, "Sorry you are on leave", Toast.LENGTH_SHORT).show();
					}
					
				}
			}
		});
		
		
	}
	
	
	//Returns the total number of items in the data set hold by the adapter.
	@Override
	public int getItemCount() {
		return notInterestedUserList.size();
	}
	
	@Override
	public Filter getFilter() {
		if (mFriendFilter == null) {
			mFriendFilter = new FriendFilter(this, notInterestedUserList);
		}
		return mFriendFilter;
	}
	
	private void callDialoguetoReport(String stratitude, String strlongitude, String function_name, String str_employee_id,
	                                  String chemist_id, String stockist_id, String str_chemist_name,
	                                  String address_latlong, final int position) {
		
		
		try {
			final Dialog dialog1 = new Dialog(mcontext);
			// Include dialog.xml file
			dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog1.setCancelable(false);
			dialog1.setCanceledOnTouchOutside(false);
			dialog1.setContentView(R.layout.popup_diaoguelayout);
			TextView name = (TextView) dialog1.findViewById(R.id.textDialog);
			TextView address = (TextView) dialog1.findViewById(R.id.textAddress);
			TextView emailId = (TextView) dialog1.findViewById(R.id.emailId);
			
			TextView tv_ok = (TextView) dialog1.findViewById(R.id.tv_yes);
			TextView tv_no = (TextView) dialog1.findViewById(R.id.tv_no);
			final SwitchCompat jointWork = (SwitchCompat) dialog1.findViewById(R.id.checkbox);
			final Spinner spinner = (Spinner) dialog1.findViewById(R.id.spnrProductQty);
			LinearLayout jointWorkLL = (LinearLayout) dialog1.findViewById(R.id.jointWorkLL);
			
			if (jointWorks.size() > 0) {
				jointWorkLL.setVisibility(View.VISIBLE);
			} else {
				jointWorkLL.setVisibility(View.INVISIBLE);
			}
			
			name.setText(str_chemist_name);
			address.setText(address_latlong);
			emailId.setText(notInterestedUserList.get(position).getEMAIL());
			
			final String get_latitude = stratitude;
			final String get_longitude = strlongitude;
			final String emp_id = str_employee_id;
			final String chem_id = chemist_id;
			final String stock_id = stockist_id;
			final String stradd = address_latlong;
			
			jointWork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						spinner.setVisibility(View.VISIBLE);
					} else {
						spinner.setVisibility(View.GONE);
					}
					
				}
			});
			
			JointWorkSpinnerAdapter adapter = new JointWorkSpinnerAdapter(mcontext, jointWorks);
			spinner.setAdapter(adapter);
			
			
			tv_ok.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					String function_name = "notInterestedUser";
					String stockist_id = "null";
					
					
					String jointWorkId = "null";
					
					if (jointWork.isChecked()) {
						if (spinner.getSelectedItemPosition() != 0) {
							jointWorkId = jointWorks.get(spinner.getSelectedItemPosition()).getJointWorkId();
							
							submitnotinterestedchemist(notInterestedUserList.get(position).getCHEMISTNAME(),
									notInterestedUserList.get(position).getEMAIL(),
									notInterestedUserList.get(position).getMOBILE(), emp_id,
									get_latitude, get_longitude, jointWorkId);
							dialog1.dismiss();
						} else {
							Toast.makeText(mcontext, "Please Select Joint work", Toast.LENGTH_SHORT).show();
						}
					} else {
						
						submitnotinterestedchemist(notInterestedUserList.get(position).getCHEMISTNAME(),
								notInterestedUserList.get(position).getEMAIL(),
								notInterestedUserList.get(position).getMOBILE(), emp_id,
								get_latitude, get_longitude, jointWorkId);
						dialog1.dismiss();
					}
					
					
				}
			});
			tv_no.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog1.dismiss();
				}
			});
			dialog1.show();
			progress.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private void submitnotinterestedchemist(String chemistName, String email_id,
	                                        String mobile_no, String str_employee_id,
	                                        String stratitude, String strlongitude, String jointWorkId) {
		
		String functionname = "notInterestedUser";
		
		Call<Login_Response> call = ApiUtils.submitdatainterface().setnotinterestedchemist(chemistName, email_id,
				mobile_no, functionname, str_employee_id, stratitude, strlongitude, jointWorkId);
		call.enqueue(new Callback<Login_Response>() {
			@Override
			public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
				if (response.isSuccessful()) {
					
					Login_Response getresponse = response.body();
					if (getresponse.getStatus().equals("200")) {
						Toast.makeText(mcontext, "Reporting has been done!!", Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(mcontext, "Something went wrong!", Toast.LENGTH_LONG).show();
					}
				}
				
			}
			
			@Override
			public void onFailure(Call<Login_Response> call, Throwable t) {
				Log.e("failure", t.toString());
				//progress.dismiss();
			}
		});
		
		
	}
	
	
	private void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
		builder.setMessage(
				"Your GPS seems to be disabled, click ok to enable it?")
				.setCancelable(false)
				.setPositiveButton("OK",
						new CommandWrapper(new EnableGpsCommand(mcontext)));
               /* .setNegativeButton("No",
                        new CommandWrapper(new CancelCommand(this)));*/
		
		final AlertDialog alert = builder.create();
		alert.show();
	}
	
	private boolean EnableGPSIfPossible() {
		final LocationManager manager = (LocationManager) mcontext.getSystemService(Context.LOCATION_SERVICE);
		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			
			return true;
		}
		return false;
        /*Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        sendBroadcast(intent);*/
	}
	
	// initializes some private fields to be used by RecyclerView.
	public static class ViewHolder extends RecyclerView.ViewHolder {
		
		public TextView name;
		private TextView version_flag;
		//public CheckBox chk_prod_name;
		
		public ViewHolder(View itemLayoutView) {
			super(itemLayoutView);
			
			name = (TextView) itemLayoutView.findViewById(R.id.pName);
			version_flag = (TextView) itemLayoutView.findViewById(R.id.tv_version);
			version_flag.setVisibility(View.GONE);
			
		}
	}
	
	private class FriendFilter extends Filter {
		
		public List<NotInterestedUserList> mFilteredList;
		private Not_interestedchemistlistAdapter mRecyclerAdapter;
		private List<NotInterestedUserList> mArrayList;
		
		
		private FriendFilter(Not_interestedchemistlistAdapter recyclerAdapter, List<NotInterestedUserList> arrayList) {
			mRecyclerAdapter = recyclerAdapter;
			mArrayList = new ArrayList<>(arrayList);
			mFilteredList = new ArrayList<>();
			
		}
		
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			mFilteredList.clear();
			FilterResults filterResults = new FilterResults();
			if (constraint.length() == 0) {
				mFilteredList.addAll(mArrayList);
			} else {
				final String filterPattern = constraint.toString().toLowerCase().trim();
				
				for (final NotInterestedUserList chemist_info : mArrayList) {
					if (chemist_info.getCHEMISTNAME().toLowerCase().contains(filterPattern)) {
						mFilteredList.add(chemist_info);
					}
				}
			}
			filterResults.values = mFilteredList;
			
			//filterResults.count = mFilteredList.size();
			return filterResults;
		}
		
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			mRecyclerAdapter.notInterestedUserList.clear();
			mRecyclerAdapter.notInterestedUserList.addAll((Collection<? extends NotInterestedUserList>) results.values);
			FragmentNotInterested.totalCount.setText(notInterestedUserList.size()+"");
			mRecyclerAdapter.notifyDataSetChanged();
		}
	}
	
	
}
package com.globalspace.miljonsales.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;
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
import com.globalspace.miljonsales.fragment.FragmentStockistList;
import com.globalspace.miljonsales.service.lat_long_service;
import com.globalspace.miljonsales.model.JointWork;
import com.globalspace.miljonsales.model.Login_Response;
import com.globalspace.miljonsales.model.STOCKIESTINFO;
import com.globalspace.miljonsales.permissions.CommandWrapper;
import com.globalspace.miljonsales.permissions.EnableGpsCommand;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by aravind on 12/7/18.
 */

public class Stockist_List_Adapter extends RecyclerView.Adapter<Stockist_List_Adapter.ViewHolder>
        implements Filterable {

    public Activity activity;
    public String Stockist_name, address;
    ViewHolder viewHolder;
    private List<STOCKIESTINFO> Stockist_Information = new ArrayList<>();
    double longitude; // Longitude
    double latitude; // Latitude
    String str_chemist_id, str_stockist_name;
    String originaladdress = "";
    String stockist_id, address_latlong = "";
    String chemist_id;
    String stratitude, strlongitude;
    String str_employee_id;
    FriendFilter mFriendFilter;
    private SharedPreferences sPref;
    private List<JointWork> jointWorks;
    private ProgressDialog progress;
   // private CharSequence[] values = {" REPORTING ", " STOCK UPDATE "};
    private CharSequence[] values = {" REPORTING "};


    public Stockist_List_Adapter(Activity context, List<STOCKIESTINFO> stockiestinfos, String str_employee_id,
                                 List<JointWork> jointWorks, ProgressDialog progress) {
        this.activity = context;
        this.Stockist_Information = stockiestinfos;
        this.str_employee_id = str_employee_id;
        this.jointWorks = jointWorks;
        sPref = activity.getSharedPreferences(activity.getString(R.string.app_name), Context.MODE_PRIVATE);
        this.progress = progress;
    }


    // Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a layout
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.name.setText(Stockist_Information.get(position).getNAME());
        viewHolder.address.setVisibility(View.VISIBLE);
        viewHolder.address.setText("Address : "+ Stockist_Information.get(position).getaDDRESS());
        viewHolder.version_flag.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fa-solid-900.ttf"));
        if (Stockist_Information.get(position).getVersion_flag() != null) {
            if (Stockist_Information.get(position).getVersion_flag().equals("0")) {
                viewHolder.version_flag.setTextColor(activity.getResources().getColor(R.color.colorRed));
            } else {
                viewHolder.version_flag.setTextColor(activity.getResources().getColor(R.color.colorGreen));
            }
        } else {
            viewHolder.version_flag.setVisibility(View.GONE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progress.show();


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                //region OnClick
               // if (!Stockist_Information.get(position).getVersion_flag().equals("0")) {
                    if (EnableGPSIfPossible()) {
                        progress.dismiss();
                        buildAlertMessageNoGps();
                    } else {
                        Intent in = new Intent(v.getContext(), lat_long_service.class);
                        activity.startService(in);
                        lat_long_service objects = new lat_long_service(activity);

                        latitude = objects.getLatitude();
                        longitude = objects.getLongitude();

                        try {
                            address_latlong = objects.getAddress();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            stratitude = String.valueOf(latitude);
                            strlongitude = String.valueOf(longitude);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        stockist_id = Stockist_Information.get(position).getEMPLOYEEID().toString();
                        str_stockist_name = Stockist_Information.get(position).getNAME().toString();

                        originaladdress = Stockist_Information.get(position).getaDDRESS().toString();

                        if (originaladdress.equals("")) {
                            originaladdress = "NA";
                        }


                        str_chemist_id = "null";

                        if (!sPref.getString(activity.getString(R.string.leave_flag), "").equals("2")) {

                 /*           final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                            builder.setTitle("Select Your Choice");

                            builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int item) {

                                    switch (item) {

                                        case 0:*/
                                            if (Internet.checkConnection(activity)) {
                                                String function_name = "deliveryPersonLocation";
                                                progress.show();
                                                callDialoguetoReport(stratitude, strlongitude, function_name, str_employee_id,
                                                        chemist_id, stockist_id, str_stockist_name, address_latlong, originaladdress, position);
                                           } else {
                                                progress.dismiss();
                                                Toast.makeText(activity, "please check your internet connection",
                                                        Toast.LENGTH_LONG).show();
                                            }
//                                            break;
//
//                                        case 1:
//                                            Intent i = new Intent(activity, StockUpdate.class);
//                                            i.putExtra("StockId", Stockist_Information.get(position).getORGID());
//                                            activity.startActivity(i);
//
//                                    }*/
//                                }
//                            });
//
//                            builder.show();

                        } else {
                            progress.dismiss();
                            Toast.makeText(activity, "Sorry you are on leave", Toast.LENGTH_SHORT).show();
                        }
                    }
               // }
                //endregion



            }

        }, 50);



            }

        });

    }


    //Returns the total number of items in the data set hold by the adapter.
    @Override
    public int getItemCount() {
        return Stockist_Information.size();
    }

    private boolean EnableGPSIfPossible() {
        final LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            return true;
        }
        return false;
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(
                "Your GPS seems to be disabled, click ok to enable it?")
                .setCancelable(false)
                .setPositiveButton("OK",
                        new CommandWrapper(new EnableGpsCommand(activity)));


        final AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public Filter getFilter() {
        if (mFriendFilter == null) {
            mFriendFilter = new FriendFilter(this, Stockist_Information);
        }
        return mFriendFilter;
    }

    private void callDialoguetoReport(String stratitude, String strlongitude,
                                      String function_name, String str_employee_id,
                                      String chemist_id, String stockist_id,
                                      String str_stockist_name, String address_latlong, String originaladdress, int position) {

        final Dialog dialog1 = new Dialog(activity);
        // Include dialog.xml file
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setContentView(R.layout.popup_diaoguelayout);
        TextView name = (TextView) dialog1.findViewById(R.id.textDialog);
        TextView address = (TextView) dialog1.findViewById(R.id.textAddress);

        TextView tv_ok = (TextView) dialog1.findViewById(R.id.tv_yes);
        TextView tv_no = (TextView) dialog1.findViewById(R.id.tv_no);
        TextView emailId = (TextView) dialog1.findViewById(R.id.emailId);

        final SwitchCompat jointWork = (SwitchCompat) dialog1.findViewById(R.id.checkbox);
        final Spinner spinner = (Spinner) dialog1.findViewById(R.id.spnrProductQty);
        LinearLayout jointWorkLL = (LinearLayout) dialog1.findViewById(R.id.jointWorkLL);

        if (jointWorks.size() > 0) {
            jointWorkLL.setVisibility(View.VISIBLE);
        } else {
            jointWorkLL.setVisibility(View.INVISIBLE);
        }
        name.setText(str_stockist_name);
        address.setText(originaladdress);
        emailId.setText(Stockist_Information.get(position).getEmailId());

        final String get_latitude = stratitude;
        final String get_longitude = strlongitude;
        final String emp_id = str_employee_id;
        final String chem_id = chemist_id;
        final String stock_id = stockist_id;
        final String str_address = address_latlong;

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region Ok Button Click
               /* String function_name = "deliveryPersonLocation";
                String stockist_id = "null";*/
                str_chemist_id = "null";
                String jointWorkId = "null";
                String function_name = "deliveryPersonLocation";

                if (jointWork.isChecked()) {
                    if (spinner.getSelectedItemPosition() != 0) {
                        jointWorkId = jointWorks.get(spinner.getSelectedItemPosition()).getJointWorkId();
                        getLocation(get_latitude, get_longitude, function_name, emp_id,
                                chem_id, stock_id, stock_id, str_address, jointWorkId);
                        dialog1.dismiss();
                    } else {
                        Toast.makeText(activity, "Please select Joint Work", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    getLocation(get_latitude, get_longitude, function_name, emp_id,
                            chem_id, stock_id, stock_id, str_address, jointWorkId);
                    dialog1.dismiss();
                }


                //endregion
            }
        });

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

        JointWorkSpinnerAdapter adapter = new JointWorkSpinnerAdapter(activity, jointWorks);
        spinner.setAdapter(adapter);

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog1.dismiss();
            }
        });
        progress.dismiss();
        dialog1.show();
    }

    private void getLocation(String get_latitude, String get_longitude,
                             String function_name, String emp_id, String chem_id,
                             String stockist_id, String stock_id, String str_address, String jointWorkId) {

        ApiUtils.getInstance().getlocation(get_latitude, get_longitude, function_name,
                emp_id, chem_id, stockist_id, str_address, jointWorkId, "MATM_7",
                new ApiUtils.ILoginCallback() {
                    @Override
                    public void onSuccessLogin(Login_Response response) {
                        if (response != null) {
                            if (response.getStatus().equals("401")) {
                                Toast.makeText(activity, response.getPlaceName() + "please check GPRS is on", Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            } else {
                                Toast.makeText(activity, " Reporting has been done", Toast.LENGTH_LONG).show();
                                progress.dismiss();

                            }
                        }

                    }

                    @Override
                    public void onFailure(String failureMessage) {

                        String message = failureMessage;

                    }
                });

    }

    // initializes some private fields to be used by RecyclerView.
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView address;
        private TextView version_flag;
        //public CheckBox chk_prod_name;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            name = (TextView) itemLayoutView.findViewById(R.id.pName);
            address = (TextView) itemLayoutView.findViewById(R.id.address);
            version_flag = (TextView) itemLayoutView.findViewById(R.id.tv_version);

        }
    }

    private class FriendFilter extends Filter {

        public List<STOCKIESTINFO> mFilteredList;
        private Stockist_List_Adapter mRecyclerAdapter;
        private List<STOCKIESTINFO> mArrayList;


        private FriendFilter(Stockist_List_Adapter recyclerAdapter, List<STOCKIESTINFO> arrayList) {
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

                for (final STOCKIESTINFO stockist_info : mArrayList) {
                    if (stockist_info.getNAME().toLowerCase().contains(filterPattern)) {
                        mFilteredList.add(stockist_info);
                    }
                }
            }

            filterResults.values = mFilteredList;

            //filterResults.count = mFilteredList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mRecyclerAdapter.Stockist_Information.clear();
            mRecyclerAdapter.Stockist_Information.addAll((Collection<? extends STOCKIESTINFO>) results.values);
            FragmentStockistList.stockistCount.setText(String.valueOf(Stockist_Information.size()));
            mRecyclerAdapter.notifyDataSetChanged();
        }
    }


}



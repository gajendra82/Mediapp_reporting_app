package com.globalspace.miljonsales.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
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

import com.globalspace.miljonsales.activity.pobActivity;
import com.globalspace.miljonsales.fragment.FragmentChemistList;
import com.globalspace.miljonsales.fragment.FragmentReporting;
import com.globalspace.miljonsales.model.UpdateLatLong;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.RetrofitClient;
import com.globalspace.miljonsales.service.LocationAddress;
import com.globalspace.miljonsales.service.lat_long_service;
import com.globalspace.miljonsales.model.ChemistInfo;
import com.globalspace.miljonsales.model.JointWork;
import com.globalspace.miljonsales.model.Login_Response;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.permissions.CommandWrapper;
import com.globalspace.miljonsales.permissions.EnableGpsCommand;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.service.updatedlatlongservice;
import com.globalspace.miljonsales.utils.Internet;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.globalspace.miljonsales.utils.Constants.login_url;


/**
 * Created by aravind on 10/7/18.
 */

public class Chemist_List_Adapter extends RecyclerView.Adapter<Chemist_List_Adapter.ViewHolder>
        implements Filterable {
    //
    private static final String TAG = "location";
    public static Activity mcontext;
    private static FragmentReporting fragment;
    private FragmentTransaction transaction;

    public List<ChemistInfo> events_filtered = new ArrayList<ChemistInfo>();
    ViewHolder viewHolder;
    static List<ChemistInfo> Chemist_Information = new ArrayList<>();
    List<ChemistInfo> allChemist_Information = new ArrayList<>();
    List<ChemistInfo> filtered_names = new ArrayList<>();
    static String str_chemist_id;
    static String str_chemist_name;
    static String stratitude;
    static String strlongitude;
    static double longitude; // Longitude
    static double latitude; // Latitude
    static String str_employee_id;
    static String chemist_id;
    static String address_latlong = "";
    static String originaladdress = "";
    FriendFilter mFriendFilter;
    private static SharedPreferences sPref;
    private String selectedId;
    private static List<JointWork> jointWorks;
    private static ProgressDialog progress;
    private static String rep_type;
    static ApiInterface apiService;
    static Dialog dialog_loc;
    static Dialog dialog_noloc;
    String From;
    static int selectedposition;
    static boolean iscallreportcall ;
    static updatedlatlongservice selectedobjects;
    static lat_long_service latLongService;
    updatedlatlongservice myService;

    static TextView tvCurrentAddress;
    static TextView tvCurrentAddressnotmatch;
    static TextView tvAddress;
    static TextView tvAddressnotmatch;

    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    public static Location mCurrentLocation;
    private String mLastUpdateTime;
    private Boolean mRequestingLocationUpdates;
    static String currentAddress = "";
    static String lastupdatedAddress = "";
    private static Boolean isitemclicked;


    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 0;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 0;

    private static final int REQUEST_CHECK_SETTINGS = 100;


    public Chemist_List_Adapter(Activity context, List<ChemistInfo> chemist_information, String str_employee_id,
                                List<JointWork> jointWorks, ProgressDialog progress, String rep_type, String From) {
        this.mcontext = context;
        this.Chemist_Information = chemist_information;
        this.allChemist_Information = chemist_information;
        this.str_employee_id = str_employee_id;
        sPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        this.jointWorks = jointWorks;
        this.progress = progress;
        this.progress.setCanceledOnTouchOutside(true);
        this.rep_type = rep_type;
        this.From = From;
        latLongService = new lat_long_service(
                mcontext);

        apiService = RetrofitClient.getClient(login_url).create(ApiInterface.class);

        init(mcontext);
        transaction = mcontext.getFragmentManager().beginTransaction();
        iscallreportcall = false;

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

    //Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        try {
            viewHolder.name.setText(Chemist_Information.get(position).getFSTNAME());
            if (From.equals("Reporting")) {
                viewHolder.address.setVisibility(View.VISIBLE);
                viewHolder.address.setText("Address : " + Chemist_Information.get(position).getaDDRESS());
            }else{
                viewHolder.address.setVisibility(View.GONE);
            }
            viewHolder.version_flag.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "fa-solid-900.ttf"));

            if (Chemist_Information.get(position).getVersion_flag() != null) {
                if (Chemist_Information.get(position).getVersion_flag().equals("0")) {
                    viewHolder.version_flag.setTextColor(Color.parseColor("#DD0000"));
                } else {
                    viewHolder.version_flag.setTextColor(Color.parseColor("#008000"));
                }
            } else {
                viewHolder.version_flag.setVisibility(View.GONE);
            }


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //region Item View Click
                    progress.show();


                    Intent in = new Intent(mcontext, pobActivity.class);
                    in.putExtra("SESSION_ID",Chemist_Information.get(position).getMEMPLOYEEID());
                    in.putExtra("Chem_org_id",Chemist_Information.get(position).getCHEMISTID());
                    mcontext.startActivity(in);
                    progress.dismiss();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                "com.globalspace.miljonsales", null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mcontext.startActivity(intent);
    }


    private ServiceConnection mConnection = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            updatedlatlongservice.LocalBinder binder = (updatedlatlongservice.LocalBinder) service;
            myService = binder.getServiceInstance(); //Get
            myService.registerClient(mcontext); //Activity register in the service as client for callabcks!

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    //Returns the total number of items in the data set hold by the adapter.
    @Override
    public int getItemCount() {
        return Chemist_Information.size();
    }

    @Override
    public Filter getFilter() {
        if (mFriendFilter == null) {
            mFriendFilter = new FriendFilter(this, Chemist_Information);
        }
        return mFriendFilter;
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

    private static void callDialoguetoReport(String stratitude, String strlongitude,
                                             String function_name, String str_employee_id,
                                             String chemist_id,
                                             String stockist_id, String str_chemist_name,
                                             String address_latlong, String originaladdress, int position, final Activity mcontext) {


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
            jointWorkLL.setVisibility(View.GONE);
        }

        name.setText(str_chemist_name);
        address.setText(originaladdress);
        emailId.setText(Chemist_Information.get(position).getEmailId());
        final String get_latitude = stratitude;
        final String get_longitude = strlongitude;
        final String emp_id = str_employee_id;
        final String chem_id = chemist_id;
        final String stock_id = stockist_id;
        final String str_address = address_latlong;


        jointWork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spinner.setVisibility(View.VISIBLE);
                } else {
                    spinner.setVisibility(View.INVISIBLE);
                }

            }
        });

        JointWorkSpinnerAdapter adapter = new JointWorkSpinnerAdapter(mcontext, jointWorks);
        spinner.setAdapter(adapter);


        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String function_name = "deliveryPersonLocation";
                String stockist_id = "null";
                String jointWorkId = "null";

                if (jointWork.isChecked()) {
                    if (spinner.getSelectedItemPosition() != 0) {
                        jointWorkId = jointWorks.get(spinner.getSelectedItemPosition()).getJointWorkId();
                        getLocation(get_latitude, get_longitude, function_name, emp_id,
                                chem_id, stockist_id, stock_id, str_address, jointWorkId);
                        dialog1.dismiss();
                    } else {
                        Toast.makeText(mcontext, "Please Select Joint Work", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    getLocation(get_latitude, get_longitude, function_name, emp_id,
                            chem_id, stockist_id, stock_id, str_address, jointWorkId);
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
        progress.dismiss();
        dialog1.show();


    }


    private static void getLocation(String get_latitude, String get_longitude, String function_name,
                                    String emp_id, String chem_id, String stockist_id,
                                    String stock_id, String address_latlong, String jointWorkId) {

        ApiUtils.getInstance().getlocation(get_latitude, get_longitude, function_name,
                emp_id, chem_id, stockist_id, address_latlong, jointWorkId, rep_type,
                new ApiUtils.ILoginCallback() {
                    @Override
                    public void onSuccessLogin(Login_Response response) {
                        //response.
                        if (response != null) {
                            if (response.getStatus().equals("401")) {
                                Toast.makeText(mcontext, response.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {

                                /*Toast.makeText(activity, response.getMessage().toString()
                                        , Toast.LENGTH_LONG).show();*/
                                Toast.makeText(mcontext, " Reporting has been done", Toast.LENGTH_LONG).show();
                                iscallreportcall = false;
                            }
                        }

                    }

                    @Override
                    public void onFailure(String failureMessage) {

                        String message = failureMessage;

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

 /*   @Override
    public void updateClient(double latitude, double longitude) {
        validtreporting(selectedposition,selectedobjects,Chemist_Information,mcontext);
    }*/


    //initializes some private fields to be used by RecyclerView.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView address;
        private TextView version_flag;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            name = (TextView) itemLayoutView.findViewById(R.id.pName);
            address = (TextView) itemLayoutView.findViewById(R.id.address);
            version_flag = (TextView) itemLayoutView.findViewById(R.id.tv_version);

        }
    }

    private class FriendFilter extends Filter {

        public List<ChemistInfo> mFilteredList;
        private Chemist_List_Adapter mRecyclerAdapter;
        private List<ChemistInfo> mArrayList;


        private FriendFilter(Chemist_List_Adapter recyclerAdapter, List<ChemistInfo> arrayList) {
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

                for (final ChemistInfo chemist_info : mArrayList) {
                    if (chemist_info.getFSTNAME().toLowerCase().contains(filterPattern)) {
                        mFilteredList.add(chemist_info);
                    }
                }
            }

            filterResults.values = mFilteredList;
            filterResults.count = mFilteredList.size();

            //filterResults.count = mFilteredList.size();
            return filterResults;
        }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            mRecyclerAdapter.Chemist_Information.clear();
            mRecyclerAdapter.Chemist_Information.addAll((Collection<? extends ChemistInfo>) filterResults.values);
            if (From.equals("Reporting")) {
                FragmentChemistList.chemistCount.setText(String.valueOf(Chemist_Information.size()));
            }


            notifyDataSetChanged();
        }
    }

    public static void validtreporting(final double latitude, final double longitude) {


        stratitude = String.valueOf(latitude);
        strlongitude = String.valueOf(longitude);

        str_chemist_id = Chemist_Information.get(selectedposition).getCHEMISTID();
        str_chemist_name = Chemist_Information.get(selectedposition).getFSTNAME();
        chemist_id = Chemist_Information.get(selectedposition).getCHEMISTID();
        originaladdress = Chemist_Information.get(selectedposition).getaDDRESS();

        if (originaladdress.equals("")) {
            originaladdress = "NA";
        }
        String function_name = "deliveryPersonLocation";
        String stockist_id = "null";

        //mychanges
        if (!sPref.getString(mcontext.getString(R.string.leave_flag), "").equals("2")) {
            if (Internet.checkConnection(mcontext)) {
                progress.show();

                if (Chemist_Information.get(selectedposition).getLatitude() != null &&
                        Chemist_Information.get(selectedposition).getLongitude() != null) {




                    float[] results = new float[1];
                    Location.distanceBetween(latitude, longitude, Double.valueOf(Chemist_Information.get(selectedposition).getLatitude()),
                            Double.valueOf(Chemist_Information.get(selectedposition).getLongitude()), results);

                    float distanceInMeters = results[0];

                    boolean isWithin1km = distanceInMeters < 500;


                    if (isWithin1km) {

                        if (!Chemist_Information.get(selectedposition).getLat_long_flag().equals("0")) {
                            progress.dismiss();
                            iscallreportcall = true;
                            LocationAddress locationAddress = new LocationAddress();
                            locationAddress.getAddressFromLocation(latitude, longitude,
                                    mcontext, new GeocoderHandler());


                        } else {
                            progress.dismiss();
                            LocationAddress locationAddress = new LocationAddress();
                            locationAddress.getAddressFromLocation(latitude, longitude,
                                    mcontext, new GeocoderHandler());


                            dialog_loc = new Dialog(mcontext);
                            dialog_loc.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog_loc.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            dialog_loc.setContentView(R.layout.dialog_location);
                            dialog_loc.show();
                            TextView yes = (TextView) dialog_loc.findViewById(R.id.tv_yesloc);
                            TextView no = (TextView) dialog_loc.findViewById(R.id.tv_noloc);
                            tvAddress = (TextView) dialog_loc.findViewById(R.id.tvAddress);
                            tvCurrentAddress = (TextView) dialog_loc.findViewById(R.id.tvcurrentAddress);
                            TextView tvname = (TextView) dialog_loc.findViewById(R.id.tvname);
                            TextView tvemailId = (TextView) dialog_loc.findViewById(R.id.tvemailId);
                            //1aug2019
                             tvAddress.setText(Chemist_Information.get(selectedposition).getaDDRESS());
                            tvname.setText(Chemist_Information.get(selectedposition).getFSTNAME());
                            tvemailId.setText(Chemist_Information.get(selectedposition).getEmailId());
                            stratitude = String.valueOf(latitude);
                            strlongitude = String.valueOf(longitude);

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    stratitude = String.valueOf(latitude);
                                    strlongitude = String.valueOf(longitude);
                                    //send this latlong on server
                                    updateLatLong(stratitude, strlongitude, str_chemist_id, selectedposition, Chemist_Information, mcontext);
                                    dialog_loc.dismiss();

                                }
                            });
                            no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog_loc.dismiss();
                                }
                            });
                            // Toast.makeText(mcontext, "not match location", Toast.LENGTH_SHORT).show();

                        }


                    } else if (!isWithin1km) {

                        if (Chemist_Information.get(selectedposition).getLat_long_flag().equals("0")) {

                            progress.dismiss();
                            LocationAddress locationAddress = new LocationAddress();
                            locationAddress.getAddressFromLocation(latitude, longitude,
                                    mcontext, new GeocoderHandler());


                            dialog_loc = new Dialog(mcontext);
                            dialog_loc.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog_loc.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            dialog_loc.setContentView(R.layout.dialog_location);
                            dialog_loc.show();
                            TextView yes = (TextView) dialog_loc.findViewById(R.id.tv_yesloc);
                            TextView no = (TextView) dialog_loc.findViewById(R.id.tv_noloc);
                            tvAddress = (TextView) dialog_loc.findViewById(R.id.tvAddress);
                            tvCurrentAddress = (TextView) dialog_loc.findViewById(R.id.tvcurrentAddress);
                            TextView tvname = (TextView) dialog_loc.findViewById(R.id.tvname);
                            TextView tvemailId = (TextView) dialog_loc.findViewById(R.id.tvemailId);
                            //1aug2019
                             tvAddress.setText(Chemist_Information.get(selectedposition).getaDDRESS());
                            tvname.setText(Chemist_Information.get(selectedposition).getFSTNAME());
                            tvemailId.setText(Chemist_Information.get(selectedposition).getEmailId());

                            stratitude = String.valueOf(latitude);
                            strlongitude = String.valueOf(longitude);

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    stratitude = String.valueOf(latitude);
                                    strlongitude = String.valueOf(longitude);
                                    //send this latlong on server
                                    updateLatLong(stratitude, strlongitude, str_chemist_id, selectedposition, Chemist_Information, mcontext);
                                    dialog_loc.dismiss();
                                }
                            });
                            no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog_loc.dismiss();
                                }
                            });
                            // Toast.makeText(mcontext, "not match location", Toast.LENGTH_SHORT).show();

                        } else {
                            //  progress.dismiss();
                            progress.dismiss();
                            LocationAddress locationAddress = new LocationAddress();
                            locationAddress.getAddressFromLocation(latitude, longitude,
                                    mcontext, new GeocoderHandler());



                            dialog_noloc = new Dialog(mcontext);
                            dialog_noloc.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog_noloc.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            dialog_noloc.setContentView(R.layout.dialog_notlocationmatch);
                            dialog_noloc.show();
                            TextView ok = (TextView) dialog_noloc.findViewById(R.id.tv_ok);
                            tvAddressnotmatch = (TextView) dialog_noloc.findViewById(R.id.tvadress);
                            tvCurrentAddressnotmatch = (TextView) dialog_noloc.findViewById(R.id.tv_currentAddress);

                            TextView tvname = (TextView) dialog_noloc.findViewById(R.id.tvnames);
                            TextView tvemailId = (TextView) dialog_noloc.findViewById(R.id.tvemailID);
                            //1aug2019
                             tvAddressnotmatch.setText(Chemist_Information.get(selectedposition).getaDDRESS());
                            tvname.setText(Chemist_Information.get(selectedposition).getFSTNAME());
                            tvemailId.setText(Chemist_Information.get(selectedposition).getEmailId());


                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog_noloc.dismiss();
                                }
                            });
                            //  Toast.makeText(mcontext, "Location does not match", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else if (Chemist_Information.get(selectedposition).getLatitude() == null &&
                        Chemist_Information.get(selectedposition).getLongitude() == null) {


                    //  if (Chemist_Information.get(selectedposition).getLat_long_flag().equals("0")) {

                    progress.dismiss();
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude,
                            mcontext, new GeocoderHandler());


                    dialog_loc = new Dialog(mcontext);
                    dialog_loc.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog_loc.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog_loc.setContentView(R.layout.dialog_location);
                    dialog_loc.show();
                    TextView yes = (TextView) dialog_loc.findViewById(R.id.tv_yesloc);
                    TextView no = (TextView) dialog_loc.findViewById(R.id.tv_noloc);
                    tvAddress = (TextView) dialog_loc.findViewById(R.id.tvAddress);
                    tvCurrentAddress = (TextView) dialog_loc.findViewById(R.id.tvcurrentAddress);
                    TextView tvname = (TextView) dialog_loc.findViewById(R.id.tvname);
                    TextView tvemailId = (TextView) dialog_loc.findViewById(R.id.tvemailId);
                    //1aug2019

                    tvAddress.setText(Chemist_Information.get(selectedposition).getaDDRESS());
                    tvname.setText(Chemist_Information.get(selectedposition).getFSTNAME());
                    tvemailId.setText(Chemist_Information.get(selectedposition).getEmailId());

                    stratitude = String.valueOf(latitude);
                    strlongitude = String.valueOf(longitude);


                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            stratitude = String.valueOf(latitude);
                            strlongitude = String.valueOf(longitude);
                            //send this latlong on server
                            updateLatLong(stratitude, strlongitude, str_chemist_id, selectedposition, Chemist_Information, mcontext);
                            dialog_loc.dismiss();
                            Toast.makeText(mcontext, "null location updatedd", Toast.LENGTH_SHORT).show();


                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog_loc.dismiss();
                        }
                    });

      /*              } else {
                        progress.dismiss();
                        LocationAddress locationAddress = new LocationAddress();
                        locationAddress.getAddressFromLocation(latitude, longitude,
                                mcontext, new GeocoderHandler());

                        dialog_noloc = new Dialog(mcontext);
                        dialog_noloc.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog_noloc.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog_noloc.setContentView(R.layout.dialog_notlocationmatch);
                        dialog_noloc.show();
                        TextView ok = (TextView) dialog_noloc.findViewById(R.id.tv_ok);
                        TextView tvAddress = (TextView) dialog_noloc.findViewById(R.id.tvadress);
                        TextView tvname = (TextView) dialog_noloc.findViewById(R.id.tvnames);
                        tvCurrentAddressnotmatch = (TextView) dialog_noloc.findViewById(R.id.tv_currentAddress);
                        TextView tvemailId = (TextView) dialog_noloc.findViewById(R.id.tvemailID);
                        //1aug2019
                        tvAddress.setText(Chemist_Information.get(selectedposition).getaDDRESS());
                        tvname.setText(Chemist_Information.get(selectedposition).getFSTNAME());
                        tvemailId.setText(Chemist_Information.get(selectedposition).getEmailId());

                        stratitude = String.valueOf(latitude);
                        strlongitude = String.valueOf(longitude);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog_noloc.dismiss();
                            }
                        });

                    }*/

                }


            } else {
                progress.dismiss();
                Toast.makeText(mcontext, "please check your internet connection", Toast.LENGTH_LONG).show();
            }
        } else {
            progress.dismiss();
            Toast.makeText(mcontext, "Sorry you are on leave", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(mcontext,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    private void init(final Activity mcontext) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mcontext);
        mSettingsClient = LocationServices.getSettingsClient(mcontext);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                //   activity.updateClient(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());

                if (mCurrentLocation != null) {

                    if (isitemclicked) {


                        validtreporting(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                        isitemclicked = false;
                    }

                }
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();


        // startLocationUpdates(mcontext);
    }

    private void startLocationUpdates(final Activity mcontext) {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(mcontext, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                        //   Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());
                        Log.i("TAG", "All location settings are satisfied.");


                        // updateLocationUI();
                    }
                })
                .addOnFailureListener(mcontext, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i("TAG", "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(mcontext, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i("TAG", "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e("TAG", errorMessage);

                                //  Toast.makeText(updatedlatlongservice.this.mcontext, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        //   updateLocationUI();
                    }
                });
    }


    public static void updateLatLong(final String lat, final String longitutde, String sessionid, final int posi, final List<ChemistInfo> Chemist_Information, final Activity mcontext) {
        FragmentReporting.progress.show();
        Call<UpdateLatLong> call = apiService.updateLatLong("setLatLongDataUpdation", lat, longitutde, sessionid);
        call.enqueue(new Callback<UpdateLatLong>() {

            @Override
            public void onResponse(Call<UpdateLatLong> call, Response<UpdateLatLong> response) {
                UpdateLatLong updateLatLong = response.body();
                if (response.isSuccessful()) {
                    if (updateLatLong.getStatus().equals("200")) {
                      TextView   refreshBtn = (TextView) mcontext.findViewById(R.id.refreshBtn);
                      refreshBtn.performClick();
                        /*fragment = new FragmentReporting();
                        mcontext.getFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();*/
                       /* Chemist_Information.get(posi).setLat_long_flag("1");
                        Chemist_Information.get(posi).setLatitude(lat);
                        Chemist_Information.get(posi).setLongitude(longitutde);*/
                        Toast.makeText(mcontext, updateLatLong.getMessage(), Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(mcontext, "Your Latitude and Longitude already exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateLatLong> call, Throwable t) {
                Toast.makeText(mcontext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void getcurrentlocation() {
        Location location = latLongService.getLocation(mcontext);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude,
                    mcontext, new GeocoderHandler());
        }
    }

    public static class GeocoderHandler extends Handler {


        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            String zipcode, city, state;
            String function_name = "deliveryPersonLocation";
            String stockist_id = "null";
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    zipcode = bundle.getString("zipcode");
                    city = bundle.getString("city");
                    state = bundle.getString("state");
                    break;
                default:
                    locationAddress = null;
                    zipcode = null;
                    city = null;
                    state = null;
            }

            currentAddress = locationAddress;


            if (iscallreportcall) {


                callDialoguetoReport(stratitude, strlongitude, function_name, str_employee_id, chemist_id, stockist_id,
                        str_chemist_name, currentAddress, originaladdress, selectedposition, mcontext);


                iscallreportcall = false;
            } else {
                if (currentAddress.equals("")) {

                    tvCurrentAddress.setText("Could not fetch current location! Try again");
                } else {
                    try {
                        tvCurrentAddress.setText(currentAddress);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        tvCurrentAddressnotmatch.setText(currentAddress);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }




}


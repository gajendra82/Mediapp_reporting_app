package com.globalspace.miljonsales.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.model.ChemistInfo;
import com.globalspace.miljonsales.model.JointWork;
import com.globalspace.miljonsales.model.Login_Response;
import com.globalspace.miljonsales.model.STOCKIESTINFO;
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

public class FragmentReporting extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View v;
    private SharedPreferences sPref;
    public static ProgressDialog progress;
    private ApiInterface apiInterface;
    private ViewPagerAdapter adapter;
    private List<ChemistInfo> chemistData = new ArrayList<>();
    private List<STOCKIESTINFO> stockistData = new ArrayList<>();
    private List<JointWork> jointWorks = new ArrayList<>();
    private TextView refreshBtn;
    FragmentManager fm;


    public FragmentReporting() {
        // Required empty public constructor
    }

    //region Extra
/*	public static FragmentReporting newInstance(String param1, String param2) {
		FragmentReporting fragment = new FragmentReporting();
		Bundle args = new Bundle();
		args.putString("fm", param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}*/

   /* public static Fragment newInstance(FragmentManager supportFragmentManager) {
        FragmentReporting fragment = new FragmentReporting();
        Bundle args = new Bundle();
        args.putSerializable("fm", (Serializable) supportFragmentManager);
   //     args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
            fm = (FragmentManager) getArguments().getSerializable("fm");
		}
	}*/

    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_reporting, container, false);
        setContent();
        getData();
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //getData();
    }

    private void setContent() {

        viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabLayout);
        setProgressDialog();
        apiInterface = ApiUtils.getWalletData();
        sPref = getActivity().getSharedPreferences(getActivity().getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        tabLayout.addTab(tabLayout.newTab().setText("Chemist"));
        tabLayout.addTab(tabLayout.newTab().setText("Stockist"));
        adapter = new ViewPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        refreshBtn = (TextView) getActivity().findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getData();
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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

    private void getData() {
        if (Internet.checkConnection(getActivity())) {
            if (isAdded())
                progress.show();

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c);

            Call<Login_Response> call = apiInterface.getWalletdetail(sPref.getString(getString(R.string.Username), ""),
                    sPref.getString(getString(R.string.Password), ""),
                    sPref.getString(getString(R.string.token_regid), ""), get_IMEI(getContext()), "SalesPersonReporting",
                    formattedDate);
            call.enqueue(new Callback<Login_Response>() {
                @Override
                public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                    if (response.isSuccessful()) {
                        if (progress.isShowing()) {
                            progress.dismiss();
                            if (response.body().getError().equals("true")) {
                                Toast.makeText(getActivity(), response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                if (response.body().getCHMIESTINFO().size() > 0) {
                                    chemistData = response.body().getCHMIESTINFO();
                                }

                                if (response.body().getSTOCKIESTINFO().size() > 0) {
                                    stockistData = response.body().getSTOCKIESTINFO();
                                }
                                if (response.body().getJointWorks() != null) {
                                    if (response.body().getJointWorks().size() > 0) {
                                        JointWork jw = new JointWork();
                                        jw.setJointWorkId("0");
                                        jw.setJointWorkName("Select");
                                        jointWorks.add(jw);
                                        jointWorks.addAll(response.body().getJointWorks());
                                    }
                                } else {
                                    jointWorks = new ArrayList<>();
                                }
                                DataHolder.setData(chemistData);
                                DataHolder_stockist.setData(stockistData);
                                DataHolder_jointWork.clearData();
                                DataHolder_jointWork.setData(jointWorks);

                                sPref.edit().putString(getResources().getString(R.string.leave_flag), response.body().getLeaveStatus()).commit();

                                viewPager.setAdapter(adapter);
                            }
                        }
                    } else {
                        if (progress.isShowing())
                            progress.dismiss();
                        Toast.makeText(getActivity(), "Error Occurred! " + response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Login_Response> call, Throwable t) {
                    if (progress.isShowing())
                        progress.dismiss();
                    Toast.makeText(getActivity(), "Error Occurred! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getActivity(), "Internet not available", Toast.LENGTH_SHORT).show();
        }

    }

    public static String get_IMEI(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }

        return deviceId;
    }

 /*   public String get_IMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        return telephonyManager.getDeviceId();
    }*/

    //region View Pager Adapter
    public class ViewPagerAdapter extends FragmentPagerAdapter {

        int tabcount;
        int flag;

        public ViewPagerAdapter(FragmentManager fm, int tabcount, int flag) {
            super(fm);
            this.tabcount = tabcount;
            this.flag = flag;
        }

        public ViewPagerAdapter(FragmentManager fm, int tabcount) {
            super(fm);
            this.tabcount = tabcount;

        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FragmentChemistList();

                case 1:
                    return new FragmentStockistList();

                default:
                    return null;
            }


        }

        @Override
        public int getCount() {
            return tabcount;
        }


        public int tabposition() {
            return getCount();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }


    }
    //endregion


    public enum DataHolder {
        INSTANCE;

        private List<ChemistInfo> mObjectList;
        private List<STOCKIESTINFO> mObjectList_stockiest;

        public static boolean hasData() {
            return INSTANCE.mObjectList != null;
        }

        public static void setData(final List<ChemistInfo> objectList) {
            INSTANCE.mObjectList = objectList;
        }

        public static List<ChemistInfo> getData() {
            final List<ChemistInfo> retList = INSTANCE.mObjectList;
            INSTANCE.mObjectList = null;
            return retList;
        }

    }

    public enum DataHolder_stockist {
        INSTANCE;
        private List<STOCKIESTINFO> mObjectList_stockiest;

        public static boolean hasData() {
            return INSTANCE.mObjectList_stockiest != null;
        }

        public static void setData(final List<STOCKIESTINFO> objectList) {
            INSTANCE.mObjectList_stockiest = objectList;
        }

        public static List<STOCKIESTINFO> getData() {
            final List<STOCKIESTINFO> retList = INSTANCE.mObjectList_stockiest;
            INSTANCE.mObjectList_stockiest = null;
            return retList;
        }

    }

    public enum DataHolder_jointWork {
        INSTANCE;
        private List<JointWork> mObjectList_stockiest;

        public static boolean hasData() {
            return INSTANCE.mObjectList_stockiest != null;
        }

        public static void setData(final List<JointWork> objectList) {
            INSTANCE.mObjectList_stockiest = objectList;
        }

        public static List<JointWork> getData() {
            final List<JointWork> retList = INSTANCE.mObjectList_stockiest;
            return retList;
        }

        public static void clearData() {
            INSTANCE.mObjectList_stockiest = null;
        }

    }


}

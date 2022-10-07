package com.globalspace.miljonsales.fragment;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.MiljonOffer;
import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.adapter.Not_interestedchemistlistAdapter;
import com.globalspace.miljonsales.model.JointWork;
import com.globalspace.miljonsales.model.Login_Response;
import com.globalspace.miljonsales.model.NotInterestedUserList;
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

public class FragmentNotInterested extends Fragment {

    private SharedPreferences sPref;
    private View v;
    private RecyclerView rcv;
    private ProgressDialog progress;
    private ApiInterface apiInterface;
    private List<NotInterestedUserList> notInterestedUserList;
    private Not_interestedchemistlistAdapter adapter;
    private TextView searchBtn, closeBtn, clearBtn;
    public static TextView totalCount;
    private Toolbar searchToolbar, mainToolbar;
    private EditText searchview;
    private List<JointWork> jointWorks = new ArrayList<>();
    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

            try {
               /* ((Filterable) (chemist_list_adapter)).getFilter().filter(
                        et_search.getText().toString());*/
                adapter.getFilter().filter(s);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    };

    public FragmentNotInterested() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_not_interested, container, false);
        sPref = getActivity().getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        apiInterface = ApiUtils.submitdatainterface();
        rcv = (RecyclerView) v.findViewById(R.id.rcv);
        totalCount = (TextView) v.findViewById(R.id.notInterestedCount);
        showProgressDialog();
        rcv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        getlistofdata();

        return v;
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


    private void getlistofdata() {
        progress.show();
        String functionname = "notInterestedUserList";
        if (Internet.checkConnection(getActivity())) {
            Call<Login_Response> call = apiInterface.getnotinteresteddata(functionname,
                    sPref.getString(getActivity().getResources().getString(R.string.employee_id), ""));
            call.enqueue(new Callback<Login_Response>() {
                @Override
                public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                    if (response.isSuccessful()) {
                        progress.dismiss();
                        Login_Response getresponse = response.body();
                        if (getresponse.getStatus().equals("200")) {
                            notInterestedUserList = getresponse.getNotInterestedUserList();
                            if (notInterestedUserList.size() > 0) {
                                searchBtn.setVisibility(View.VISIBLE);
                                totalCount.setText("" + notInterestedUserList.size());
                                Date c = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String formattedDate = df.format(c);

                                Call<Login_Response> call1 = apiInterface.getWalletdetail(sPref.getString(getString(R.string.Username), ""),
                                        sPref.getString(getString(R.string.Password), ""),
                                        sPref.getString(getString(R.string.token_regid), ""), get_IMEI(getActivity()), "SalesPersonReporting",
                                        formattedDate);

                                call1.enqueue(new Callback<Login_Response>() {
                                    @Override
                                    public void onResponse(Call<Login_Response> call1, Response<Login_Response> response) {
                                        if (response.isSuccessful()) {
                                            if (progress.isShowing()) {
                                                progress.dismiss();
                                            }
                                            if (response.body().getJointWorks() != null) {
                                                JointWork jw = new JointWork();
                                                jw.setJointWorkId("0");
                                                jw.setJointWorkName("Select");
                                                jointWorks.add(jw);

                                                jointWorks.addAll(response.body().getJointWorks());
                                            } else {
                                                jointWorks = new ArrayList<>();
                                            }
                                            adapter = new Not_interestedchemistlistAdapter(getActivity(), notInterestedUserList,
                                                    sPref.getString(getActivity().getResources().getString(R.string.employee_id), ""), jointWorks, progress);
                                            rcv.setAdapter(adapter);


                                        } else {
                                            if (progress.isShowing())
                                                progress.dismiss();
                                            Toast.makeText(getActivity(), "Error Occurred! " + response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Login_Response> call1, Throwable t) {
                                        if (progress.isShowing())
                                            progress.dismiss();
                                        Toast.makeText(getActivity(), "Error Occurred! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else {
                                searchBtn.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "No data to dislpay", Toast.LENGTH_LONG).show();
                            }
                            //
                        } else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Login_Response> call, Throwable t) {
                    progress.dismiss();
                    Log.e("failure", t.toString());
                    Toast.makeText(getActivity(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void showProgressDialog() {
        progress = new ProgressDialog(getActivity());
        progress.setMax(100);
        // progress.setTitle("Progress Dialog");
        progress.setMessage("Your Data is loading Please Wait.......");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {


            searchBtn = (TextView) getActivity().findViewById(R.id.searchBtn);
            closeBtn = (TextView) getActivity().findViewById(R.id.search_back);
            closeBtn.setTypeface(MiljonOffer.solidFont);
            mainToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            searchToolbar = (Toolbar) getActivity().findViewById(R.id.searchtoolbar);
            searchview = (EditText) getActivity().findViewById(R.id.search_view);

            getlistofdata();

            searchBtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    if (getUserVisibleHint()) {
                        mainToolbar.setVisibility(View.GONE);
                        searchToolbar.setVisibility(View.VISIBLE);
                        circleReveal(R.id.searchtoolbar, 1, true, true);
                        searchview.setHint("Search");
                        searchview.addTextChangedListener(filterTextWatcher);
                    }
                }
            });

            closeBtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    searchview.setText("");
                    circleReveal(R.id.searchtoolbar, 1, true, false);
                    searchToolbar.setVisibility(View.GONE);
                    mainToolbar.setVisibility(View.VISIBLE);
                }
            });

            clearBtn = (TextView) getActivity().findViewById(R.id.search_clear);
            clearBtn.setTypeface(MiljonOffer.solidFont);


            clearBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchview.setText("");
                }
            });


        }
    }

    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            final View myView = getActivity().findViewById(viewID);

            int width = myView.getWidth();


            if (posFromRight > 0)

                width -= getActivity().getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)
                        - getActivity().getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2;


            if (containsOverflow)

                width -= getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);


            int cx = width;

            int cy = myView.getHeight() / 2;


            Animator anim;

            if (isShow)

                anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, (float) width);

            else

                anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float) width, 0);


            anim.setDuration((long) 220);


            // make the view invisible when the animation is done

            anim.addListener(new AnimatorListenerAdapter() {

                @Override

                public void onAnimationEnd(Animator animation) {

                    if (!isShow) {

                        super.onAnimationEnd(animation);


                        myView.setVisibility(View.GONE);

                    }

                }

            });


            // make the view visible and start the animation

            if (isShow)

                myView.setVisibility(View.VISIBLE);


            // start the animation

            anim.start();

        }

    }
}

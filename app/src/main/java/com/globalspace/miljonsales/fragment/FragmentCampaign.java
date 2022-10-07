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

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.MiljonOffer;
import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.adapter.CampaignListAdapter;
import com.globalspace.miljonsales.adapter.Chemist_List_Adapter;
import com.globalspace.miljonsales.adapter.DeleteCampaignListAdapter;
import com.globalspace.miljonsales.model.ChemistInfo;
import com.globalspace.miljonsales.model.JointWork;
import com.globalspace.miljonsales.model.Login_Response;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCampaign extends Fragment {

    private View view;
    private TextView memberCount;
    private RecyclerView recyclerView, selectMembers;
    private TextView editBtn, refresh;
    private FloatingActionButton fab;
    private BottomSheetDialog addMembersDialog;
    public static ArrayList<ChemistInfo> addMembersList;
    public static ArrayList<ChemistInfo> deleteMembersList;
    private List<ChemistInfo> allChemistList;
    private List<ChemistInfo> campaignMembers = new ArrayList<>();
    private Button add;
    private SharedPreferences sPref;
    private ProgressDialog progress;
    private ApiInterface apiInterface;
    private CampaignListAdapter campaignListAdapter;
    private EditText searchChemist;
    private List<JointWork> jointWorks = new ArrayList<>();
    private Toolbar editToolbar, mainToolbar;
    private TextView backBtn, delete;

    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                campaignListAdapter.getFilter().filter(s);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    };

    public FragmentCampaign() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_campaign, container, false);
        setContents();
        getAllChemistData();
        return view;
    }

    private void setContents() {
        sPref = getActivity().getSharedPreferences(getActivity().getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        memberCount = (TextView) view.findViewById(R.id.memberCount);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv);
        apiInterface = ApiUtils.getDashboardData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        editBtn = (TextView) getActivity().findViewById(R.id.editBtn);
        refresh = (TextView) getActivity().findViewById(R.id.refreshBtn);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        setProgressDialog();

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
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getUserVisibleHint())
                        getAllChemistData();
                }
            });

            editToolbar = (Toolbar) getActivity().findViewById(R.id.edittoolbar);
            backBtn = (TextView) getActivity().findViewById(R.id.cancelbtn);
            backBtn.setTypeface(MiljonOffer.solidFont);
            mainToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            delete = (TextView) getActivity().findViewById(R.id.deletebtn);
            delete.setTypeface(MiljonOffer.solidFont);

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteMembersList = new ArrayList<>();
                    mainToolbar.setVisibility(View.GONE);
                    editToolbar.setVisibility(View.VISIBLE);
                    circleReveal(R.id.edittoolbar, 1, true, true);
                    for (int i = 0; i < campaignMembers.size(); i++) {
                        campaignMembers.get(i).setChecked(false);
                    }
                    DeleteCampaignListAdapter adapter = new DeleteCampaignListAdapter(getActivity(), campaignMembers);
                    recyclerView.setAdapter(adapter);
                }
            });

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    circleReveal(R.id.edittoolbar, 1, true, false);
                    editToolbar.setVisibility(View.GONE);
                    mainToolbar.setVisibility(View.VISIBLE);
                    Chemist_List_Adapter adapter = new Chemist_List_Adapter(getActivity(), campaignMembers,
                            sPref.getString(getActivity().getResources().getString(R.string.employee_id), ""), jointWorks,
                            progress, "MATM_8", "Campaign");
                    recyclerView.setAdapter(adapter);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteMembersList != null) {
                        if (deleteMembersList.size() != 0) {
                            try {
                                JSONObject data = new JSONObject();
                                JSONArray chemistList = new JSONArray();
                                for (int i = 0; i < deleteMembersList.size(); i++) {
                                    JSONObject chemistData = new JSONObject();
                                    chemistData.put("chemist_id", deleteMembersList.get(i).getCHEMISTID());
                                    chemistList.put(chemistData);

                                }
                                data.put("Type", "2");
                                data.put("Emp_id", sPref.getString(getActivity().getResources().getString(R.string.employee_id), ""));
                                data.put("Chemist_List", chemistList);
                                sendChemistList(data, "setChampEmpCheDetail", "Delete");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Please select chemist to be added", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openAddMembersDialog();
                }
            });
        }
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

    private void getAllChemistData() {
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
                            if (response.body().getError().equals("true")) {
                                Toast.makeText(getActivity(), response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                if (response.body().getCHMIESTINFO().size() > 0) {
                                    allChemistList = response.body().getCHMIESTINFO();

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
                                }
                                sPref.edit().putString(getResources().getString(R.string.leave_flag), response.body().getLeaveStatus()).commit();
                                getCampaignMembersList();
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
                    Toast.makeText(getActivity(), "Error Occurred! ", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getActivity(), "Internet not available", Toast.LENGTH_SHORT).show();
        }

    }

    private void openAddMembersDialog() {
        for (int i = 0; i < allChemistList.size(); i++) {
            allChemistList.get(i).setChecked(false);
        }
        addMembersList = new ArrayList<>();
        addMembersDialog = new BottomSheetDialog(getActivity());
        addMembersDialog.setContentView(R.layout.dialog_select_members);
        searchChemist = (EditText) addMembersDialog.findViewById(R.id.searchChemistEdt);
        add = (Button) addMembersDialog.findViewById(R.id.addBtn);
        selectMembers = (RecyclerView) addMembersDialog.findViewById(R.id.chemistRcv);
        selectMembers.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        campaignListAdapter = new CampaignListAdapter(getActivity(), allChemistList, "Campaign");
        selectMembers.setAdapter(campaignListAdapter);

        searchChemist.addTextChangedListener(filterTextWatcher);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.show();
                if (addMembersList.size() != 0) {
                    try {
                        JSONObject data = new JSONObject();
                        JSONArray chemistList = new JSONArray();
                        for (int i = 0; i < addMembersList.size(); i++) {
                            if (!campaignMembers.contains(addMembersList.get(i))) {
                                JSONObject chemistData = new JSONObject();
                                chemistData.put("chemist_id", addMembersList.get(i).getCHEMISTID());
                                chemistList.put(chemistData);
                            }
                        }
                        data.put("Type", "1");
                        data.put("Emp_id", sPref.getString(getActivity().getResources().getString(R.string.employee_id), ""));
                        data.put("Chemist_List", chemistList);
                        sendChemistList(data, "setChampEmpCheDetail", "Add");
                        addMembersDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please select chemist to be added", Toast.LENGTH_SHORT).show();
                }

            }
        });

        addMembersDialog.show();

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


/*    public String get_IMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        return telephonyManager.getDeviceId();
    }*/

    private void sendChemistList(JSONObject object, String function_name, final String type) {

        if (Internet.checkConnection(getActivity())) {
            Call<Login_Response> call = apiInterface.setChampEmpCheDetail(function_name, object);
            call.enqueue(new Callback<Login_Response>() {
                @Override
                public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                    if (response.isSuccessful()) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                        if (type.equals("Delete")) {
                            circleReveal(R.id.edittoolbar, 1, true, false);
                            editToolbar.setVisibility(View.GONE);
                            mainToolbar.setVisibility(View.VISIBLE);
                        }
                        getCampaignMembersList();
                    } else {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Login_Response> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            progress.dismiss();
            Toast.makeText(getActivity(), "Internet not available", Toast.LENGTH_SHORT).show();
        }

    }

    private void getCampaignMembersList() {
        progress.dismiss();
        progress.show();
        if (Internet.checkConnection(getActivity())) {
            Call<Login_Response> call = apiInterface.getChampEmpCheDetail("getChampEmpCheDetail",
                    sPref.getString(getActivity().getResources().getString(R.string.employee_id), ""));

            call.enqueue(new Callback<Login_Response>() {
                @Override
                public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                    if (response.isSuccessful()) {
                        progress.dismiss();
                        memberCount.setText(String.valueOf(response.body().getCHMIESTINFO().size()));
                        campaignMembers = response.body().getCHMIESTINFO();
                        Chemist_List_Adapter adapter = new Chemist_List_Adapter(getActivity(), campaignMembers,
                                sPref.getString(getActivity().getResources().getString(R.string.employee_id), ""), jointWorks,
                                progress, "MATM_8", "Campaign");
                        recyclerView.setAdapter(adapter);
                    } else {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Login_Response> call, Throwable t) {

                    progress.dismiss();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
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

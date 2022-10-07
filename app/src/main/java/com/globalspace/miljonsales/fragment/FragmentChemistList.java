package com.globalspace.miljonsales.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.MiljonOffer;
import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.service.lat_long_service;
import com.globalspace.miljonsales.activity.RegisterActivity;
import com.globalspace.miljonsales.adapter.Chemist_List_Adapter;
import com.globalspace.miljonsales.adapter.JointWorkSpinnerAdapter;
import com.globalspace.miljonsales.model.ChemistInfo;
import com.globalspace.miljonsales.model.JointWork;
import com.globalspace.miljonsales.model.Login_Response;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentChemistList extends Fragment {

    public static List<ChemistInfo> chemistData = new ArrayList<>();
    public static List<JointWork> jointWork = new ArrayList<>();
    public static boolean isSearchOn = false;
    private View v;
    private SharedPreferences sPref;
    private AlertDialog alertDialog1;
    private RecyclerView rcv;
    private Chemist_List_Adapter chemist_list_adapter;
    private FloatingActionButton floatingActionButton;
    private CharSequence[] values = {" DOWNLOAD ", " NOT INTERESTED "};
    private double latitude, longitude;
    private String stratitude, strlongitude;
    private EditText Et_name, Et_emailid, Et_mobile;
    private ApiInterface apiInterface;
    private TextView searchBtn, closeBtn, clearBtn;
    public static TextView chemistCount;
    private Toolbar searchToolbar, mainToolbar;
    private EditText searchview;
    private ProgressDialog progress;
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
                chemist_list_adapter.getFilter().filter(s);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    };


    public FragmentChemistList() {
        // Required empty public constructor
    }

    public static void hideSoftKeyboard(Activity activity) {
      /*  InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);*/


        InputMethodManager inputMethodManager=(InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        View focusedView = activity.getWindow().getDecorView().getRootView();

        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_chemist_list, container, false);
        sPref = getActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        chemistCount = (TextView) v.findViewById(R.id.chemistCount);
        progress = new ProgressDialog(getActivity());
        progress.setMax(100);
        // progress.setTitle("Progress Dialog");
        progress.setMessage("Your Data is loading Please Wait.......");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);


        rcv = (RecyclerView) v.findViewById(R.id.chemistRCV);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        if (FragmentReporting.DataHolder.hasData()) {
            chemistData = FragmentReporting.DataHolder.getData();
            chemistCount.setText(chemistData.size() + "");
            jointWork = FragmentReporting.DataHolder_jointWork.getData();
            chemist_list_adapter = new  Chemist_List_Adapter(getActivity(), chemistData,
                    sPref.getString(getString(R.string.employee_id), ""), jointWork, progress, "MATM_7","Reporting");
            rcv.setAdapter(chemist_list_adapter);

        }


        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            FragmentStockistList.isSearchOn = false;
            hideSoftKeyboard(getActivity());
            floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!sPref.getString(getActivity().getString(R.string.leave_flag), "").equals("2")) {
                        if (getUserVisibleHint()) {
                            AlertDialogue();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Sorry you are on leave ", Toast.LENGTH_SHORT).show();
                    }

                }
            });


            searchBtn = (TextView) getActivity().findViewById(R.id.searchBtn);
            closeBtn = (TextView) getActivity().findViewById(R.id.search_back);
            closeBtn.setTypeface(MiljonOffer.solidFont);
            mainToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            searchToolbar = (Toolbar) getActivity().findViewById(R.id.searchtoolbar);
            searchview = (EditText) getActivity().findViewById(R.id.search_view);


            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getUserVisibleHint()) {
                        mainToolbar.setVisibility(View.GONE);
                        searchToolbar.setVisibility(View.VISIBLE);
                        circleReveal(R.id.searchtoolbar, 1, true, true);
                        searchview.setHint("Search Chemist");
                        searchview.addTextChangedListener(filterTextWatcher);
                        isSearchOn = true;
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
                    isSearchOn = false;
                    hideSoftKeyboard(getActivity());


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


            if (!isSearchOn) {
                searchview.setText("");
                circleReveal(R.id.searchtoolbar, 1, true, false);
                searchToolbar.setVisibility(View.GONE);
                mainToolbar.setVisibility(View.VISIBLE);
            }


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            onResume();
        }
    }

    private void AlertDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select Your Choice");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch (item) {
                    case 0:
                        Intent i = new Intent(getActivity(), RegisterActivity.class);
                        i.putExtra("Identifierflag", "4");
                        startActivity(i);
                        alertDialog1.dismiss();

                        break;
                    case 1:
                        Intent in = new Intent(getActivity(), lat_long_service.class);
                        getActivity().startService(in);
                        lat_long_service objects = new lat_long_service(getActivity());

                        latitude = objects.getLatitude();
                        longitude = objects.getLongitude();
                        stratitude = String.valueOf(latitude);
                        strlongitude = String.valueOf(longitude);
                        call_dialogue_notinterested(stratitude, strlongitude);
                        alertDialog1.dismiss();
                        break;
                }
                //  alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }

    private void call_dialogue_notinterested(final String stratitude, final String strlongitude) {
        final Dialog chemist_dialog = new Dialog(getActivity());
        // Include dialog.xml file
        chemist_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        chemist_dialog.setContentView(R.layout.not_interestedchemist_popuplayout);
        Et_name = (EditText) chemist_dialog.findViewById(R.id.etName);
        Et_emailid = (EditText) chemist_dialog.findViewById(R.id.etEmailid);
        Et_mobile = (EditText) chemist_dialog.findViewById(R.id.etmobile);
        final SwitchCompat jointWorkSwitch = (SwitchCompat) chemist_dialog.findViewById(R.id.checkbox);
        final Spinner spinner = (Spinner) chemist_dialog.findViewById(R.id.spnrProductQty);


        TextView tv_ok = (TextView) chemist_dialog.findViewById(R.id.tv_ok);
        TextView tv_cancel = (TextView) chemist_dialog.findViewById(R.id.tv_cancel);
        chemist_dialog.show();

        jointWorkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spinner.setVisibility(View.VISIBLE);
                } else {
                    spinner.setVisibility(View.GONE);
                }

            }
        });

        JointWorkSpinnerAdapter adapter = new JointWorkSpinnerAdapter(getActivity(), jointWork);
        spinner.setAdapter(adapter);


        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String chemist_name = Et_name.getText().toString().trim();
                String email_id = Et_emailid.getText().toString();
                String mobile_no = Et_mobile.getText().toString();
                if (chemist_name.equals("")) {
                    Toast.makeText(getActivity(), "Please enter shop name/chemist name to report",
                            Toast.LENGTH_LONG).show();
                } else if (Et_mobile.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter mobile number to report", Toast.LENGTH_SHORT).show();
                } else {


                    apiInterface = ApiUtils.submitdatainterface();
                    if (Internet.checkConnection(getActivity())) {
                        String jointWorkId = "null";


                        if (jointWorkSwitch.isChecked()) {
                            if (spinner.getSelectedItemPosition() != 0) {
                                jointWorkId = jointWork.get(spinner.getSelectedItemPosition()).getJointWorkId();

                                submitnotinterestedchemist(chemist_name, email_id,
                                        mobile_no, sPref.getString(getResources().getString(R.string.employee_id), ""),
                                        stratitude, strlongitude, jointWorkId);
                                chemist_dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Please Select JointWork", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            submitnotinterestedchemist(chemist_name, email_id,
                                    mobile_no, sPref.getString(getResources().getString(R.string.employee_id), ""),
                                    stratitude, strlongitude, jointWorkId);
                            chemist_dialog.dismiss();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please check your Internet connection",
                                Toast.LENGTH_LONG).show();
                        chemist_dialog.dismiss();
                    }


                }


            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chemist_dialog.dismiss();
            }
        });

    }

    private void submitnotinterestedchemist(String chemistName, String email_id,
                                            String mobile_no, String str_employee_id,
                                            String stratitude, String strlongitude, String jointWorkId) {

        String functionname = "notInterestedUser";

        Call<Login_Response> call = apiInterface.setnotinterestedchemist(chemistName, email_id,
                mobile_no, functionname, str_employee_id, stratitude, strlongitude, jointWorkId);
        call.enqueue(new Callback<Login_Response>() {
            @Override
            public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                if (response.isSuccessful()) {

                    Login_Response getresponse = response.body();
                    if (getresponse.getStatus().equals("200")) {
                        Toast.makeText(getActivity(), "Reporting has been done!!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_LONG).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<Login_Response> call, Throwable t) {
                Log.e("failure", t.toString());
                Toast.makeText(getActivity(), "Error Occurred !", Toast.LENGTH_SHORT).show();
                //progress.dismiss();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

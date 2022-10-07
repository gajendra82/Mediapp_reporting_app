package com.globalspace.miljonsales.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.MiljonOffer;
import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.adapter.SubordinateAdapter;
import com.globalspace.miljonsales.model.SubordinateResponse;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSubordinatesList extends Fragment {

    private View v;
    private RecyclerView subordinateRecycler;
    private TextView subordinateCount, searchBtn, closeBtn, clearBtn;
    private EditText searchview;
    private Toolbar mainToolbar, searchToolbar;
    private SharedPreferences sPref;
    private ApiInterface apiInterface;
    private ProgressDialog progress;
    private SubordinateAdapter subordinateAdapter;
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
                subordinateAdapter.getFilter().filter(s);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    };

    public FragmentSubordinatesList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_subordinates_list, container, false);
        setComponents();
        getData();
        return v;
    }

    private void setComponents() {
        subordinateRecycler = (RecyclerView) v.findViewById(R.id.subordinateRecycler);
        subordinateCount = (TextView) v.findViewById(R.id.subordinateCount);
        showProgressDialog();
        subordinateRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        sPref = getActivity().getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        apiInterface = ApiUtils.submitdatainterface();
    }

    private void showProgressDialog() {
        progress = new ProgressDialog(getActivity());
        progress.setMax(100);
        progress.setMessage("Your Data is loading Please Wait.......");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);

    }

    private void getData() {
        progress.show();
        if (Internet.checkConnection(getActivity())) {

            Call<SubordinateResponse> call = apiInterface.getMiljonEmpSubOrInfo("getMiljonEmpSubOrInfo",
                    sPref.getString(getActivity().getResources().getString(R.string.employee_id), ""));

            call.enqueue(new Callback<SubordinateResponse>() {
                @Override
                public void onResponse(Call<SubordinateResponse> call, Response<SubordinateResponse> response) {
                    if (response.body().getStatus().equals("200")) {
                        progress.dismiss();
                        ArrayList<SubordinateResponse.SubordinateData> data = response.body().getSubordinateData();
                        subordinateCount.setText(data.size() + "");
                        subordinateAdapter = new SubordinateAdapter(getActivity(), data);
                        subordinateRecycler.setAdapter(subordinateAdapter);
                    } else {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Error Occurred !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SubordinateResponse> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Error Occurred !", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            progress.dismiss();
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
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


            searchBtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    if (getUserVisibleHint()) {
                        mainToolbar.setVisibility(View.GONE);
                        searchToolbar.setVisibility(View.VISIBLE);
                        circleReveal(R.id.searchtoolbar, 1, true, true);
                        searchview.setHint("Search Subordinates");
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

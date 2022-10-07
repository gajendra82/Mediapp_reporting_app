package com.globalspace.miljonsales.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Build;
import androidx.annotation.RequiresApi;

import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalspace.miljonsales.MiljonOffer;
import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.adapter.CallDetailsAdapter;
import com.globalspace.miljonsales.model.DashboardData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CallDetails extends Activity {
    private ImageView backBtn;
    private TextView toolbarTitle, selectDate, calIcon, closeBtn,searchIcon, searchBack, clearSearch;
    private String Type;
    private RecyclerView callRcv;
    private ArrayList<DashboardData.ReportingDetails> reportingDetails;
    public static String FilterType;
    private CardView filterCV;
    private CallDetailsAdapter callDetailsAdapter;
    private Date date;
    private String selected_date;
    private Toolbar mainToolbar,searchToolbar;
    private EditText searchEdt;
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
                FilterType = "ShopName";
                callDetailsAdapter.getFilter().filter(s);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_details);
        setContent();
        Bundle b = getIntent().getExtras();
        Type = b.getString("Type");
        reportingDetails = (ArrayList<DashboardData.ReportingDetails>) b.getSerializable("ReportingData");
        if (Type.equals("Monthly")) {
            filterCV.setVisibility(View.VISIBLE);
        }

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region Date Picker

                final Calendar c = Calendar.getInstance();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                try {
                    date = format.parse(selected_date + "T09:27:37Z");
                    c.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = 1;
                c.set(mYear, mMonth, mDay);


                DatePickerDialog datePickerDialog = new DatePickerDialog(CallDetails.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                FilterType = "Date";
                                callDetailsAdapter.getFilter().filter("");
                                int month = monthOfYear + 1;
                                int day = dayOfMonth;
                                String monthStr, dayStr;
                                if (month < 10) {
                                    monthStr = "0" + month;
                                } else {
                                    monthStr = String.valueOf(month);
                                }

                                if (day < 10) {
                                    dayStr = "0" + day;
                                } else {
                                    dayStr = String.valueOf(day);
                                }
                                String date = year + "-" + monthStr + "-" + dayStr;
                                selectDate.setText(date);
                                closeBtn.setVisibility(View.VISIBLE);
                                callDetailsAdapter.getFilter().filter(date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                c.set(mYear, mMonth, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                datePickerDialog.show();

                //endregion
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterType = "Date";
                callDetailsAdapter.getFilter().filter("");
                selectDate.setText("SELECT DATE");
                closeBtn.setVisibility(View.GONE);
            }
        });
        callDetailsAdapter = new CallDetailsAdapter(CallDetails.this, reportingDetails);
        callRcv.setAdapter(callDetailsAdapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainToolbar.setVisibility(View.GONE);
                searchToolbar.setVisibility(View.VISIBLE);
                circleReveal(R.id.searchtoolbar, 1, true, true);
                searchEdt.setHint("Search Shop Name");
                searchEdt.addTextChangedListener(filterTextWatcher);
            }
        });

        searchBack.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                searchEdt.setText("");
                circleReveal(R.id.searchtoolbar, 1, true, false);
                searchToolbar.setVisibility(View.GONE);
                mainToolbar.setVisibility(View.VISIBLE);
                hideSoftKeyboard();


            }
        });


        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEdt.setText("");

            }
        });
    }
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    private void setContent() {
        backBtn = (ImageView) findViewById(R.id.backBtn);
        toolbarTitle = (TextView) findViewById(R.id.title);
        filterCV = (CardView) findViewById(R.id.filterCardView);
        selectDate = (TextView) findViewById(R.id.selectDate);
        calIcon = (TextView) findViewById(R.id.calIcon);
        calIcon.setTypeface(MiljonOffer.solidFont);
        closeBtn = (TextView) findViewById(R.id.closeIcon);
        closeBtn.setTypeface(MiljonOffer.solidFont);
        closeBtn.setVisibility(View.GONE);
        toolbarTitle.setText("Call Details");
        searchIcon = (TextView) findViewById(R.id.searchBtn);
        searchIcon.setTypeface(MiljonOffer.solidFont);
        searchIcon.setVisibility(View.VISIBLE);
        mainToolbar = (Toolbar) findViewById(R.id.toolbar);
        searchToolbar = (Toolbar) findViewById(R.id.searchtoolbar);
        searchBack = (TextView) findViewById(R.id.search_back);
        searchBack.setTypeface(MiljonOffer.solidFont);
        searchEdt = (EditText) findViewById(R.id.search_view);
        searchEdt.setHint("Search Shop Name");

        clearSearch = (TextView) findViewById(R.id.search_clear);
        clearSearch.setTypeface(MiljonOffer.solidFont);
        callRcv = (RecyclerView) findViewById(R.id.callRcv);
        callRcv.setLayoutManager(new LinearLayoutManager(CallDetails.this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CallDetails.this.finish();
    }

    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            final View myView = findViewById(viewID);

            int width = myView.getWidth();


            if (posFromRight > 0)

                width -= getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)
                        - getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2;


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

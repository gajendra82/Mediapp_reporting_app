package com.globalspace.miljonsales;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.MiljonOffer;
import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.adapter.DailyPobAdapter;
import com.globalspace.miljonsales.adapter.POBAdapter;
import com.globalspace.miljonsales.model.DailyPobResponse;
import com.globalspace.miljonsales.model.MonthlyPOB;
import com.globalspace.miljonsales.model.POBResponse;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class POBActivity extends Activity {

    private TextView toolbarTitle, searchIcon, searchBack, clearSearch, filterIcon, calIcon, selectDate, clearBtn;
    private LinearLayout selectDateLL, filterLL;
    private ImageView backBtn;
    private RecyclerView recyclerView;
    private android.app.ProgressDialog progress;
    private ApiInterface apiInterface;
    private SharedPreferences sPref;
    private String selected_date, showDate, function_name, EmpId, type;
    private Toolbar searchToolbar, mainToolbar;
    private EditText searchEdt;
    private POBAdapter pobAdapter;
    private DailyPobAdapter dailyPobAdapter;
    private ArrayList<MonthlyPOB> monthlyPOBS;
    private List<POBResponse.MONTHLY> monthlyArrayList, filteredPOB;
    private List<DailyPobResponse._0> dailyPobResponses;
    private List<DailyPobResponse._0> filtereddailyPOB;
    private Date date;
    private CardView filterCardView, totalCardView;
    public static String FilterType;
    private BottomSheetDialog filterDialog;
    private RadioButton approved, pending, postpone, declined, delivered;
    private TextWatcher filter = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            try {
                FilterType = "ChemistName";
                // pobAdapter.getFilter().filter(s);
                if (dailyPobAdapter != null){
                    dailyPobAdapter.getFilter().filter(editable);
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    };
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
                FilterType = "ChemistName";
                if (pobAdapter != null){
                pobAdapter.getFilter().filter(s);}
                if (dailyPobAdapter != null){
                dailyPobAdapter.getFilter().filter(s);}
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    };

    public static TextView totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pob);
        setContent();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            function_name = b.getString("function_name");
            selected_date = b.getString("date");
            EmpId = b.getString("EmpId");
            type = b.getString("Type");
            if (type.equals("Daily")) {
                getDailyData(function_name, selected_date, EmpId);
            }

            if (type.equals("Monthly")) {
                filterCardView.setVisibility(View.VISIBLE);
            } else {
                filterCardView.setVisibility(View.GONE);
            }
            getData(function_name, selected_date, EmpId);


        }

        filterLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.show();
            }
        });

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


                DatePickerDialog datePickerDialog = new DatePickerDialog(POBActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                FilterType = "Date";
                                if (pobAdapter != null) {
                                    pobAdapter.getFilter().filter("");
                                }
                                if (dailyPobAdapter != null) {
                                    dailyPobAdapter.getFilter().filter("");
                                }
                                approved.setChecked(false);
                                pending.setChecked(false);
                                postpone.setChecked(false);
                                delivered.setChecked(false);
                                declined.setChecked(false);
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
                                clearBtn.setVisibility(View.VISIBLE);
                                if (pobAdapter != null) {
                                    pobAdapter.getFilter().filter(date);
                                }
                                if (dailyPobAdapter != null) {
                                    dailyPobAdapter.getFilter().filter(date);
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                c.set(mYear, mMonth, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                datePickerDialog.show();

                //endregion
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterType = "Date";

                if (pobAdapter != null) {
                    pobAdapter.getFilter().filter("");
                }
                if (dailyPobAdapter != null) {
                    dailyPobAdapter.getFilter().filter("");
                }
                selectDate.setText("SELECT DATE");
                approved.setChecked(false);
                pending.setChecked(false);
                postpone.setChecked(false);
                delivered.setChecked(false);
                declined.setChecked(false);
                clearBtn.setVisibility(View.GONE);
            }
        });
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainToolbar.setVisibility(View.GONE);
                searchToolbar.setVisibility(View.VISIBLE);
                circleReveal(R.id.searchtoolbar, 1, true, true);
                searchEdt.setHint("Search Chemist");
                searchEdt.addTextChangedListener(filterTextWatcher);
                searchEdt.addTextChangedListener(filter);
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
        toolbarTitle = (TextView) findViewById(R.id.title);
        searchIcon = (TextView) findViewById(R.id.searchBtn);
        searchIcon.setTypeface(MiljonOffer.solidFont);
        totalAmount = (TextView) findViewById(R.id.totalAmount);
        searchIcon.setVisibility(View.VISIBLE);
        openDialogFilter();
        selectDate = (TextView) findViewById(R.id.selectDate);
        clearBtn = (TextView) findViewById(R.id.closeIcon);
        clearBtn.setTypeface(MiljonOffer.solidFont);
        clearBtn.setVisibility(View.GONE);
        filterCardView = (CardView) findViewById(R.id.filterCardView);
        filterCardView.setVisibility(View.VISIBLE);
        totalCardView = (CardView) findViewById(R.id.totalCardView);
        selectDateLL = (LinearLayout) findViewById(R.id.selectDateLL);
        filterLL = (LinearLayout) findViewById(R.id.filterIconLL);

        calIcon = (TextView) findViewById(R.id.calIcon);
        calIcon.setTypeface(MiljonOffer.solidFont);

        filterIcon = (TextView) findViewById(R.id.filterIcon);
        filterIcon.setTypeface(MiljonOffer.solidFont);


        toolbarTitle.setText("POB");
        sPref = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        recyclerView = (RecyclerView) findViewById(R.id.pobList);
        recyclerView.setLayoutManager(new LinearLayoutManager(POBActivity.this, LinearLayoutManager.VERTICAL, false));
        apiInterface = ApiUtils.getDashboardData();
        showProgressDialog();

        mainToolbar = (Toolbar) findViewById(R.id.toolbar);
        searchToolbar = (Toolbar) findViewById(R.id.searchtoolbar);
        searchBack = (TextView) findViewById(R.id.search_back);
        searchBack.setTypeface(MiljonOffer.solidFont);

        clearSearch = (TextView) findViewById(R.id.search_clear);
        clearSearch.setTypeface(MiljonOffer.solidFont);

        searchEdt = (EditText) findViewById(R.id.search_view);
        searchEdt.setHint("Search Chemist");
    }

    private void showProgressDialog() {
        progress = new android.app.ProgressDialog(POBActivity.this);
        progress.setMax(100);
        //progress.setTitle("Progress Dialog");
        progress.setMessage("Your Data is loading Please Wait.......");
        progress.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);

    }

    private void openDialogFilter() {
        filterDialog = new BottomSheetDialog(POBActivity.this);
        filterDialog.setContentView(R.layout.dialog_status);
        approved = (RadioButton) filterDialog.findViewById(R.id.approved);
        pending = (RadioButton) filterDialog.findViewById(R.id.pending);
        postpone = (RadioButton) filterDialog.findViewById(R.id.postpone);
        declined = (RadioButton) filterDialog.findViewById(R.id.declined);
        delivered = (RadioButton) filterDialog.findViewById(R.id.delivered);

        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterType = "Status";
                if (pobAdapter != null) {
                    pobAdapter.getFilter().filter("");
                    approved.setChecked(true);
                    pobAdapter.getFilter().filter("APPROVED");
                }
                if (dailyPobAdapter != null) {
                    dailyPobAdapter.getFilter().filter("");
                    approved.setChecked(true);
                    dailyPobAdapter.getFilter().filter("APPROVED");
                }
                filterDialog.dismiss();
            }
        });
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterType = "Status";
                if (pobAdapter != null) {
                    pobAdapter.getFilter().filter("");
                    pending.setChecked(true);
                    pobAdapter.getFilter().filter("PENDING");
                }

                if (dailyPobAdapter != null) {
                    dailyPobAdapter.getFilter().filter("");
                    pending.setChecked(true);
                    dailyPobAdapter.getFilter().filter("PENDING");
                }
                filterDialog.dismiss();
            }
        });
        postpone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterType = "Status";
                if (pobAdapter != null) {
                    pobAdapter.getFilter().filter("");
                    postpone.setChecked(true);
                    pobAdapter.getFilter().filter("POSTPONE");
                }
                if (dailyPobAdapter != null) {
                    dailyPobAdapter.getFilter().filter("");
                    postpone.setChecked(true);
                    dailyPobAdapter.getFilter().filter("POSTPONE");
                }
                filterDialog.dismiss();
            }
        });
        declined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterType = "Status";
                if (pobAdapter != null) {
                    pobAdapter.getFilter().filter("");
                    declined.setChecked(true);

                    pobAdapter.getFilter().filter("CANCEL");
                }
                if (dailyPobAdapter != null) {
                    dailyPobAdapter.getFilter().filter("");
                    declined.setChecked(true);

                    dailyPobAdapter.getFilter().filter("CANCEL");
                }

                filterDialog.dismiss();
            }
        });
        delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterType = "Status";

                if (pobAdapter != null) {
                    pobAdapter.getFilter().filter("");
                    delivered.setChecked(true);
                    pobAdapter.getFilter().filter("DELIVERED");
                }

                if (dailyPobAdapter != null) {
                    dailyPobAdapter.getFilter().filter("");
                    delivered.setChecked(true);
                    dailyPobAdapter.getFilter().filter("DELIVERED");
                }

                filterDialog.dismiss();
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        POBActivity.this.finish();
    }

    private void getData(String function_name, String date, String EmpId) {
        if (Internet.checkConnection(POBActivity.this)) {
            progress.show();
            Call<POBResponse> call = apiInterface.getEmpMonthPOBDetail(function_name,
                    EmpId, date);

            call.enqueue(new Callback<POBResponse>() {
                @Override
                public void onResponse(Call<POBResponse> call, Response<POBResponse> response) {
                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                    if (response.body().getStatus().equals("200")) {
                        if (type.equals("Monthly")) {
                            monthlyArrayList = response.body().getMONTHLYDELIVEREDPOB();
                            filteredPOB = response.body().getMONTHLYDELIVEREDPOB();
                            pobAdapter = new POBAdapter(POBActivity.this, monthlyArrayList);
                            recyclerView.setAdapter(pobAdapter);
                        } else if (type.equals("Approved")) {
                            monthlyArrayList = response.body().getmONTHLYAPPROVED();
                            filteredPOB = response.body().getmONTHLYAPPROVED();
                            pobAdapter = new POBAdapter(POBActivity.this, monthlyArrayList);
                            recyclerView.setAdapter(pobAdapter);
                        } else if (type.equals("Decline")) {
                            monthlyArrayList = response.body().getmONTHLYCANCEL();
                            filteredPOB = response.body().getmONTHLYCANCEL();
                            pobAdapter = new POBAdapter(POBActivity.this, monthlyArrayList);
                            recyclerView.setAdapter(pobAdapter);
                        } else if (type.equals("Pending")) {
                            monthlyArrayList = response.body().getmONTHLYPENDINGPOB();
                            filteredPOB = response.body().getmONTHLYPENDINGPOB();
                            if (monthlyArrayList.get(0).getORDERID().equals("")) {
                                Toast.makeText(POBActivity.this, "NO Orders", Toast.LENGTH_SHORT).show();
                                POBActivity.this.finish();
                            } else {
                                pobAdapter = new POBAdapter(POBActivity.this, monthlyArrayList);
                                recyclerView.setAdapter(pobAdapter);
                            }

                        } else if (type.equals("Postpone")) {
                            monthlyArrayList = response.body().getmONTHLYPOSTPONEPOB();
                            filteredPOB = response.body().getmONTHLYPOSTPONEPOB();
                            if (monthlyArrayList.get(0).getORDERID().equals("")) {
                                Toast.makeText(POBActivity.this, "No Orders", Toast.LENGTH_SHORT).show();
                                POBActivity.this.finish();
                            } else {
                                pobAdapter = new POBAdapter(POBActivity.this, monthlyArrayList);
                                recyclerView.setAdapter(pobAdapter);
                            }

                        }


                    } else {
                        Toast.makeText(POBActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<POBResponse> call, Throwable t) {
                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                    Toast.makeText(POBActivity.this, "Error Occurred !", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "No internet Connection ! ", Toast.LENGTH_SHORT).show();
        }
    }


    private void getDailyData(String function_name, String date, String EmpId) {
        if (Internet.checkConnection(POBActivity.this)) {
            progress.show();
            Call<DailyPobResponse> call = apiInterface.getEmpDailyPOBDetail(function_name,
                    EmpId, date);

            call.enqueue(new Callback<DailyPobResponse>() {
                @Override
                public void onResponse(Call<DailyPobResponse> call, Response<DailyPobResponse> response) {
                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                    if (response.body().getStatus().equals("200")) {

                        dailyPobResponses = response.body().get0();
                        filtereddailyPOB = response.body().get0();
                        dailyPobAdapter = new DailyPobAdapter(POBActivity.this, dailyPobResponses);
                        recyclerView.setAdapter(dailyPobAdapter);


                    } else {
                        Toast.makeText(POBActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DailyPobResponse> call, Throwable t) {
                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                    Toast.makeText(POBActivity.this, "Error Occurred !", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "No internet Connection ! ", Toast.LENGTH_SHORT).show();
        }
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
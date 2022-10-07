package com.globalspace.miljonsales.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.adapter.getprdlistadapter;
import com.globalspace.miljonsales.adapter.prdwisediscCompanyadapter;
import com.globalspace.miljonsales.adapter.prdwisediscategoryfilteradapter;
import com.globalspace.miljonsales.database.DBAdapter;
import com.globalspace.miljonsales.model.CategoryInfo;
import com.globalspace.miljonsales.model.ManufactureInfo;
import com.globalspace.miljonsales.model.discountProductInfo;
import com.globalspace.miljonsales.model.productwisediscountprdlist;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pobActivity extends AppCompatActivity implements prdwisediscategoryfilteradapter.OnCategorySelectedListener, prdwisediscCompanyadapter.onItemSelected {
    LinearLayoutManager mLayoutManager;
    ImageView cartIcon;
    RecyclerView recyclerView;
    AppCompatActivity appcompact;
    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    private ApiInterface apiInterface;
    getprdlistadapter adapter;
    String org_id = "";
    String chem_id="";
    String SESSION_ID="";


    public static ProgressBar mainscrennprogress_img;
    private Call<productwisediscountprdlist> callprdwisedis;
    private int totalcount;
    private ProgressBar lazyLoadingProgress;
    private List<ManufactureInfo> companyList;
    private List<CategoryInfo> categoryList;
    private List<discountProductInfo> searchprdlist;

    private ArrayList<ManufactureInfo> CompanyList = new ArrayList<>();


    private LinearLayout ll_category, ll_comapnyname;
    private RecyclerView rv_brands, rv_companynames;
    boolean isLoading = false;
    private int scrollPosition;
    private LinearLayout ll_carddetails, mainLL;
    private prdwisediscCompanyadapter companyFilterAdapter;
    private prdwisediscategoryfilteradapter categoryFilterAdapter;
    private String CompanyId = "";
    private String CategoryId = "";
    int visibleItemCount;
    int totalItemCount;
    int pastVisiblesItems;
    private int currentSize;
    private int nextLimit;
    EditText edittextsearch;
    private ImageView bckimg, searchclose;
    private int CART = 1;
    private TextView cartCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pobbooking);

        appcompact = this;

        context = this;
        pref = context.getSharedPreferences(getString(R.string.app_name),
                context.MODE_PRIVATE);

        chem_id = getIntent().getExtras().getString("Chem_org_id");
        SESSION_ID = getIntent().getExtras().getString("SESSION_ID");

        edit = pref.edit();
        mainscrennprogress_img = (ProgressBar) findViewById(R.id.progress_img_filter);
        lazyLoadingProgress = (ProgressBar) findViewById(R.id.lazyLoadingProgress);

        ll_category = (LinearLayout) findViewById(R.id.ll_category);
        ll_comapnyname = (LinearLayout) findViewById(R.id.ll_companyname);

        rv_brands = (RecyclerView) findViewById(R.id.rv_category);
        cartCount = (TextView) findViewById(R.id.cartCount);
        rv_brands.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rv_companynames = (RecyclerView) findViewById(R.id.rv_companynames);
        rv_companynames.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        mainLL = (LinearLayout) findViewById(R.id.ll_main);
        ll_carddetails = (LinearLayout) findViewById(R.id.ll_carddetails);
        edittextsearch = (EditText) findViewById(R.id.spnrSearchMedicine);
        bckimg = (ImageView) findViewById(R.id.backIcon);
        searchclose = (ImageView) findViewById(R.id.imgclose);
        cartIcon = (ImageView) findViewById(R.id.cartIcon);

        searchclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittextsearch.setText("");
            }
        });
        mLayoutManager = new LinearLayoutManager(context);

        recyclerView = (RecyclerView) findViewById(R.id.list);

        recyclerView.setLayoutManager(mLayoutManager);

        MyDownloader callasync = new MyDownloader();
        callasync.execute("callserveice");

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(pobActivity.this,CartActivity.class);
                i.putExtra("Chem_org_id",chem_id);
                i.putExtra("SESSION_ID",SESSION_ID);
                startActivityForResult(i,CART);
            }
        });


        ll_carddetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_carddetails.getVisibility() == View.VISIBLE) {
                    ll_carddetails.setVisibility(View.GONE);
                    rv_brands.setVisibility(View.INVISIBLE);
                    rv_companynames.setVisibility(View.INVISIBLE);
                    mainLL.setClickable(true);
                }
            }
        });


        ll_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rv_brands.getVisibility() == View.INVISIBLE) {
                    ll_carddetails.setVisibility(View.VISIBLE);
                    categoryFilterAdapter = new prdwisediscategoryfilteradapter(appcompact, categoryList, CategoryId);
                    rv_brands.setAdapter(categoryFilterAdapter);
                    categoryFilterAdapter.setonItemClickListener(pobActivity.this);
                    rv_brands.setVisibility(View.VISIBLE);
                    rv_companynames.setVisibility(View.INVISIBLE);
                    mainLL.setClickable(false);
                    ll_carddetails.setClickable(true);
                } else {
                    ll_carddetails.setVisibility(View.GONE);
                    rv_brands.setVisibility(View.INVISIBLE);
                    mainLL.setClickable(true);
                }
            }
        });


        ll_comapnyname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rv_companynames.getVisibility() == View.INVISIBLE) {
                    ll_carddetails.setVisibility(View.VISIBLE);
                    rv_brands.setVisibility(View.INVISIBLE);
                    rv_companynames.setVisibility(View.VISIBLE);

                    companyFilterAdapter = new prdwisediscCompanyadapter(context, companyList, CompanyId);
                    rv_companynames.setAdapter(companyFilterAdapter);
                    companyFilterAdapter.setonItemClickListener(pobActivity.this);

                    mainLL.setClickable(false);
                    ll_carddetails.setClickable(true);
                } else {
                    ll_carddetails.setVisibility(View.GONE);
                    rv_companynames.setVisibility(View.INVISIBLE);
                    mainLL.setClickable(true);
                }
            }
        });


        edittextsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String filterString = charSequence.toString().toLowerCase();
                if (filterString.length() != 0) {
                    searchclose.setVisibility(View.VISIBLE);
                    if (rv_companynames.getVisibility() == View.VISIBLE) {
                        rv_companynames.setVisibility(View.GONE);
                    }

                } else {
                    searchclose.setVisibility(View.GONE);

                    try {

                        View viewkeyboard = pobActivity.this.getCurrentFocus();
                        //If no view currently has focus, create a new one, just so we can grab a window token from it
                        if (viewkeyboard == null) {
                            viewkeyboard = new View(context);
                        }
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(viewkeyboard.getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                apiInterface = ApiUtils.getWalletData();


                mainscrennprogress_img.setVisibility(View.VISIBLE);

                // searchtext = filterString;
                //getproductstockfilter("getProductStock", useridtosave, filterString, 0);
                if (callprdwisedis.isExecuted()) {
                    callprdwisedis.cancel();
                }
                //  mainscrennprogress_img.setVisibility(View.GONE);

                getprdwisedicountProductList(filterString, 0, CompanyId, CategoryId);

                // adapterSearchProduct.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (searchprdlist.size() != 0) {
                    // mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);


                    // LinearLayoutManager linearLayoutManager = (LinearLayoutManager) lvProducts.getLayoutManager();
                    visibleItemCount = mLayoutManager.findLastCompletelyVisibleItemPosition();
                    totalItemCount = mLayoutManager.getItemCount() - 1;
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();


                    if (!isLoading) {
                        if (mLayoutManager != null && mLayoutManager.findLastVisibleItemPosition() == searchprdlist.size() - 1) {
                            if (searchprdlist.size() != 0) {
                                if (searchprdlist.size() < totalcount) {
                                    isLoading = true;

                                    loadMore();
                                }

                            }
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);

            }
        });


        bckimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1){
            if (data.getExtras().getString("Result").equals("Order Placed")){
                new DBAdapter(this).deleteAll();
                pobActivity.this.finish();
            }else if (data.getExtras().getString("Result").equals("All Delete")){
                adapter = new getprdlistadapter(searchprdlist, pobActivity.this, org_id, mainscrennprogress_img, "");
                recyclerView.setAdapter(adapter);
                cartIcon.setVisibility(View.GONE);
                cartCount.setVisibility(View.GONE);
            }
        }
    }

    private void loadMore() {

        // addbtn.setVisibility(View.GONE);

        lazyLoadingProgress.setVisibility(View.VISIBLE);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (searchprdlist.size() != 0) {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            scrollPosition = searchprdlist.size();
                            currentSize = scrollPosition;
                            nextLimit = currentSize + 50;
                            if (nextLimit < searchprdlist.size() - 1) {
                                nextLimit = 0;
                                nextLimit = currentSize + 50;

                            } else {
                                nextLimit = currentSize + totalcount - 1 - currentSize;
                            }


                            if (nextLimit <= totalcount - 1) {


                                if (currentSize - 1 < nextLimit) {

                                    apiInterface = ApiUtils.getWalletData();
                                    // getSearchProductList("",currentSize, CompanyId, CategoryId);

                                    getprdwisedicountProductList("", currentSize, CompanyId, CategoryId);

                                    //  currentSize++;

                                }
                            }
                        }

                    }, 1000);

                    // isLoading = false;CALL GET_STK_PRODUCT_FOR_ORDER
                } else {
                    isLoading = false;
                }
            }

        }, 500);

    }

    @Override
    public void onBackPressed() {
        new DBAdapter(this).deleteAll();
        pobActivity.this.finish();
    }

    private void getprdwisedicountProductList(String searchtext, final int currentSize, String companyId, String categoryId) {

        if (Internet.checkConnection(context)) {
            mainscrennprogress_img.setVisibility(View.VISIBLE);


            apiInterface = ApiUtils.getWalletData();
            callprdwisedis = apiInterface.getProductwisedisData("getProductStockUpdate", searchtext, companyId, categoryId, String.valueOf(currentSize));

            callprdwisedis.enqueue(new Callback<productwisediscountprdlist>() {
                @Override
                public void onResponse(Call<productwisediscountprdlist> call, Response<productwisediscountprdlist> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("200")) {
                            totalcount = Integer.valueOf(response.body().getTotalCount());
                            if (currentSize == 0) {
                                try {

                                    if (mainscrennprogress_img.isShown())

                                        mainscrennprogress_img.setVisibility(View.GONE);


                                    if (lazyLoadingProgress.isShown())
                                        lazyLoadingProgress.setVisibility(View.GONE);


                                    companyList = new ArrayList<>();
                                    CompanyList = new ArrayList<>();
                                    categoryList = new ArrayList<>();
                                    companyList = response.body().getManufactureInfo();
                                    CompanyList = (ArrayList<ManufactureInfo>) response.body().getManufactureInfo();
                                    categoryList = response.body().getCategoryInfo();


                                    searchprdlist = response.body().getProductInfo();

                                    adapter = new getprdlistadapter(searchprdlist, pobActivity.this, org_id, mainscrennprogress_img, "");


                                    recyclerView.setAdapter(adapter);


                                } catch (Exception ex) {
                                    ex.printStackTrace();

                                }
                            } else {

                                if (mainscrennprogress_img.isShown())

                                    mainscrennprogress_img.setVisibility(View.GONE);


                                if (lazyLoadingProgress.isShown())
                                    lazyLoadingProgress.setVisibility(View.GONE);

                                isLoading = false;


                                isLoading = false;
                                List<discountProductInfo> data = response.body().getProductInfo();
                                //totalcount = productstock.getTOTAL_COUNT();
                                searchprdlist.addAll(data);


                                //lazyLoadingProgress.setVisibility(View.GONE);
                                //  currentSize = searchprdlist.size();
                                 /*adapterSearchProduct = new offlinesoldprdlistadapter(
                                 context, availableprdstock, useridtosave, Reedempoint);*/
                                //adapterSearchProduct.setonItemClickListener(context);
                                adapter.notifyItemRangeInserted(scrollPosition, searchprdlist.size());
                                //   adapter.setonItemClickListener(SearchChemistActivity.this);

                            }
                        } else {
                        }
                    } else {

                    }
                    mainscrennprogress_img.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<productwisediscountprdlist> call, Throwable t) {
                    if (!call.isCanceled()) {
                        mainscrennprogress_img.setVisibility(View.GONE);
                    }
                }

            });
        }
    }

    @Override
    public void onItemSelected(String companyId) {

        CompanyId = companyId;
        ll_carddetails.setVisibility(View.GONE);
        rv_companynames.setVisibility(View.INVISIBLE);
        mainLL.setClickable(true);

        // getSearchProductList("",0,CompanyId,CategoryId);
        getprdwisedicountProductList("", 0, CompanyId, CategoryId);

    }

    @Override
    public void onCategorySelected(String categoryId) {
        CategoryId = categoryId;
        CompanyId = "";
        ll_carddetails.setVisibility(View.GONE);
        rv_brands.setVisibility(View.INVISIBLE);
        mainLL.setClickable(true);

        getprdwisedicountProductList("", 0, CompanyId, CategoryId);

    }


    public class MyDownloader extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            getprdwisedicountProductList("", 0, "", "");

   /*         PrdListResponse = pref.getString(Constants.PRD_LIST_RESPONSE,"");
            Gson gson = new Gson();

            if (!PrdListResponse.equals("")) {

                productlistresponse = gson.fromJson(PrdListResponse, ProductListResponse.class);
            }*/









        /*Intent service = new Intent(Dashboard_stockist.this, DeleteTokenService.class);
        startService(service);*/
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
          /*  if (productlistresponse != null) {
                //loaddata();
            }else {

                if (pref.getBoolean(Constants.IS_FLASHSCREEN_API_CALL_CANCELLED,false)) {

                    //   Flashscreen.call.cancel();
           MyDownloader callasync = new MyDownloader();
                    callasync.execute("callserveice");

                }else {
                    getProductList(0);

                }



                //getProductList(0);
            }*/

        }
    }


}
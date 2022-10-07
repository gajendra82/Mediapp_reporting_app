package com.globalspace.miljonsales.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.MiljonOffer;
import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.adapter.OrderStockistAdapter;
import com.globalspace.miljonsales.database.DBAdapter;
import com.globalspace.miljonsales.model.AddProductToCart;
import com.globalspace.miljonsales.model.CartStockistListResponse;
import com.globalspace.miljonsales.model.Login_Response;
import com.globalspace.miljonsales.model.OrderResponse;
import com.globalspace.miljonsales.model.STOCKIESTINFO;
import com.globalspace.miljonsales.model.StockistData;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

public class StockistListActivity extends AppCompatActivity implements OrderStockistAdapter.OnItemClicked {
    private ProgressBar progress_bar;
    private String chem_id, SESSION_ID;
    private OrderStockistAdapter adapter;
    private RecyclerView stockistRv;
    private ImageView backBtn;
    private TextView searchBtn,clearBtn,closeBtn;
    private AppBarLayout toolbar;
    private Toolbar searchToolbar;
    public ArrayList<AddProductToCart> cartData = new ArrayList<>();
    private EditText searchview;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stockist_list);
        setComponents();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StockistListActivity.this.finish();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                    toolbar.setVisibility(View.GONE);
                    searchToolbar.setVisibility(View.VISIBLE);
                    circleReveal(R.id.searchtoolbar, 1, true, true);
                    searchview.setHint("Search Stockist");
                    searchview.addTextChangedListener(filterTextWatcher);

            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                searchview.setText("");
                circleReveal(R.id.searchtoolbar, 1, true, false);
                searchToolbar.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
            }
        });



        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchview.setText("");

            }
        });





    }



    private void setComponents() {
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        searchBtn = (TextView) findViewById(R.id.searchIcon);
        toolbar = (AppBarLayout) findViewById(R.id.toolbar);
        searchToolbar = findViewById(R.id.searchtoolbar);
        closeBtn = (TextView) findViewById(R.id.search_back);
        closeBtn.setTypeface(MiljonOffer.solidFont);
        clearBtn = (TextView) findViewById(R.id.search_clear);
        clearBtn.setTypeface(MiljonOffer.solidFont);
        searchview = (EditText) findViewById(R.id.search_view);
        searchBtn.setTypeface(MiljonOffer.solidFont);
        stockistRv = (RecyclerView) findViewById(R.id.stockistRv);
        stockistRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        chem_id = getIntent().getExtras().getString("Chem_org_id");
        SESSION_ID = getIntent().getExtras().getString("SESSION_ID");
        getStockistList();
    }


    private void getStockistList() {
        progress_bar.setVisibility(View.VISIBLE);
        cartData = new DBAdapter(this).getAllCartProducts();
        String productData = "";
        for (int i = 0; i < cartData.size(); i++) {
            if (productData == "") {
                productData = cartData.get(i).getProductId() + "|" + cartData.get(i).getSelectedQuantity();
            } else {
                productData = productData + "," + cartData.get(i).getProductId() + "|" + cartData.get(i).getSelectedQuantity();
            }
        }

        Call<CartStockistListResponse> call = ApiUtils.getWalletData().setStockistListOrder("getCoveredStockist", SESSION_ID, chem_id,productData);
        call.enqueue(new Callback<CartStockistListResponse>() {
            @Override
            public void onResponse(Call<CartStockistListResponse> call, Response<CartStockistListResponse> response) {
                if (response.body().getStatus().equals("200")) {
                    adapter = new OrderStockistAdapter(response.body().getStockist_List());
                    stockistRv.setAdapter(adapter);
                    adapter.setOnItemSelectedListener(StockistListActivity.this);

                } else {
                    Toast.makeText(StockistListActivity.this, "Something went wrong !!", Toast.LENGTH_SHORT).show();
                }
                progress_bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CartStockistListResponse> call, Throwable throwable) {
                progress_bar.setVisibility(View.GONE);
                Toast.makeText(StockistListActivity.this, "Something went wrong !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClicked(StockistData data) {
        setOrder(data);
    }


    private void setOrder(StockistData data) {
        progress_bar.setVisibility(View.VISIBLE);
        cartData = new DBAdapter(this).getAllCartProducts();
        String productData = "";
        for (int i = 0; i < cartData.size(); i++) {
            if (productData == "") {
                productData = cartData.get(i).getProductId() + "|" + cartData.get(i).getSelectedQuantity() + "|" + cartData.get(i).getSchemeId();
            } else {
                productData = productData + "," + cartData.get(i).getProductId() + "|" + cartData.get(i).getSelectedQuantity() + "|" + cartData.get(i).getSchemeId();
            }
        }

        Call<OrderResponse> call = ApiUtils.getWalletData().setOrderData("setOrder",SESSION_ID,chem_id,productData,data.getSTK_ORG_ID());
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.body().getStatus().equals("200")){
                    Toast.makeText(StockistListActivity.this, response.body().getMESSAGE(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.putExtra("Result","Order Placed");
                    setResult(1,i);
                    StockistListActivity.this.finish();
                }else{
                    Toast.makeText(StockistListActivity.this, response.body().getMESSAGE(), Toast.LENGTH_SHORT).show();
                }
                progress_bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable throwable) {
                progress_bar.setVisibility(View.GONE);
                Toast.makeText(StockistListActivity.this, "Something went wrong !!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

                    if (!isShow)

                    {

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
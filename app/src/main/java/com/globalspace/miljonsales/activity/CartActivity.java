package com.globalspace.miljonsales.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.adapter.CartAdapter;
import com.globalspace.miljonsales.database.DBAdapter;
import com.globalspace.miljonsales.model.AddProductToCart;
import com.globalspace.miljonsales.model.OrderResponse;
import com.globalspace.miljonsales.retrofit.ApiUtils;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnChangeListener {
    private DBAdapter dbAdapter;
    public static ArrayList<AddProductToCart> cartData = new ArrayList<>();
    private RecyclerView lst_rv;
    private CartAdapter adapter;
    private TextView tv_mrp, tv_tax, tv_nettotal, tv_roundoff, tv_tot;
    private Button placeOrderBtn;
    String org_id = "";
    String chem_id = "";
    String SESSION_ID = "";
    private ProgressBar progress_bar;
    private ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cart);
        setComponents();
        chem_id = getIntent().getExtras().getString("Chem_org_id");
        SESSION_ID = getIntent().getExtras().getString("SESSION_ID");
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CartActivity.this,StockistListActivity.class);
                i.putExtra("Chem_org_id",chem_id);
                i.putExtra("SESSION_ID",SESSION_ID);
                startActivityForResult(i,1);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void setOrder() {
        progress_bar.setVisibility(View.VISIBLE);
        String productData = "";
        for (int i = 0; i < cartData.size(); i++) {
            if (productData == "") {
                productData = cartData.get(i).getProductId() + "|" + cartData.get(i).getSelectedQuantity() + "|" + cartData.get(i).getPts();
            } else {
                productData = productData + "^" + cartData.get(i).getProductId() + "|" + cartData.get(i).getSelectedQuantity() + "|" + cartData.get(i).getPts();
            }
        }

        Call<OrderResponse> call = ApiUtils.getWalletData().setOrderData("setOrder",SESSION_ID,chem_id,productData,"ORG_40");
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.body().getStatus().equals("200")){
                    Toast.makeText(CartActivity.this, response.body().getMESSAGE(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.putExtra("Result","Order Placed");
                    setResult(1,i);
                    CartActivity.this.finish();
                }else{
                    Toast.makeText(CartActivity.this, response.body().getMESSAGE(), Toast.LENGTH_SHORT).show();
                }
                progress_bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable throwable) {
                progress_bar.setVisibility(View.GONE);
                Toast.makeText(CartActivity.this, "Something went wrong !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            if (data.getExtras().getString("Result").equals("Order Placed")) {
                Intent i = new Intent();
                i.putExtra("Result","Order Placed");
                setResult(1,i);
                CartActivity.this.finish();
            }
        }
    }

    private void setComponents() {
        dbAdapter = new DBAdapter(this);
        cartData = dbAdapter.getAllCartProducts();
        lst_rv = findViewById(R.id.lst_rv);
        tv_mrp = findViewById(R.id.tv_discountedPrice);
        backbtn = findViewById(R.id.ivAddToCartNext);
        progress_bar = findViewById(R.id.progress_bar);
        placeOrderBtn = findViewById(R.id.btnPayLater);
        tv_tax = findViewById(R.id.tv_tax);
        tv_nettotal = findViewById(R.id.tv_nettotal);
        tv_roundoff = findViewById(R.id.tv_roundoff);
        tv_tot = findViewById(R.id.tv_total);
        lst_rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new CartAdapter(this);
        adapter.setOnItemChangeListener(this);
        lst_rv.setAdapter(adapter);
        calculateTotal();
    }

    private void calculateTotal() {
        float totalValue = 0.0f, taxValue = 0.0f, netValue = 0.0f, mrpValue = 0.0f;
        for (int pos = 0; pos < cartData.size(); pos++) {
            mrpValue = mrpValue + (Float.valueOf(CartActivity.cartData.get(pos).getMrp()));
            float totalValueCount = 0.0f;
            if (!CartActivity.cartData.get(pos).getDiscount().equals("")) {
                float discount = Float.valueOf(CartActivity.cartData.get(pos).getPts()) * (Float.valueOf(CartActivity.cartData.get(pos).getDiscount()) / 100);
                totalValueCount = Integer.valueOf(CartActivity.cartData.get(pos).getSelectedQuantity()) * discount;
            }else {
                totalValueCount = Integer.valueOf(CartActivity.cartData.get(pos).getSelectedQuantity()) * Float.valueOf(CartActivity.cartData.get(pos).getPts());
            }
            totalValue = totalValue + totalValueCount;
            taxValue = taxValue + ((totalValue * 5) / 100);
            netValue = netValue + (totalValue + taxValue);
        }

        tv_mrp.setText(String.format("%.0f", totalValue));
        tv_tax.setText(String.format("%.0f", taxValue));
        tv_nettotal.setText(String.format("%.0f", netValue));
        int total = Math.round(netValue);
        tv_roundoff.setText(String.valueOf(netValue - total));
        tv_tot.setText("" + total);
    }

    @Override
    public void onChange() {
        calculateTotal();
        if(dbAdapter.cartSize() == 0){
            Intent i = new Intent();
            i.putExtra("Result","All Delete");
            setResult(1,i);
            CartActivity.this.finish();
        }
    }
}
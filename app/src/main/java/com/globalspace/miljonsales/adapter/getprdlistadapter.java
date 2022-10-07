package com.globalspace.miljonsales.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.database.Constants;
import com.globalspace.miljonsales.database.DBAdapter;
import com.globalspace.miljonsales.model.AddProductToCart;
import com.globalspace.miljonsales.model.discountProductInfo;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.utils.Internet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getprdlistadapter extends RecyclerView.Adapter<getprdlistadapter.ViewHolder> {
    private List<discountProductInfo> searchprdlist;
    public static String SelectedDiscount = "";
    ApiInterface apiInterface;
    String org_id = "";
    String user_id = "";
    private DBAdapter dbAdapter;

    Context context;
    public ProgressBar mainscrennprogress_img;
    String isstockupdate = "";
    private ImageView cartIcon;
    private TextView cartTv;
    private Typeface font;


    public getprdlistadapter(List<discountProductInfo> searchprdlist, Activity context, String org_id, ProgressBar mainscrennprogress_img,
                             String userid) {
        this.searchprdlist = searchprdlist;
        this.context = context;
        this.org_id = org_id;
        this.user_id = userid;
        this.mainscrennprogress_img = mainscrennprogress_img;
        this.isstockupdate = isstockupdate;
        dbAdapter = new DBAdapter(context);
        cartIcon = (ImageView) context.findViewById(R.id.cartIcon);
        cartTv = (TextView) context.findViewById(R.id.cartCount);

        font = Typeface.createFromAsset(context.getAssets(), "fa-solid-900.ttf");
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.getprdlistitemlayout, parent, false);
        return new getprdlistadapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        final int position = holder.getAdapterPosition();
        holder.setIsRecyclable(false);

        holder.mIdView.setText(searchprdlist.get(position).getProdName());
        holder.mContentView.setText(searchprdlist.get(position).getProdComposition());
        holder.mrp.setText("MRP - \u20B9 " + searchprdlist.get(position).getProdMRP());
        holder.ptr.setText("PTR - \u20B9 " + searchprdlist.get(position).getProdPTS());

        holder.mContentView.setTag(position);

        if (searchprdlist.get(i).getScheme().equals("0")) {
            holder.schemeLL.setVisibility(View.GONE);
        } else {
            holder.schemeLL.setVisibility(View.VISIBLE);
            holder.schemeTv.setText(R.string.schemes_icon);
            holder.schemeTv.setTypeface(font);
            holder.schemeTv.append(" " + searchprdlist.get(i).getScheme());
        }

        if (searchprdlist.get(i).isIs_selected()) {
            holder.addtocart.setText("Added");
            holder.cartLT.setVisibility(View.VISIBLE);
            holder.et_qty.setText(searchprdlist.get(position).getSelectedQty());
        } else {
            holder.cartLT.setVisibility(View.GONE);
            holder.addtocart.setText("Add to cart");
        }


        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int count = holder.getAdapterPosition();
                if (!searchprdlist.get(count).isIs_selected()) {
                    searchprdlist.get(count).setIs_selected(true);
                    holder.addtocart.setText("Added");
                    holder.cartLT.setVisibility(View.VISIBLE);
                    dbAdapter.addProductsToCart(searchprdlist.get(count));
                } else {
                    dbAdapter.deleteProductFromCart(searchprdlist.get(count).getProductId());
                    holder.et_qty.setText("1");
                    searchprdlist.get(count).setIs_selected(false);
                    holder.cartLT.setVisibility(View.GONE);
                    holder.addtocart.setText("Add to cart");
                }
                if (dbAdapter.cartSize() > 0) {
                    cartIcon.setVisibility(View.VISIBLE);
                    cartTv.setVisibility(View.VISIBLE);
                } else {
                    cartIcon.setVisibility(View.GONE);
                    cartTv.setVisibility(View.GONE);
                }
                cartTv.setText(String.valueOf(dbAdapter.cartSize()));
            }
        });

        holder.iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int count = holder.getAdapterPosition();
                int qty = Integer.valueOf(holder.et_qty.getText().toString());
                if (qty == 1) {
                    dbAdapter.deleteProductFromCart(searchprdlist.get(count).getProductId());
                    searchprdlist.get(count).setIs_selected(false);
                    holder.cartLT.setVisibility(View.GONE);
                    holder.addtocart.setText("Add to cart");
                    if (dbAdapter.cartSize() > 0) {
                        cartIcon.setVisibility(View.VISIBLE);
                        cartTv.setVisibility(View.VISIBLE);
                    } else {
                        cartIcon.setVisibility(View.GONE);
                        cartTv.setVisibility(View.GONE);
                    }
                } else {
                    holder.et_qty.setText("" + (qty - 1));
                }
                cartTv.setText(String.valueOf(dbAdapter.cartSize()));

            }
        });

        holder.iv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.valueOf(holder.et_qty.getText().toString());
                holder.et_qty.setText("" + (qty + 1));

            }
        });


        holder.et_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                final int count = holder.getAdapterPosition();
                searchprdlist.get(count).setSelectedQty(editable.toString());
                dbAdapter.updateCartQuantity(searchprdlist.get(count).getProductId(), editable.toString());


                int selectedqty = Integer.parseInt(searchprdlist.get(count).getSelectedQty());
                int schemvalue = Integer.parseInt(searchprdlist.get(count).getSchemeValue());
                int schemqty = Integer.parseInt(searchprdlist.get(count).getSchemeQuantity());
                if (schemqty != 0) {
                    if (selectedqty > schemqty || selectedqty == schemqty) {
                        holder.freeLL.setVisibility(View.VISIBLE);
                        int calculatedschemvalue = selectedqty / schemqty * schemvalue;
                        holder.freeTv.setText(String.valueOf(calculatedschemvalue));
                    } else {
                        holder.freeLL.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return searchprdlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final LinearLayout cartLT;
        public final TextView addtocart;
        public final TextView mrp, schemeTv,freeTv;
        public final TextView ptr;
        public final ImageView iv_minus;
        public final ImageView iv_plus;
        public final EditText et_qty;
        public final LinearLayout schemeLL, freeLL;
        Button update;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            addtocart = (TextView) view.findViewById(R.id.cbSearchChemistProduct);
            mrp = (TextView) view.findViewById(R.id.tv_mrp);
            ptr = (TextView) view.findViewById(R.id.tv_ptr);
            schemeTv = (TextView) view.findViewById(R.id.schemeTv);
            freeTv = (TextView) view.findViewById(R.id.freeTv);
            cartLT = (LinearLayout) view.findViewById(R.id.cartLT);
            iv_minus = (ImageView) view.findViewById(R.id.iv_minus);
            iv_plus = (ImageView) view.findViewById(R.id.iv_plus);
            et_qty = (EditText) view.findViewById(R.id.et_qty);
            schemeLL = (LinearLayout) view.findViewById(R.id.schemeLL);
            freeLL = (LinearLayout) view.findViewById(R.id.freeLL);
        }


    }


}

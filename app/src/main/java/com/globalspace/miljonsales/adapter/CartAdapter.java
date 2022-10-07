package com.globalspace.miljonsales.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.activity.CartActivity;
import com.globalspace.miljonsales.database.DBAdapter;
import com.globalspace.miljonsales.model.AddProductToCart;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartVH> {

    private Activity activity;
    private DBAdapter dbAdapter;
    private OnChangeListener mListener;

    public interface OnChangeListener {
        void onChange();
    }

    public void setOnItemChangeListener(OnChangeListener listener) {
        mListener = listener;
    }

    public CartAdapter(Activity activity) {
        this.activity = activity;
        dbAdapter = new DBAdapter(activity);
    }


    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cart, viewGroup, false);
        return new CartVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartVH holder, int i) {
        final int position = holder.getAdapterPosition();
        holder.et_qty.setTag(position);

        holder.item_name.setText(CartActivity.cartData.get(i).productName);
        holder.et_qty.setText(CartActivity.cartData.get(i).getSelectedQuantity());
        holder.mrpValue.setText(CartActivity.cartData.get(i).getMrp());
        holder.ptrValue.setText(CartActivity.cartData.get(i).getPts());

        int selectedqty = Integer.parseInt(CartActivity.cartData.get(i).getSelectedQuantity());
        int schemvalue = Integer.parseInt(CartActivity.cartData.get(i).getSchemeValue());
        int schemqty = Integer.parseInt(CartActivity.cartData.get(i).getSchemeQuantity());

        if (schemqty != 0) {
            if (selectedqty > schemqty || selectedqty == schemqty) {
                holder.freeLL.setVisibility(View.VISIBLE);
                int calculatedschemvalue = selectedqty / schemqty * schemvalue;
                holder.freeTv.setText(String.valueOf(calculatedschemvalue));
            } else {
                holder.freeLL.setVisibility(View.GONE);
            }
        }

        if (!CartActivity.cartData.get(i).getDiscount().equals("")) {
            holder.discountValue.setText(CartActivity.cartData.get(i).discount + "%");
            holder.discountLabel.setVisibility(View.GONE);
            holder.discountValue.setVisibility(View.GONE);
            float discount = Float.valueOf(CartActivity.cartData.get(i).getPts()) * (Float.valueOf(CartActivity.cartData.get(i).getDiscount()) / 100);
            holder.tvTotal.setText("Qty * PTR : " + CartActivity.cartData.get(i).getSelectedQuantity() + " * " + String.format("%.0f", discount));
        } else {
            holder.discountLabel.setVisibility(View.GONE);
            holder.discountValue.setVisibility(View.GONE);
            holder.tvTotal.setText("Qty * PTR : " + CartActivity.cartData.get(i).getSelectedQuantity() + " * " + CartActivity.cartData.get(i).getPts());
        }

        holder.totalValue.setText(calculateTotalValue(i));
        holder.taxValue.setText(calculateTaxValue(i));
        holder.netTotalValue.setText(calculateNetValue(i));

        holder.et_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int pos = holder.getAdapterPosition();
                CartActivity.cartData.get(pos).setSelectedQuantity(editable.toString());
                dbAdapter.updateCartQuantity(CartActivity.cartData.get(pos).productId, editable.toString());
                if (!CartActivity.cartData.get(pos).getDiscount().equals("")) {
                    holder.discountValue.setText(CartActivity.cartData.get(pos).discount + "%");
                    holder.discountLabel.setVisibility(View.GONE);
                    holder.discountValue.setVisibility(View.GONE);
                    float discount = Float.valueOf(CartActivity.cartData.get(pos).getPts()) * (Float.valueOf(CartActivity.cartData.get(pos).getDiscount()) / 100);
                    holder.tvTotal.setText("Qty * PTR : " + CartActivity.cartData.get(pos).getSelectedQuantity() + " * " + String.format("%.0f", discount));
                } else {
                    holder.discountLabel.setVisibility(View.GONE);
                    holder.discountValue.setVisibility(View.GONE);
                    holder.tvTotal.setText("Qty * PTR : " + CartActivity.cartData.get(pos).getSelectedQuantity() + " * " + CartActivity.cartData.get(pos).getPts());
                }
                holder.totalValue.setText(calculateTotalValue(pos));
                holder.taxValue.setText(calculateTaxValue(pos));
                holder.netTotalValue.setText(calculateNetValue(pos));
                int selectedqty1 = Integer.parseInt(CartActivity.cartData.get(pos).getSelectedQuantity());
                int schemvalue1 = Integer.parseInt(CartActivity.cartData.get(pos).getSchemeValue());
                int schemqty1 = Integer.parseInt(CartActivity.cartData.get(pos).getSchemeQuantity());
                if (schemqty1 != 0) {
                    if (selectedqty1 > schemqty1 || selectedqty1 == schemqty1) {
                        holder.freeLL.setVisibility(View.VISIBLE);
                        int calculatedschemvalue1 = selectedqty1 / schemqty1 * schemvalue1;
                        holder.freeTv.setText(String.valueOf(calculatedschemvalue1));
                    } else {
                        holder.freeLL.setVisibility(View.GONE);
                    }
                }
                mListener.onChange();
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                if (CartActivity.cartData.get(pos).getSelectedQuantity().equals("1")) {
                    dbAdapter.deleteProductFromCart(CartActivity.cartData.get(pos).productId);
                    CartActivity.cartData.remove(pos);
                    notifyDataSetChanged();
                    mListener.onChange();
                } else {
                    holder.et_qty.setText(String.valueOf(Integer.valueOf(CartActivity.cartData.get(pos).getSelectedQuantity()) - 1));
                }
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                dbAdapter.deleteProductFromCart(CartActivity.cartData.get(pos).productId);
                CartActivity.cartData.remove(pos);
                notifyDataSetChanged();
                mListener.onChange();
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                holder.et_qty.setText(String.valueOf(Integer.valueOf(CartActivity.cartData.get(pos).getSelectedQuantity()) + 1));
            }
        });


    }

    private String calculateTotalValue(int pos) {
        float totalValue = 0.0f;
        if (!CartActivity.cartData.get(pos).getDiscount().equals("")) {
            float discount = Float.valueOf(CartActivity.cartData.get(pos).getPts()) * (Float.valueOf(CartActivity.cartData.get(pos).getDiscount()) / 100);
            totalValue = Integer.valueOf(CartActivity.cartData.get(pos).getSelectedQuantity()) * discount;
        } else {
            totalValue = Integer.valueOf(CartActivity.cartData.get(pos).getSelectedQuantity()) * Float.valueOf(CartActivity.cartData.get(pos).getPts());
        }
        return String.format("%.0f", totalValue);
    }

    private String calculateTaxValue(int pos) {
        float totalValue = 0.0f;
        if (!CartActivity.cartData.get(pos).getDiscount().equals("")) {
            float discount = Float.valueOf(CartActivity.cartData.get(pos).getPts()) * (Float.valueOf(CartActivity.cartData.get(pos).getDiscount()) / 100);
            totalValue = Integer.valueOf(CartActivity.cartData.get(pos).getSelectedQuantity()) * discount;
        } else {
            totalValue = Integer.valueOf(CartActivity.cartData.get(pos).getSelectedQuantity()) * Float.valueOf(CartActivity.cartData.get(pos).getPts());
        }
        float taxValue = (totalValue * 5) / 100;
        return String.format("%.0f", taxValue);
    }

    private String calculateNetValue(int pos) {
        float totalValue = 0.0f;
        if (!CartActivity.cartData.get(pos).getDiscount().equals("")) {
            float discount = Float.valueOf(CartActivity.cartData.get(pos).getPts()) * (Float.valueOf(CartActivity.cartData.get(pos).getDiscount()) / 100);
            totalValue = Integer.valueOf(CartActivity.cartData.get(pos).getSelectedQuantity()) * discount;
        } else {
            totalValue = Integer.valueOf(CartActivity.cartData.get(pos).getSelectedQuantity()) * Float.valueOf(CartActivity.cartData.get(pos).getPts());
        }
        float taxValue = (totalValue * 5) / 100;
        float netValue = totalValue + taxValue;
        return String.format("%.0f", netValue);
    }


    @Override
    public int getItemCount() {
        return CartActivity.cartData.size();
    }

    public class CartVH extends RecyclerView.ViewHolder {
        public TextView item_name, desc, mrpValue, ptrValue, tvTotal, totalValue, taxValue, netTotalValue, freeTv, discountValue, discountLabel,
                newPTRValue;
        public ImageView deleteBtn, minus, plus;
        public EditText et_qty;
        public LinearLayout freeLL, discountLL;

        public CartVH(@NonNull View v) {
            super(v);
            item_name = v.findViewById(R.id.item_name);
            desc = v.findViewById(R.id.content);
            mrpValue = v.findViewById(R.id.mrpValue);
            ptrValue = v.findViewById(R.id.ptrValue);
            tvTotal = v.findViewById(R.id.tvTotal);
            totalValue = v.findViewById(R.id.totalValue);
            discountValue = v.findViewById(R.id.discountValue);
            discountLabel = v.findViewById(R.id.tv_discount);
            taxValue = v.findViewById(R.id.taxValue);
            netTotalValue = v.findViewById(R.id.netTotalValue);
            newPTRValue = v.findViewById(R.id.newPTRValue);
            deleteBtn = v.findViewById(R.id.deleteBtn);
            minus = v.findViewById(R.id.iv_minus);
            plus = v.findViewById(R.id.iv_plus);
            freeLL = v.findViewById(R.id.freeLL);
            discountLL = v.findViewById(R.id.discountLL);
            freeTv = v.findViewById(R.id.freeTv);
            et_qty = v.findViewById(R.id.et_qty);
        }
    }
}

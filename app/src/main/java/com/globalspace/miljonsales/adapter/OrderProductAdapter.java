package com.globalspace.miljonsales.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.model.OrderDetailsResponse;

import java.util.ArrayList;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.OrderProductVH> {

    private Activity activity;
    private ArrayList<OrderDetailsResponse.ProductInfo> productInfos;


    public OrderProductAdapter(Activity activity, ArrayList<OrderDetailsResponse.ProductInfo> productInfos) {
        this.activity = activity;
        this.productInfos = productInfos;
    }

    @NonNull
    @Override
    public OrderProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_list, parent, false);
        OrderProductVH vh = new OrderProductVH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductVH holder, int position) {
        if (productInfos.get(position).getProductPrice().equals("0.00"))
          holder.tvfree.setVisibility(View.VISIBLE);
        else
            holder.tvfree.setVisibility(View.INVISIBLE);
            holder.productName.setText(productInfos.get(position).getProductName());
        holder.quantity.setText(productInfos.get(position).getProductQuantity());
        holder.price.setText(productInfos.get(position).getProductPrice());
    }

    @Override
    public int getItemCount() {
        return productInfos.size();
    }

    public class OrderProductVH extends RecyclerView.ViewHolder {

        private TextView productName, quantity, price,tvfree;

        public OrderProductVH(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.pname);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            price = (TextView) itemView.findViewById(R.id.price);
            tvfree = (TextView) itemView.findViewById(R.id.tvfree);
        }
    }

}

package com.globalspace.miljonsales.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.globalspace.miljonsales.MiljonOffer;
import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.activity.StockUpdate;
import com.squareup.picasso.Picasso;

public class StockUpdateAdapter extends RecyclerView.Adapter<StockUpdateAdapter.StockUpdateVH> {

    private Activity activity;

    public StockUpdateAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public StockUpdateVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_availability, parent, false);
        return new StockUpdateVH(v);
    }

    @Override
    public void onBindViewHolder(final StockUpdateVH holder, final int position) {

        holder.availableIcon.setTypeface(MiljonOffer.solidFont);
        holder.unavailableIcon.setTypeface(MiljonOffer.solidFont);
        holder.prodName.setText(StockUpdate.productList.get(position).getProdName());
        holder.description.setText(StockUpdate.productList.get(position).getProdDesc());
        holder.pack.setText(StockUpdate.productList.get(position).getPack());
        holder.ptr.setText(StockUpdate.productList.get(position).getPts());
        holder.mrp.setText(StockUpdate.productList.get(position).getMrp());
        holder.availabilityTV.setText(StockUpdate.productList.get(position).getAvailable());


        Picasso.get().load(StockUpdate.productList.get(position).getProdImage()).into(holder.prodImage);

        holder.availableLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockUpdate.productList.get(position).setAvailable("Available");
                holder.availabilityTV.setText(StockUpdate.productList.get(position).getAvailable());
            }
        });

        holder.unavailableLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockUpdate.productList.get(position).setAvailable("Unavailable");
                holder.availabilityTV.setText(StockUpdate.productList.get(position).getAvailable());
            }
        });


    }

    @Override
    public int getItemCount() {
        return StockUpdate.productList.size();
    }

    public class StockUpdateVH extends RecyclerView.ViewHolder {

        private TextView prodName, description, pack, ptr, mrp, availableIcon, unavailableIcon, availabilityTV;
        private ImageView prodImage;
        private LinearLayout availableLL, unavailableLL;

        public StockUpdateVH(View itemView) {
            super(itemView);

            prodImage = (ImageView) itemView.findViewById(R.id.iv_pdtimg);
            prodName = (TextView) itemView.findViewById(R.id.tv_pdtname);
            description = (TextView) itemView.findViewById(R.id.tv_pdtdesc);
            pack = (TextView) itemView.findViewById(R.id.tv_pack);
            ptr = (TextView) itemView.findViewById(R.id.tv_ptr);
            mrp = (TextView) itemView.findViewById(R.id.tv_mrp);
            availableIcon = (TextView) itemView.findViewById(R.id.availableIcon);
            availableLL = (LinearLayout) itemView.findViewById(R.id.availableLL);
            unavailableIcon = (TextView) itemView.findViewById(R.id.unavailableIcon);
            unavailableLL = (LinearLayout) itemView.findViewById(R.id.unavailableLL);
            availabilityTV = (TextView) itemView.findViewById(R.id.availabilityTV);
        }
    }
}

package com.globalspace.miljonsales.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.fragment.FragmentStockistList;
import com.globalspace.miljonsales.model.STOCKIESTINFO;
import com.globalspace.miljonsales.model.StockistData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderStockistAdapter extends RecyclerView.Adapter<OrderStockistAdapter.OrderStockistVH> implements Filterable {

    private ArrayList<StockistData> stockiestinfos;
    private OnItemClicked mListener;
    FriendFilter mFriendFilter;

    public interface OnItemClicked {
        void onItemClicked(StockistData data);
    }

    public void setOnItemSelectedListener(OnItemClicked listener) {
        mListener = listener;
    }

    public OrderStockistAdapter(ArrayList<StockistData> stockistInfo) {
        this.stockiestinfos = stockistInfo;
    }


    @NonNull
    @Override
    public OrderStockistVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new OrderStockistVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderStockistVH holder, final int i) {
        holder.pName.setText(stockiestinfos.get(i).getORG_NAME());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClicked(stockiestinfos.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return stockiestinfos.size();
    }

    public class OrderStockistVH extends RecyclerView.ViewHolder {
        private TextView tv_version, pName;

        public OrderStockistVH(@NonNull View itemView) {
            super(itemView);
            tv_version = itemView.findViewById(R.id.tv_version);
            tv_version.setVisibility(View.GONE);
            pName = itemView.findViewById(R.id.pName);
        }
    }


    @Override
    public Filter getFilter() {
        if (mFriendFilter == null) {
            mFriendFilter = new FriendFilter(OrderStockistAdapter.this, stockiestinfos);
        }
        return mFriendFilter;
    }

    private class FriendFilter extends Filter {

        public List<StockistData> mFilteredList;
        private OrderStockistAdapter mRecyclerAdapter;
        private List<StockistData> mArrayList;


        private FriendFilter(OrderStockistAdapter recyclerAdapter, List<StockistData> arrayList) {
            mRecyclerAdapter = recyclerAdapter;
            mArrayList = new ArrayList<>(arrayList);
            mFilteredList = new ArrayList<>();

        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            mFilteredList.clear();
            FilterResults filterResults = new FilterResults();
            if (constraint.length() == 0) {
                mFilteredList.addAll(mArrayList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final StockistData stockist_info : mArrayList) {
                    if (stockist_info.getORG_NAME().toLowerCase().contains(filterPattern)) {
                        mFilteredList.add(stockist_info);
                    }
                }
            }

            filterResults.values = mFilteredList;

            //filterResults.count = mFilteredList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mRecyclerAdapter.stockiestinfos.clear();
            mRecyclerAdapter.stockiestinfos.addAll((Collection<? extends StockistData>) results.values);
            mRecyclerAdapter.notifyDataSetChanged();
        }
    }
}

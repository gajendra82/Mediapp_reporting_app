package com.globalspace.miljonsales.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.activity.OrderDetails;
import com.globalspace.miljonsales.POBActivity;
import com.globalspace.miljonsales.model.DailyPobResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DailyPobAdapter extends RecyclerView.Adapter<DailyPobAdapter.DailyPobVH> implements Filterable {
    private Activity activity;
    private FriendFilter mFriendFilter;
    private List<DailyPobResponse._0> dailyPobResponses;

    public DailyPobAdapter(Activity activity, List<DailyPobResponse._0> dailyPobResponses) {
        this.activity = activity;
        this.dailyPobResponses = dailyPobResponses;
        calculateTotalAmount(dailyPobResponses);
    }

    @NonNull
    @Override
    public DailyPobAdapter.DailyPobVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pob, parent, false);
        DailyPobVH vh = new DailyPobVH(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull DailyPobAdapter.DailyPobVH holder, final int position) {
        holder.chemistName.setText(dailyPobResponses.get(position).getCHEMISTNAME());
        holder.chemistName.setPaintFlags(holder.chemistName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.stockiestName.setText(dailyPobResponses.get(position).getSTOCKIESTNAME());
        holder.orderId.setText(dailyPobResponses.get(position).getORDERID());
        holder.status.setText(dailyPobResponses.get(position).getTRANSACTIONSTATUS());
        holder.amount.setText(dailyPobResponses.get(position).getPURCHASEORDERBOOKING());
        holder.date.setText(dailyPobResponses.get(position).getTRANSACTIONDATE());
        holder.tvpobaddress.setText(dailyPobResponses.get(position).getADDRESS());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, OrderDetails.class);
                i.putExtra("OrderId", dailyPobResponses.get(position).getORDERID());
                i.putExtra("Status", dailyPobResponses.get(position).getTRANSACTIONSTATUS());
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dailyPobResponses.size();
    }



    public class DailyPobVH extends RecyclerView.ViewHolder {
        private TextView chemistName, date, orderId, stockiestName, amount, status,tvpobaddress;
        public DailyPobVH(View itemView) {
            super(itemView);
            chemistName = (TextView) itemView.findViewById(R.id.stockistName);
            tvpobaddress = (TextView) itemView.findViewById(R.id.tvpobaddress);
            date = (TextView) itemView.findViewById(R.id.date);
            stockiestName = (TextView) itemView.findViewById(R.id.stockiestName);
            orderId = (TextView) itemView.findViewById(R.id.orderId);
            amount = (TextView) itemView.findViewById(R.id.amount);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }

    @Override
    public Filter getFilter() {
        if (mFriendFilter == null) {
            mFriendFilter = new DailyPobAdapter.FriendFilter(this, dailyPobResponses);
        }

        return mFriendFilter;
    }
    private class FriendFilter extends Filter {

        public List<DailyPobResponse._0> mFilteredList;
        private DailyPobAdapter mRecyclerAdapter;
        private List<DailyPobResponse._0> mArrayList;
        private List<DailyPobResponse._0> originalList;


        private FriendFilter(DailyPobAdapter recyclerAdapter, List<DailyPobResponse._0> arrayList) {
            mRecyclerAdapter = recyclerAdapter;
            mArrayList = new ArrayList<>(arrayList);
            originalList = new ArrayList<>(arrayList);
            mFilteredList = new ArrayList<>();

        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            mFilteredList.clear();
            FilterResults filterResults = new FilterResults();
            if (constraint.length() == 0) {
                if (POBActivity.FilterType.equals("Date")){
                    mArrayList.clear();
                    mArrayList.addAll(originalList);
                    mFilteredList.addAll(originalList);
                }else {
                    mFilteredList.addAll(mArrayList);
                }
                calculateTotalAmount(mFilteredList);
            }
            else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final DailyPobResponse._0 monthlyPOB : mArrayList) {
                    if (POBActivity.FilterType.equals("ChemistName")) {
                        if (monthlyPOB.getCHEMISTNAME().toLowerCase().contains(filterPattern)) {
                            mFilteredList.add(monthlyPOB);
                        }
                    }else if (POBActivity.FilterType.equals("Date")) {
                        if (monthlyPOB.getTRANSACTIONDATE().toLowerCase().contains(filterPattern)) {
                            mFilteredList.add(monthlyPOB);
                        }
                    }else{
                        if (monthlyPOB.getTRANSACTIONSTATUS().toLowerCase().contains(filterPattern)) {
                            mFilteredList.add(monthlyPOB);
                        }
                    }
                }

                if (POBActivity.FilterType.equals("Date")) {
                    mArrayList.clear();
                    mArrayList.addAll(mFilteredList);
                }
            }

            calculateTotalAmount(mFilteredList);
            filterResults.values = mFilteredList;

            //filterResults.count = mFilteredList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mRecyclerAdapter.dailyPobResponses.clear();
            mRecyclerAdapter.dailyPobResponses.addAll((Collection<? extends DailyPobResponse._0>) results.values);
            mRecyclerAdapter.notifyDataSetChanged();
        }


    }

    private void calculateTotalAmount(List<DailyPobResponse._0> info){
        Float totalAmount = 0.0f;

        for (int i=0;i<info.size();i++){
            totalAmount = totalAmount + Float.valueOf(info.get(i).getPURCHASEORDERBOOKING());
        }

        POBActivity.totalAmount.setText(totalAmount.toString());
    }
}

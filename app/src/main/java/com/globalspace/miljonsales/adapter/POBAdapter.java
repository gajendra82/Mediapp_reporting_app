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
import android.widget.Toast;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.activity.OrderDetails;
import com.globalspace.miljonsales.POBActivity;
import com.globalspace.miljonsales.model.POBResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class POBAdapter extends RecyclerView.Adapter<POBAdapter.POBViewHolder> implements Filterable {

    private Activity activity;
    private List<POBResponse.MONTHLY> monthlyPOB;
    private FriendFilter mFriendFilter;

    public POBAdapter(Activity activity, List<POBResponse.MONTHLY> monthlyPOB) {
        this.activity = activity;
        this.monthlyPOB = monthlyPOB;
        calculateTotalAmount(monthlyPOB);
    }

    @NonNull
    @Override
    public POBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pob, parent, false);
        POBViewHolder vh = new POBViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull POBViewHolder holder, final int position) {
        if(monthlyPOB.get(position).getORDERID().equals("")){
            holder.chemistName.setVisibility(View.GONE);
            holder.stockiestName.setVisibility(View.GONE);
            holder.orderId.setVisibility(View.GONE);
            holder.status.setVisibility(View.GONE);
            holder.amount.setVisibility(View.GONE);
            holder.date.setVisibility(View.GONE);
            holder.tvpobaddress.setVisibility(View.GONE);
        }else {
            holder.chemistName.setText(monthlyPOB.get(position).getCHEMISTNAME());
            holder.chemistName.setPaintFlags(holder.chemistName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.stockiestName.setText(monthlyPOB.get(position).getSTOCKIESTNAME());
            holder.orderId.setText(monthlyPOB.get(position).getORDERID());
            holder.status.setText(monthlyPOB.get(position).getTRANSACTIONSTATUS());
            holder.amount.setText(monthlyPOB.get(position).getPURCHASEORDERBOOKING());
            holder.date.setText(monthlyPOB.get(position).getTRANSACTIONDATE());
            holder.tvpobaddress.setText(monthlyPOB.get(position).getADDRESS());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, OrderDetails.class);
                    i.putExtra("OrderId", monthlyPOB.get(position).getORDERID());
                    i.putExtra("Status", monthlyPOB.get(position).getTRANSACTIONSTATUS());
                    activity.startActivity(i);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return monthlyPOB.size();
    }

    public class POBViewHolder extends RecyclerView.ViewHolder {
        private TextView chemistName, date, orderId, stockiestName, amount, status,tvpobaddress;

        public POBViewHolder(View view) {
            super(view);
            chemistName = (TextView) view.findViewById(R.id.stockistName);
            tvpobaddress = (TextView) view.findViewById(R.id.tvpobaddress);
            date = (TextView) view.findViewById(R.id.date);
            stockiestName = (TextView) view.findViewById(R.id.stockiestName);
            orderId = (TextView) view.findViewById(R.id.orderId);
            amount = (TextView) view.findViewById(R.id.amount);
            status = (TextView) view.findViewById(R.id.status);
        }
    }

    @Override
    public Filter getFilter() {
        if (mFriendFilter == null) {
            mFriendFilter = new POBAdapter.FriendFilter(this, monthlyPOB);
        }

        return mFriendFilter;
    }

    private class FriendFilter extends Filter {

        public List<POBResponse.MONTHLY> mFilteredList;
        private POBAdapter mRecyclerAdapter;
        private List<POBResponse.MONTHLY> mArrayList;
        private List<POBResponse.MONTHLY> originalList;


        private FriendFilter(POBAdapter recyclerAdapter, List<POBResponse.MONTHLY> arrayList) {
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
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final POBResponse.MONTHLY monthlyPOB : mArrayList) {
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
            mRecyclerAdapter.monthlyPOB.clear();
            mRecyclerAdapter.monthlyPOB.addAll((Collection<? extends POBResponse.MONTHLY>) results.values);
            mRecyclerAdapter.notifyDataSetChanged();
        }


    }

    private void calculateTotalAmount(List<POBResponse.MONTHLY> info){
        Float totalAmount = 0.0f;

            for (int i=0;i<info.size();i++){
                if(info.get(i).getORDERID().equals("")){
                    Toast.makeText(activity, "No orders available!!", Toast.LENGTH_SHORT).show();

                }
                else {
                    totalAmount = totalAmount + Float.valueOf(info.get(i).getPURCHASEORDERBOOKING());
                    POBActivity.totalAmount.setText(totalAmount.toString());
                }

            }




    }
}
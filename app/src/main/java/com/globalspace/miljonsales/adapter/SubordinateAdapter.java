package com.globalspace.miljonsales.adapter;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.activity.SubordinateDashboardActivity;
import com.globalspace.miljonsales.model.SubordinateResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SubordinateAdapter extends RecyclerView.Adapter<SubordinateAdapter.SubordinateVH> implements Filterable {

    private Activity activity;
    private ArrayList<SubordinateResponse.SubordinateData> subordinateData;
    private FriendFilter mFriendFilter;

    public SubordinateAdapter(Activity activity, ArrayList<SubordinateResponse.SubordinateData> subordinateData) {
        this.activity = activity;
        this.subordinateData = subordinateData;
    }

    @NonNull
    @Override
    public SubordinateVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new SubordinateVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubordinateVH holder, final int position) {

        holder.version_flag.setVisibility(View.GONE);

        holder.subordinateName.setText(subordinateData.get(position).getSubordinateName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, SubordinateDashboardActivity.class);
                i.putExtra("EmpId", subordinateData.get(position).getSubordinateId());
                i.putExtra("SubordinateName", subordinateData.get(position).getSubordinateName());
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subordinateData.size();
    }

    public class SubordinateVH extends RecyclerView.ViewHolder {
        private TextView subordinateName, version_flag;

        public SubordinateVH(View itemView) {
            super(itemView);
            subordinateName = (TextView) itemView.findViewById(R.id.pName);
            version_flag = (TextView) itemView.findViewById(R.id.tv_version);
        }
    }


    @Override
    public Filter getFilter() {
        if (mFriendFilter == null) {
            mFriendFilter = new FriendFilter(this, subordinateData);
        }
        return mFriendFilter;
    }


    private class FriendFilter extends Filter {

        public List<SubordinateResponse.SubordinateData> mFilteredList;
        private SubordinateAdapter mRecyclerAdapter;
        private List<SubordinateResponse.SubordinateData> mArrayList;


        private FriendFilter(SubordinateAdapter recyclerAdapter, List<SubordinateResponse.SubordinateData> arrayList) {
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

                for (final SubordinateResponse.SubordinateData subordinateData : mArrayList) {
                    if (subordinateData.getSubordinateName().toLowerCase().contains(filterPattern)) {
                        mFilteredList.add(subordinateData);
                    }
                }
            }
            filterResults.values = mFilteredList;

            //filterResults.count = mFilteredList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mRecyclerAdapter.subordinateData.clear();
            mRecyclerAdapter.subordinateData.addAll((Collection<? extends SubordinateResponse.SubordinateData>) results.values);
            mRecyclerAdapter.notifyDataSetChanged();
        }


    }
}

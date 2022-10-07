package com.globalspace.miljonsales.adapter;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.activity.CallDetails;
import com.globalspace.miljonsales.model.DashboardData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CallDetailsAdapter extends RecyclerView.Adapter<CallDetailsAdapter.CallDetailsVH> implements Filterable {
    private Activity activity;
    private ArrayList<DashboardData.ReportingDetails> details;
    private FriendFilter mFriendFilter;

    public CallDetailsAdapter(Activity activity, ArrayList<DashboardData.ReportingDetails> details) {
        this.activity = activity;
        this.details = details;
    }

    @NonNull
    @Override
    public CallDetailsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.call_detail_list_view, parent, false);

        CallDetailsVH vh = new CallDetailsVH(itemLayoutView);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull CallDetailsVH h, int p) {


        h.reportingType.setText(details.get(p).getReportingType());
        h.tv_ReportingAddress.setText(details.get(p).getrEPORTINGADDRESS());
        if (details.get(p).getAddress().equals("")) {

            h.addressLL.setVisibility(View.GONE);
        } else {
            h.addressLL.setVisibility(View.VISIBLE);
            h.address.setText(details.get(p).getAddress());
        }

        if (details.get(p).getShopName().equals("")) {

            h.shopNameLL.setVisibility(View.GONE);
        } else {
            h.shopNameLL.setVisibility(View.VISIBLE);
            h.shopName.setText(details.get(p).getShopName());
        }

        if (details.get(p).getMobile().equals("")) {

            h.mobileNoLL.setVisibility(View.GONE);
        } else {
            h.mobileNoLL.setVisibility(View.VISIBLE);
            h.mobileNo.setText(details.get(p).getMobile());
        }

        if (details.get(p).getJointWork().equals("")) {

            h.jointWorkLL.setVisibility(View.GONE);
        } else {
            h.jointWorkLL.setVisibility(View.VISIBLE);
            h.jointWork.setText(details.get(p).getJointWork());
        }
        h.chemistType.setText(details.get(p).getCategory());
        String date[] = ((details.get(p).getReportingDate().split(" "))[0]).split("-");
        String formattedDate = date[2] + "-" + date[1] + "-" + date[0];
        h.reportingDate.setText(formattedDate);

    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class CallDetailsVH extends RecyclerView.ViewHolder {

        private TextView shopName, mobileNo, address, chemistType, reportingDate, reportingType,jointWork,tv_ReportingAddress;
        private LinearLayout addressLL,shopNameLL,mobileNoLL,jointWorkLL;

        public CallDetailsVH(View v) {
            super(v);
            shopName = (TextView) v.findViewById(R.id.shopName);
            mobileNo = (TextView) v.findViewById(R.id.mobileNo);
            address = (TextView) v.findViewById(R.id.address);
            addressLL = (LinearLayout) v.findViewById(R.id.addressLL);
            shopNameLL = (LinearLayout) v.findViewById(R.id.shopNameLL);
            mobileNoLL = (LinearLayout) v.findViewById(R.id.mobileNoLL);
            chemistType = (TextView) v.findViewById(R.id.chemistCategory);
            reportingDate = (TextView) v.findViewById(R.id.reportingDate);
            reportingType = (TextView) v.findViewById(R.id.reportingType);
            jointWork = (TextView) v.findViewById(R.id.jointWork);
            tv_ReportingAddress = (TextView) v.findViewById(R.id.tv_ReportingAddress);
            jointWorkLL = (LinearLayout) v.findViewById(R.id.jointWorkLL);
        }
    }

    @Override
    public Filter getFilter() {
        if (mFriendFilter == null) {
            mFriendFilter = new FriendFilter(CallDetailsAdapter.this, details);
        }
        return mFriendFilter;
    }

    private class FriendFilter extends Filter {

        public List<DashboardData.ReportingDetails> mFilteredList;
        private CallDetailsAdapter mRecyclerAdapter;
        private List<DashboardData.ReportingDetails> mArrayList;
        private List<DashboardData.ReportingDetails> originalList;


        private FriendFilter(CallDetailsAdapter recyclerAdapter, List<DashboardData.ReportingDetails> arrayList) {
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
                if (CallDetails.FilterType.equals("Date")){
                    mArrayList.clear();
                    mArrayList.addAll(originalList);
                    mFilteredList.addAll(originalList);
                }else {
                    mFilteredList.addAll(mArrayList);
                }
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final DashboardData.ReportingDetails monthlyPOB : mArrayList) {
                    if (CallDetails.FilterType.equals("ShopName")) {
                        if (monthlyPOB.getShopName().toLowerCase().contains(filterPattern)) {
                            mFilteredList.add(monthlyPOB);
                        }
                    }else {
                        if (monthlyPOB.getReportingDate().toLowerCase().contains(filterPattern)) {
                            mFilteredList.add(monthlyPOB);
                        }

                    }
                }

                if (CallDetails.FilterType.equals("Date")) {
                    mArrayList.clear();
                    mArrayList.addAll(mFilteredList);
                }
            }


            filterResults.values = mFilteredList;

            //filterResults.count = mFilteredList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mRecyclerAdapter.details.clear();
            mRecyclerAdapter.details.addAll((Collection<? extends DashboardData.ReportingDetails>) results.values);
            mRecyclerAdapter.notifyDataSetChanged();
        }


    }
}

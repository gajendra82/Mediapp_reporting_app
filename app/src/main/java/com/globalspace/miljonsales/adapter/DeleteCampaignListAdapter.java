package com.globalspace.miljonsales.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.TextView;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.fragment.FragmentCampaign;
import com.globalspace.miljonsales.model.ChemistInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DeleteCampaignListAdapter extends RecyclerView.Adapter<DeleteCampaignListAdapter.CampaignVH> implements Filterable {

    private List<ChemistInfo> chemistData;
    private Activity activity;
    private FriendFilter mFriendFilter;


    public DeleteCampaignListAdapter(Activity activity, List<ChemistInfo> chemistData) {
        this.activity = activity;
        this.chemistData = chemistData;

    }

    @NonNull
    @Override
    public CampaignVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delete_chemist, parent, false);
        CampaignVH vh = new CampaignVH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CampaignVH holder, final int position) {
        holder.chemistName.setText(chemistData.get(position).getFSTNAME());
        if (chemistData.get(position).isChecked()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chemistData.get(position).isChecked()) {
                    FragmentCampaign.deleteMembersList.add(chemistData.get(position));
                    chemistData.get(position).setChecked(true);
                    holder.checkBox.setChecked(true);
                } else {
                    if (FragmentCampaign.deleteMembersList.contains(chemistData.get(position))) {
                        FragmentCampaign.deleteMembersList.remove(chemistData.get(position));
                        chemistData.get(position).setChecked(false);
                        holder.checkBox.setChecked(false);
                    }

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return chemistData.size();
    }

    @Override
    public Filter getFilter() {
        if (mFriendFilter == null) {
            mFriendFilter = new FriendFilter(this, chemistData);
        }
        return mFriendFilter;
    }

    public class CampaignVH extends RecyclerView.ViewHolder {

        private TextView chemistName;
        private RadioButton checkBox;

        public CampaignVH(View itemView) {
            super(itemView);

            chemistName = (TextView) itemView.findViewById(R.id.pName);
            checkBox = (RadioButton) itemView.findViewById(R.id.checkboxChemist);
        }
    }

    private class FriendFilter extends Filter {

        public List<ChemistInfo> mFilteredList;
        private DeleteCampaignListAdapter mRecyclerAdapter;
        private List<ChemistInfo> mArrayList;


        private FriendFilter(DeleteCampaignListAdapter recyclerAdapter, List<ChemistInfo> arrayList) {
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

                for (final ChemistInfo chemist_info : mArrayList) {
                    if (chemist_info.getFSTNAME().toLowerCase().contains(filterPattern)) {
                        mFilteredList.add(chemist_info);
                    }
                }
            }
            filterResults.values = mFilteredList;

            //filterResults.count = mFilteredList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mRecyclerAdapter.chemistData.clear();
            mRecyclerAdapter.chemistData.addAll((Collection<? extends ChemistInfo>) results.values);
            mRecyclerAdapter.notifyDataSetChanged();
        }


    }

}

package com.globalspace.miljonsales.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.model.DownloadResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DownloadsAdapter extends RecyclerView.Adapter<DownloadsAdapter.DownloadsViewHolder> implements Filterable {
	private Activity activity;
	private ArrayList<DownloadResponse.DownloadData> data;
	private FriendFilter mFriendFilter;
	
	public DownloadsAdapter(Activity activity, ArrayList<DownloadResponse.DownloadData> data){
		this.activity = activity;
		this.data = data;
	}
	
	@NonNull
	@Override
	public DownloadsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download, parent,false);
		DownloadsViewHolder vh = new DownloadsViewHolder(v);
		return vh;
	}
	
	@Override
	public void onBindViewHolder(@NonNull DownloadsViewHolder holder, int position) {
		holder.chemistName.setText(data.get(position).getcHEMISTNAME());
		holder.registrationDate.setText(data.get(position).getREG_DATE());
	}
	
	@Override
	public int getItemCount() {
		return data.size();
	}

	@Override
	public Filter getFilter() {
		if (mFriendFilter == null) {
			mFriendFilter = new FriendFilter(this, data);
		}
		return mFriendFilter;
	}

	public class DownloadsViewHolder extends RecyclerView.ViewHolder{
		
		private TextView chemistName, registrationDate;
		
		public DownloadsViewHolder(View v) {
			super(v);
			
			chemistName = (TextView) v.findViewById(R.id.stockistName);
			registrationDate = (TextView) v.findViewById(R.id.date_dwnld);
		}
	}


	private class FriendFilter extends Filter {

		public List<DownloadResponse.DownloadData> mFilteredList;
		private DownloadsAdapter mRecyclerAdapter;
		private List<DownloadResponse.DownloadData> mArrayList;


		private FriendFilter(DownloadsAdapter recyclerAdapter, List<DownloadResponse.DownloadData> arrayList) {
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

				for (final DownloadResponse.DownloadData downloads : mArrayList) {
					if (downloads.gettRANSACTIONDATE().toLowerCase().contains(filterPattern)) {
						mFilteredList.add(downloads);
					}
				}
			}
			filterResults.values = mFilteredList;

			//filterResults.count = mFilteredList.size();
			return filterResults;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			mRecyclerAdapter.data.clear();
			if(results.count>0){
				mRecyclerAdapter.data.addAll((Collection<? extends DownloadResponse.DownloadData>) results.values);

			}
			mRecyclerAdapter.notifyDataSetChanged();
		}


	}
}

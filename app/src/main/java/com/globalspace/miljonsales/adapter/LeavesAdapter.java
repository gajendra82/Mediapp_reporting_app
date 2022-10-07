package com.globalspace.miljonsales.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.model.LeavesResponse;

import java.util.ArrayList;

public class LeavesAdapter extends RecyclerView.Adapter<LeavesAdapter.LeaveVH> {
	private Activity activity;
	private ArrayList<LeavesResponse.LeavesData> data;
	
	public LeavesAdapter(Activity activity, ArrayList<LeavesResponse.LeavesData> data) {
		this.activity = activity;
		this.data = data;
	}
	
	@NonNull
	@Override
	public LeaveVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaves, parent, false);
		LeaveVH vh = new LeaveVH(v);
		return vh;
	}
	
	@Override
	public void onBindViewHolder(@NonNull LeaveVH holder, int position) {
			holder.fromDate.setText(data.get(position).getLeaveFromDate());
			holder.toDate.setText(data.get(position).getLeaveTODate());
			holder.reason.setText(data.get(position).getLeaveReason());
	}
	
	@Override
	public int getItemCount() {
		return data.size();
	}
	
	public class LeaveVH extends RecyclerView.ViewHolder {
		
		private TextView toDate, fromDate, reason;
		
		public LeaveVH(View v) {
			super(v);
			
			toDate = (TextView) v.findViewById(R.id.toDate);
			fromDate = (TextView) v.findViewById(R.id.fromDate);
			reason = (TextView) v.findViewById(R.id.reason);
		}
	}
}

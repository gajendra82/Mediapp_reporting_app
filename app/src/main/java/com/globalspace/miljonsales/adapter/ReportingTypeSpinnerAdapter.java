package com.globalspace.miljonsales.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.model.JointWork;
import com.globalspace.miljonsales.model.OtherReporting;
import com.globalspace.miljonsales.model.OtherResportingResponse;

import java.util.List;

public class ReportingTypeSpinnerAdapter extends BaseAdapter {
	private Activity activity;
	private int resourceId;
	private List<OtherReporting> reportingType;
	private LayoutInflater layoutInflater;


	public ReportingTypeSpinnerAdapter(Activity mcontext, List<OtherReporting> reportingType) {
		this.activity = mcontext;
		this.reportingType = reportingType;
		layoutInflater = activity.getLayoutInflater();
	}
	
	
	@Override
	public int getCount() {
		return reportingType.size();
	}
	
	@Override
	public Object getItem(int position) {
		return null;
	}
	
	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = layoutInflater.inflate(R.layout.reporting_type_spinner,null);
		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(reportingType.get(position).getActivityType());
		return convertView;
	}
}

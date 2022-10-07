package com.globalspace.miljonsales.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.model.JointWork;

import java.util.List;

public class JointWorkSpinnerAdapter extends BaseAdapter {
	private Activity activity;
	private int resourceId;
	private List<JointWork> jointWorks;
	private LayoutInflater layoutInflater;
	
	
	public JointWorkSpinnerAdapter(Activity mcontext, List<JointWork> jointWorks) {
		this.activity = mcontext;
		this.jointWorks = jointWorks;
		layoutInflater = activity.getLayoutInflater();
	}
	
	
	@Override
	public int getCount() {
		return jointWorks.size();
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
		convertView = layoutInflater.inflate(R.layout.joint_work_spinner,null);
		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(jointWorks.get(position).getJointWorkName());
		return convertView;
	}
}

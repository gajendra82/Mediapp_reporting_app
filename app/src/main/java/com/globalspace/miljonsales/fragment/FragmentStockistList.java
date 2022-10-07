package com.globalspace.miljonsales.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.MiljonOffer;
import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.activity.RegisterActivity;
import com.globalspace.miljonsales.adapter.Stockist_List_Adapter;
import com.globalspace.miljonsales.model.JointWork;
import com.globalspace.miljonsales.model.STOCKIESTINFO;

import java.util.ArrayList;
import java.util.List;

public class FragmentStockistList extends Fragment {
	
	public static List<STOCKIESTINFO> stockistData;
	public static List<JointWork> jointWorks = new ArrayList<>();
	public static boolean isSearchOn = false;
	private View v;
	private RecyclerView stockistRcv;
	private SharedPreferences sPref;
	private FloatingActionButton floatingActionButton;
	private TextView searchBtn, closeBtn, clearBtn;
	public static TextView stockistCount;
	private Toolbar searchToolbar, mainToolbar;
	private EditText searchview;
	private Stockist_List_Adapter stockist_list_adapter;
	private ProgressDialog progress;
	private TextWatcher filterTextWatcher = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count,
		                              int after) {
			
		}
		
		public void onTextChanged(CharSequence s, int start, int before,
		                          int count) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
			try {
               /* ((Filterable) (chemist_list_adapter)).getFilter().filter(
                        et_search.getText().toString());*/
				stockist_list_adapter.getFilter().filter(s);
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
	};
	
	public FragmentStockistList() {
		// Required empty public constructor
	}
	
	public static void hideSoftKeyboard(Activity activity) {
	/*	InputMethodManager inputMethodManager =
				(InputMethodManager) activity.getSystemService(
						Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(
				activity.getCurrentFocus().getWindowToken(), 0);*/

		InputMethodManager inputMethodManager=(InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
		View focusedView = activity.getWindow().getDecorView().getRootView();

		if (focusedView != null) {
			inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)  {
		// Inflate the layout for this fragment
		v = inflater.inflate(R.layout.fragment_stockist_list, container, false);
		sPref = getActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
		stockistRcv = (RecyclerView) v.findViewById(R.id.stockistRcv);
		stockistRcv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
		stockistCount = (TextView) v.findViewById(R.id.stockistCount);
		
		progress = new ProgressDialog(getActivity());
		progress.setMax(100);
		// progress.setTitle("Progress Dialog");
		progress.setMessage("Your Data is loading Please Wait.......");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setCancelable(false);
		progress.setCanceledOnTouchOutside(false);
		
		
		if (FragmentReporting.DataHolder_stockist.hasData()) {
			stockistData = FragmentReporting.DataHolder_stockist.getData();
			stockistCount.setText("" + stockistData.size());

			jointWorks = FragmentReporting.DataHolder_jointWork.getData();
			stockist_list_adapter = new Stockist_List_Adapter(getActivity(), stockistData,
					sPref.getString(getString(R.string.employee_id), ""),jointWorks,progress);
			stockistRcv.setAdapter(stockist_list_adapter);
		}
		return v;
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser && isResumed()) {
			onResume();
		}
	}
	
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void onResume() {
		super.onResume();
		if (getUserVisibleHint()) {
			FragmentChemistList.isSearchOn = false;
			hideSoftKeyboard(getActivity());
			floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
			
			floatingActionButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					if (!sPref.getString(getActivity().getString(R.string.leave_flag), "").equals("2")) {
						if (getUserVisibleHint()) {
							Intent i = new Intent(getActivity(), RegisterActivity.class);
							i.putExtra("Identifierflag", "1");
							i.putExtra("RegisterActivity", "Stockiest");
							getActivity().startActivity(i);
						}
					}else{
						Toast.makeText(getActivity(), "Sorry you are on leave", Toast.LENGTH_SHORT).show();
					}
					
				}
			});
			
			searchBtn = (TextView) getActivity().findViewById(R.id.searchBtn);
			closeBtn = (TextView) getActivity().findViewById(R.id.search_back);
			closeBtn.setTypeface(MiljonOffer.solidFont);
			mainToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
			searchToolbar = (Toolbar) getActivity().findViewById(R.id.searchtoolbar);
			searchview = (EditText) getActivity().findViewById(R.id.search_view);
			
			
			searchBtn.setOnClickListener(new View.OnClickListener() {
				@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
				@Override
				public void onClick(View v) {
					if (getUserVisibleHint()) {
						mainToolbar.setVisibility(View.GONE);
						searchToolbar.setVisibility(View.VISIBLE);
						circleReveal(R.id.searchtoolbar, 1, true, true);
						searchview.setHint("Search Stockist");
						searchview.addTextChangedListener(filterTextWatcher);
						isSearchOn = true;
					}
				}
			});
			
			closeBtn.setOnClickListener(new View.OnClickListener() {
				@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
				@Override
				public void onClick(View v) {
					searchview.setText("");
					circleReveal(R.id.searchtoolbar, 1, true, false);
					searchToolbar.setVisibility(View.GONE);
					mainToolbar.setVisibility(View.VISIBLE);
					isSearchOn = false;
					hideSoftKeyboard(getActivity());
					
				}
			});
			
			clearBtn = (TextView) getActivity().findViewById(R.id.search_clear);
			clearBtn.setTypeface(MiljonOffer.solidFont);
			
			
			clearBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					searchview.setText("");
					
				}
			});
			
			
			if (!isSearchOn) {
				searchview.setText("");
				//circleReveal(R.id.searchtoolbar, 1, true, false);
				searchToolbar.setVisibility(View.GONE);
				mainToolbar.setVisibility(View.VISIBLE);
				hideSoftKeyboard(getActivity());
				
			}
			
		}
	}
	
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
			
			final View myView = getActivity().findViewById(viewID);
			
			int width = myView.getWidth();
			
			
			if (posFromRight > 0)
				
				width -= getActivity().getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)
						- getActivity().getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2;
			
			
			if (containsOverflow)
				
				width -= getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);
			
			
			int cx = width;
			
			int cy = myView.getHeight() / 2;
			
			
			Animator anim;
			
			if (isShow)
				
				anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, (float) width);
			
			else
				
				anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float) width, 0);
			
			
			anim.setDuration((long) 220);
			
			
			// make the view invisible when the animation is done
			
			anim.addListener(new AnimatorListenerAdapter() {
				
				@Override
				
				public void onAnimationEnd(Animator animation) {
					
					if (!isShow)
					
					{
						
						super.onAnimationEnd(animation);
						
						
						myView.setVisibility(View.GONE);
						
					}
					
				}
				
			});
			
			
			// make the view visible and start the animation
			
			if (isShow)
				
				myView.setVisibility(View.VISIBLE);
			
			
			// start the animation
			
			anim.start();
		}
	}
	
}

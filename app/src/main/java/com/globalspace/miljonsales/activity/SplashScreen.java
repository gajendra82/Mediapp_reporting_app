package com.globalspace.miljonsales.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

import com.globalspace.miljonsales.R;

public class SplashScreen extends Activity {
	
	private SharedPreferences sPref;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		sPref = getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (sPref.getBoolean(getString(R.string.isLogin),false)){
					Intent i = new Intent(SplashScreen.this, Dashboard.class);
					startActivity(i);
					SplashScreen.this.finish();
				}else{
					Intent i = new Intent(SplashScreen.this, Login.class);
					startActivity(i);
					SplashScreen.this.finish();
				}
			}
		},3000);
	}
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		SplashScreen.this.finish();
	}
}

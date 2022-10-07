package com.globalspace.miljonsales;

import android.app.Application;
import android.graphics.Typeface;

public class MiljonOffer extends Application {
	
	public static Typeface solidFont;
	
	@Override
	public void onCreate() {
		super.onCreate();
		solidFont = Typeface.createFromAsset(getAssets(),"fa-solid-900.ttf");
	}
}

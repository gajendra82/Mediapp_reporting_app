package com.globalspace.miljonsales.retrofit;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;


/**
 * Created by aravind on 29/3/18.
 */

public class Syncservice_Retrofit extends Service {

    Context ctx;
    Syncservice_Retrofit syncservice_retrofit;
    ApiInterface apiInterface;

    boolean flag = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public Syncservice_Retrofit() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}


package com.globalspace.miljonsales.retrofit;

import android.util.Log;

import com.globalspace.miljonsales.model.Login_Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.globalspace.miljonsales.utils.Constants.login_url;
import static com.globalspace.miljonsales.utils.Constants.stockisturl;


/**
 * Created by aravind on 8/7/18.
 */

public class ApiUtils {
    private static final String TAG = "ApiUtils";


    public interface ILoginCallback {
        void onSuccessLogin(Login_Response response);

        void onFailure(String failureMessage);
    }


    private static ApiUtils objWebAPI;

    public static ApiUtils getInstance() {
        if (objWebAPI == null) {
            objWebAPI = new ApiUtils();
        }
        return objWebAPI;
    }

    
    //region Comment
/*
    public void getWalletdata(String s1, String s2, String s3, String s4, String s5,
                              final ILoginCallback callback) {

        ApiInterface apiService = RetrofitClient.getClient(login_url).create(ApiInterface.class);

        Call<Login_Response> call = apiService.getWalletdetail(s1, s2, s3, s4, s5);
        call.enqueue(new Callback<Login_Response>() {
            @Override
            public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {

                if (response.isSuccessful()) {
                    callback.onSuccessLogin(response.body()); // passing the whole response model
                } else {
                    //response unSuccessful
                    callback.onFailure("Failure");
                }
            }

            @Override
            public void onFailure(Call<Login_Response> call, Throwable t) {
                Log.e(TAG, t.toString());
                callback.onFailure(t.getMessage());
            }
        });

    }*/


//endregion

    public static ApiInterface getWalletData(){
        return RetrofitClient.getClient(login_url).create(ApiInterface.class);
    }
    
    public static ApiInterface getDashboardData(){
        return RetrofitClient.getClient(login_url).create(ApiInterface.class);
    }

    public void getlocation(String latitude, String longitude, String functionname, String emp_id,
                            String chem_id, String stock_id,String Address,String jointWorkId,String rep_type, final ILoginCallback ilogincall) {
        ApiInterface apiService = RetrofitClient.getClient(login_url).create(ApiInterface.class);

        Call<Login_Response> call = apiService.getlocation(latitude, longitude,
                functionname, emp_id, chem_id, stock_id,Address,jointWorkId,rep_type);
        call.enqueue(new Callback<Login_Response>() {
            @Override
            public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                if (response.isSuccessful()) {
                    ilogincall.onSuccessLogin(response.body()); // passing the whole response model
                } else {
                    //response unSuccessful
                    ilogincall.onFailure("Failure");
                }
            }

            @Override
            public void onFailure(Call<Login_Response> call, Throwable t) {
                Log.e(TAG, t.toString());
                ilogincall.onFailure(t.getMessage());
            }
        });
    }


    public void setregistration(String str_druglicenseno, String str_name,
                                String str_shopName, String str_emailId, String str_mObile,
                                String str_street, String str_city,
                                String str_pin, String str_state, String gstno, String functionname,
                                String flag, String createpassword, String otpflag, String session_id,
                                String regId, String imei, String strlatitude, String strlongitude,
                                String altMobNumber, String zip_id,String mrId, final ILoginCallback ilogincall) {

        ApiInterface apiService = RetrofitClient.getClient(login_url).create(ApiInterface.class);

        Call<Login_Response> call = apiService.setregister(str_druglicenseno, str_name, str_shopName, str_emailId,
                str_mObile, str_street, str_city, str_pin, str_state, gstno, functionname, flag,
                createpassword, otpflag, session_id,
                regId, imei, strlatitude, strlongitude,
                altMobNumber, zip_id,mrId);
        call.enqueue(new Callback<Login_Response>() {
            @Override
            public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                if (response.isSuccessful()) {
                    ilogincall.onSuccessLogin(response.body()); // passing the whole response model
                } else {
                    //response unSuccessful
                    ilogincall.onFailure("Failure");
                }
            }

            @Override
            public void onFailure(Call<Login_Response> call, Throwable t) {
                Log.e(TAG, t.toString());
                ilogincall.onFailure(t.getMessage());
            }
        });
    }


    public static ApiInterface submitdatainterface() {
        return RetrofitClient.getClient(login_url).create(ApiInterface.class);
    }


    public static ApiInterface getstockistorderinterface() {
        return RetrofitClient.getClient(stockisturl).create(ApiInterface.class);
    }
    
    

}

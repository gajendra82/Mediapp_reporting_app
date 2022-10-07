package com.globalspace.miljonsales.retrofit;


import com.globalspace.miljonsales.model.Addpincode;
import com.globalspace.miljonsales.model.ApprovedResponse;
import com.globalspace.miljonsales.model.CartStockistListResponse;
import com.globalspace.miljonsales.model.DailyPobResponse;
import com.globalspace.miljonsales.model.DashboardData;
import com.globalspace.miljonsales.model.DeclineResponse;
import com.globalspace.miljonsales.model.Deletepincode;
import com.globalspace.miljonsales.model.DownloadResponse;
import com.globalspace.miljonsales.model.LeavesResponse;
import com.globalspace.miljonsales.model.Login_Response;
import com.globalspace.miljonsales.model.OrderDetailsResponse;
import com.globalspace.miljonsales.model.OrderInfo;
import com.globalspace.miljonsales.model.OrderResponse;
import com.globalspace.miljonsales.model.OtherResportingResponse;
import com.globalspace.miljonsales.model.POBResponse;
import com.globalspace.miljonsales.model.ProductResponse;
import com.globalspace.miljonsales.model.Response;
import com.globalspace.miljonsales.model.Stockist_response;
import com.globalspace.miljonsales.model.SubordinateResponse;
import com.globalspace.miljonsales.model.UpdateLatLong;
import com.globalspace.miljonsales.model.getcoveredpincode;
import com.globalspace.miljonsales.model.productwisediscountprdlist;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by aravind on 8/7/18.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("reporting.php")
    Call<Login_Response> getWalletdetail(
            @Field("username") String email,
            @Field("password") String password,
            @Field("token") String token,
            @Field("imei") String imei,
            @Field("function_name") String function_name,
            @Field("date") String date
    );


    @FormUrlEncoded
    @POST("reporting.php")
    Call<OrderResponse> setOrderData(
            @Field("function_name") String function_name,
            @Field("SESSION_ID") String SESSION_ID,
            @Field("emp_id") String emp_id,
            @Field("product") String product,
            @Field("org_id") String org_id
    );
    @FormUrlEncoded
    @POST("reporting.php")
    Call<CartStockistListResponse> setStockistListOrder(
            @Field("function_name") String function_name,
            @Field("SESSION_ID") String SESSION_ID,
            @Field("emp_id") String emp_id,
            @Field("ORDER_ROW") String product

    );

    @FormUrlEncoded
    @POST("reporting.php")
    Call<OtherResportingResponse> otherThanReporting(
            @Field("function_name") String function_name
    );



    @FormUrlEncoded
    @POST("reporting.php")
    Call<productwisediscountprdlist> getProductwisedisData(
            @Field("function_name") String endpoint,
            @Field("search") String search,
            @Field("manf_id") String companyId,
            @Field("category_id") String categoryId,
            @Field("limit") String limit

    );


    @FormUrlEncoded
    @POST("reporting.php")
    Call<OtherResportingResponse> setOtherThanReporting(
            @Field("function_name") String function_name,
            @Field("Emp_id") String emp_id,
            @Field("Activity_id") String Activity_id,
            @Field("Lat") String Lat,
            @Field("Long") String Long,
            @Field("Address") String Address,
            @Field("Remark") String Remark
    );
    @FormUrlEncoded
    @POST("reporting.php")
    Call<UpdateLatLong> updateLatLong(
            @Field("function_name") String function_name,
            @Field("LAT") String Lat,
            @Field("LONG") String Long,
            @Field("SESSION_ID") String session_id
    );

    @FormUrlEncoded
    @POST("reporting.php")
    Call<Login_Response> getlocation(
            @Field("lat") String lat,
            @Field("long") String longitude,
            @Field("function_name") String function_name,
            @Field("session_id") String employee_id,
            @Field("chemist_id") String chemist_id,
            @Field("stockiest_id") String stockist_id,
            @Field("address") String address,
            @Field("joint_work_id") String joint_work_id,
            @Field("rep_type") String rep_type
    );

    @FormUrlEncoded
    @POST("reporting.php")
    Call<Login_Response> setregister(
            @Field("drug_licenseno") String str_druglicenseno,
            @Field("name") String str_name,
            @Field("shop_name") String str_shopName,
            @Field("email_id") String str_emailId,
            @Field("mobile_no") String str_mObile,
            @Field("street") String str_street,
            @Field("city") String str_city,
            @Field("pin") String str_pin,
            @Field("state") String str_state,
            @Field("gst_number") String str_gstno,
            @Field("function_name") String functionname,
            @Field("flag") String flag,
            @Field("password") String createpassword,
            @Field("otpflag") String otpflag,
            @Field("session_id") String session_id,
            @Field("reg_id") String regId,
            @Field("imei") String imei,
            @Field("lat") String strlatitude,
            @Field("long") String strlongitude,
            @Field("pin_covered") String altMobNumber,
            @Field("zip_id") String zip_id,
            @Field("mr_id") String mrid
    );


    @FormUrlEncoded
    @POST("reporting.php")
    Call<Login_Response> setnotinterestedchemist(
            @Field("name") String name,
            @Field("email") String email,
            @Field("mobile") String mobile_no,
            @Field("function_name") String functionname,
            @Field("session_id") String employee_id,
            @Field("lat") String strlatitude,
            @Field("long") String strlongitude,
            @Field("joint_work_id") String joint_work_id
    );

    @FormUrlEncoded
    @POST("reporting.php")
    Call<Login_Response> getnotinteresteddata(
            @Field("function_name") String functionname,
            @Field("session_id") String employee_id
    );

    @FormUrlEncoded
    @POST("reporting.php")
    Call<Response> setProductAvailStockiest(
            @Field("function_name") String function_name,
            @Field("obj") JSONObject obj);


    @FormUrlEncoded
    @POST("reporting.php")
    Call<DashboardData> getDashboardData(
            @Field("function_name") String function_name,
            @Field("username") String username,
            @Field("search_date") String date
    );

    @FormUrlEncoded
    @POST("reporting.php")
    Call<Response> setLeaveData(
            @Field("function_name") String function_name,
            @Field("username") String employee_id,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("remarks") String remarks
    );

    @FormUrlEncoded
    @POST("reporting.php")
    Call<POBResponse> getEmpMonthPOBDetail(
            @Field("function_name") String function_name,
            @Field("emp_id") String emp_id,
            @Field("date") String date
    );
    @FormUrlEncoded
    @POST("reporting.php")
    Call<DailyPobResponse> getEmpDailyPOBDetail(
            @Field("function_name") String function_name,
            @Field("emp_id") String emp_id,
            @Field("date") String date
    );

    @FormUrlEncoded
    @POST("reporting.php")
    Call<LeavesResponse> getEmpLeaveDetail(
            @Field("function_name") String function_name,
            @Field("emp_id") String emp_id,
            @Field("date") String date
    );

    @FormUrlEncoded
    @POST("reporting.php")
    Call<DownloadResponse> getDownloadDetail(
            @Field("function_name") String function_name,
            @Field("emp_id") String emp_id,
            @Field("date") String date
    );


    @FormUrlEncoded
    @POST("reporting.php")
    Call<Login_Response> setChampEmpCheDetail(
            @Field("function_name") String function_name,
            @Field("chemist_list") JSONObject chemistList
    );

    @FormUrlEncoded
    @POST("reporting.php")
    Call<Login_Response> changePassword(
            @Field("function_name") String function_name,
            @Field("SESSION_ID") String userId,
            @Field("OLD_PASSWORD") String oldPass,
            @Field("NEW_PASSWORD") String newPass
    );

    @FormUrlEncoded
    @POST("reporting.php")
    Call<Login_Response> getChampEmpCheDetail(
            @Field("function_name") String function_name,
            @Field("Emp_id") String emp_id
    );

    @FormUrlEncoded
    @POST("reporting.php")
    Call<OrderDetailsResponse> getOrderDetails(
            @Field("function_name") String function_name,
            @Field("Order_id") String order_id,
            @Field("Status") String status
    );

    @FormUrlEncoded
    @POST("reporting.php")
    Call<SubordinateResponse> getMiljonEmpSubOrInfo(
            @Field("function_name") String function_name,
            @Field("Emp_id") String empId
    );

    @FormUrlEncoded
    @POST("reporting.php")
    Call<ProductResponse> getProductList(
            @Field("function_name") String function_name,
            @Field("Org_id") String org_id
    );

    @FormUrlEncoded
    @POST("reporting.php")
    Call<getcoveredpincode> getaddedcoveredpincode(
            @Field("function_name") String endpoint,
            @Field("SESSION_ID") String session_id


    );


    @FormUrlEncoded
    @POST("reporting.php")
    Call<Addpincode> Addcoveredpincode(
            @Field("function_name") String endpoint,
            @Field("SESSION_ID") String session_id,
            @Field("NEW_PINCODE") String new_pincode


    );


    @FormUrlEncoded
    @POST("reporting.php")
    Call<Deletepincode> deleteaddedcoveredpincode(
            @Field("function_name") String endpoint,
            @Field("SESSION_ID") String session_id,
            @Field("OLD_PINCODE") String newpincode


    );
}

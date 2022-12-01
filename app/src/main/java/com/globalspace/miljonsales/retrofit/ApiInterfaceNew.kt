package com.globalspace.miljonsales.retrofit

import com.globalspace.miljonsales.ui.add_details.*
import com.globalspace.miljonsales.ui.add_details_dashboard.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterfaceNew {

    @FormUrlEncoded
    @POST("reporting.php")
    suspend fun GetHospitalSummaryData(
        @Field("function_name") function_name: String,
        @Field("EmployeeID") employeeID: String
    ): Response<SummaryModel>

    @FormUrlEncoded
    @POST("reporting.php")
    suspend fun GetHospitalDetailsData(
        @Field("function_name") function_name: String,
        @Field("EmployeeID") employeeID: String
    ): Response<HospitalDetailsModel>

    @FormUrlEncoded
    @POST("reporting.php")
    suspend fun add_details_citymaster(@Field("function_name") function_name: String): Response<AddDetailsModel>

    @FormUrlEncoded
    @POST("reporting.php")
    suspend fun add_details_citymaster(
        @Field("function_name") function_name: String,
        @Field("state_name") employeeID: String
    ): Response<CityModel>

    @FormUrlEncoded
    @POST("reporting.php")
    suspend fun add_details_hospital(@Field("function_name") function_name: String): Response<AddDetailsHospitalModel>

    @FormUrlEncoded
    @POST("reporting.php")
    suspend fun add_details_facility(@Field("function_name") function_name: String): Response<AddDetailsFacilityModel>

    @FormUrlEncoded
    @POST("reporting.php")
    suspend fun add_details_speciality(@Field("function_name") function_name: String): Response<AddDetailsSpecialityModel>

    @FormUrlEncoded
    @POST("reporting.php")
    suspend fun add_details_consumtion(
        @Field("function_name") function_name: String,
        @Field("molecule") molecule: String,
        @Field("strength") strength: String,
        @Field("brand") brand: String,
        @Field("product_ids") product_ids: String,
    ): Response<AddDetailsConsumptionModel>

    @Headers("Content-Type: application/json")
    @POST("reporting.php")
    suspend fun submit_HospitalDetails(
        @Body data: setHospitalDetails
    ): Response<getHospitalDetails>

    @Multipart
    @POST("reporting_v1.php")
    suspend fun submit_HospitalImages(
        @Part image: MultipartBody.Part,
        @Part("function_name") function_name: RequestBody,
        @Part("EmployeeID") employeeID: RequestBody,
        @Part("ImageText") imgtxt: RequestBody,
        @Part("Flag") flag: RequestBody
    ): Response<getHospitalImagesDetails>

}
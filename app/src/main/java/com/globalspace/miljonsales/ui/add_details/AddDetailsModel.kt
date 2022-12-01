package com.globalspace.miljonsales.ui.add_details

import androidx.room.PrimaryKey

data class AddDetailsModel(
    val status: String, val error: String,val errorMessage: String,val response : ArrayList<FetchGeography>
)
data class CityModel(
    val status: String, val error: String,val errorMessage: String,val response : ArrayList<FetchGeography>
)
data class AddDetailsHospitalModel(
    val status: String, val error: String,val errorMessage: String,val response : ArrayList<FetchHospital>
)
data class FetchGeography(
val COUNTRY_ID: Int,
val COUNTRY_NAME: String?,
val STATE_CODE: Int?,
val STATE_NAME: String?,
val CITY_ID: Int?,
val CITY_NAME: String?,
val ZIP_CODE: Int?
)

data class FetchHospital(
    val ID: Int,
    val HospitalType: String?
)


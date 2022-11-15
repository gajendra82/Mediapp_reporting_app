package com.globalspace.miljonsales.ui.add_details_dashboard

import androidx.room.PrimaryKey
import com.globalspace.miljonsales.local_db.entity.*
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddDetailsModel(
   val status: String, val error: String,val errorMessage: String,val response : ArrayList<FetchGeography>
)

data class AddDetailsHospitalModel(
   val status: String, val error: String,val errorMessage: String,val response : ArrayList<FetchHospital>
)

data class AddDetailsFacilityModel(
   val status: String, val error: String,val errorMessage: String,val response : ArrayList<FetchHospitalFacility>
)

data class AddDetailsSpecialityModel(
   val status: String, val error: String,val errorMessage: String,val response : ArrayList<FetchSpecialityTypes>
)

data class AddDetailsConsumptionModel(
   val status: String, val error: String,val errorMessage: String,val response : ArrayList<FetchConsumptionTypes>
)

data class SummaryModel(
   val status: String, val error: String,val errorMessage: String,val response : ArrayList<FetchHospitalSummmary>
)

data class HospitalDetailsModel(
   val status: String, val error: String,val errorMessage: String,val response : ArrayList<InitSyncList>
)

data class InitSyncList(
   val hospitaldetails: ArrayList<FetchHospitalDetails>,
   val hospitalfacility: ArrayList<FetchHospitalFacilityDetails>,
   val hospitalspeciality: ArrayList<FetchHospitalSpecialityDetails>,
   val hospitalstrength: ArrayList<FetchHospitalStrengthDetails>,
   val hospitalmolecule: ArrayList<FetchHospitalMoleculeDetails>,
   val hospitalconsumption: ArrayList<FetchHospitalConsumptionDetails>,
   val hospitalstockist: ArrayList<FetchHospitalStockistDetails>
)

data class FetchFacility(
   val ID: Int,
   val Facility: String?,
   var isCheckFlag : Boolean? = false
)

data class FetchSpeciality(
   val ID: Int,
   val Speciality: String?,
   var isCheckFlag : Boolean?= false
)
data class FetchMolecule(
   val PRODUCT_ID: String,
   val BRAND: String?,
   val STRENGTH: String?,
   val MOLECULE: String?,
   val COMPANY_NAME: String?,
   var isCheckFlag : Boolean?= false
)

data class setHospitalDetails(val hospitaldata: JsonObject)
data class getHospitalDetails(val status: String, val error: String,val errorMessage: String,val response : String)
data class getHospitalImagesDetails(val status: String, val error: String,val errorMessage: String,val message : String)

data class Add_Consumption_items(
   var strength: String,
   var brand: String,
   var availability: String,
   var reason: String,
   var reasonother: String,
   var quantity: String,
   var rate: String,
   var mrp: String,
) : Serializable

data class Add_Stockist_items(
   var stockistname: String,
   var stockistaddress: String,
   var stockiststate: String,
   var stockistcity: String,
   var stockistpersonalname: String,
   var stockistcontactnum: String,
   var stockistpincode: String,
) : Serializable

data class Add_Purchase_Authority_items(
   var paname: String,
   var padesignation: String,
   var pacontactnumber: String,
) : Serializable

data class Add_CompetitorBrand(
   var cbstrength: String,
   var cbbrand: String,
   var cbquantity: String,
   var cbmrp: String,
) : Serializable

data class AddImages(
   var Flag : String,
   var ImagePath : String,
   var ImageText : String,
)

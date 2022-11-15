package com.globalspace.miljonsales.local_db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "CITY_MASTER")
data class FetchGeography(
    @PrimaryKey(autoGenerate = true)
    val GEOID: Int,
    val COUNTRY_ID: Int,
    val COUNTRY_NAME: String?,
    val STATE_CODE: Int?,
    val STATE_NAME: String?,
    val CITY_ID: Int?,
    val CITY_NAME: String?,
    val ZIP_CODE: Int?
)

@Entity(tableName = "HOSPITAL_TYPES")
data class FetchHospital(
    @PrimaryKey
    val ID: Int,
    val HospitalType: String?
)

@Entity(tableName = "FACILITY")
data class FetchHospitalFacility(
    @PrimaryKey
    val ID: Int,
    val Facility: String?
)

@Entity(tableName = "SPECIALITY_TYPES")
data class FetchSpecialityTypes(
    @PrimaryKey
    val ID: Int,
    val Speciality: String?
)

@Entity(tableName = "CONSUMPTION_TYPES")
data class FetchConsumptionTypes(
    @PrimaryKey
    val PRODUCT_ID: String,
    val BRAND: String?,
    val STRENGTH: String?,
    val MOLECULE: String?,
    val COMPANY_NAME: String?,
)

@Entity(tableName = "HOSPITALSUMMARY")
data class FetchHospitalSummmary(
    @PrimaryKey
    val speciality: String,
    val count: String?,
)

@Entity(tableName = "hospitaldetails")
data class FetchHospitalDetails(
    @PrimaryKey
    val HOSPITAL_ID: String,
    val name: String?,
    val address: String?,
    val statecode: String?,
    val state: String?,
    val citycode: String?,
    val city: String?,
    val pincode: String?,
    val hospital: String?,
    val rooms: String?,
    val beds: String?,
    val board_image: String?,
    val physician_list: String?,
    val comment: String?,
    val observation: String?,
): Serializable

@Entity(tableName = "hospitalfacility")
data class FetchHospitalFacilityDetails(
    @PrimaryKey
    val facility_index_id: String,
    val hoapital_id: String?,
    val facility_id: String?,
    val facility_name: String?,
): Serializable

@Entity(tableName = "hospitalspeciality")
data class FetchHospitalSpecialityDetails(
    @PrimaryKey
    val speciality_index_id: String,
    val hoapital_id: String?,
    val speciality_id: String?,
    val speciality_name: String?,
): Serializable

@Entity(tableName = "hospitalstrength")
data class FetchHospitalStrengthDetails(
    @PrimaryKey
    val strength_index_id: String,
    val hoapital_id: String?,
    val product_id: String?,
    val strength: String?,
): Serializable

@Entity(tableName = "hospitalmolecule")
data class FetchHospitalMoleculeDetails(
    @PrimaryKey
    val molecule_index_id: String,
    val hoapital_id: String?,
    val product_id: String?,
    val molecule: String?,
): Serializable

@Entity(tableName = "hospitalconsumption")
data class FetchHospitalConsumptionDetails(
    @PrimaryKey
    val consumption_index_id: String,
    val hoapital_id: String?,
    val consumption_strength: String?,
    val consumption_brand: String?,
    val consumption_availability: String?,
    val consumption_reason: String?,
    val consumption_reasonother: String?,
    val consumption_quantity: String?,
    val consumption_rate: String?,
    val consumption_mrp: String?,
): Serializable

@Entity(tableName = "hospitalstockist")
data class FetchHospitalStockistDetails(
    @PrimaryKey
    val stockist_index_id: String,
    val hoapital_id: String?,
    val stockist_name: String?,
    val stockist_address: String?,
    val stockist_statecode: String?,
    val stockist_state: String?,
    val stockist_citycode: String?,
    val stockist_city: String?,
    val stockist_personalname: String?,
    val stockist_number: String?,
    val stockist_pincode: String?,
): Serializable

package com.globalspace.miljonsales.local_db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globalspace.miljonsales.local_db.entity.*
import com.globalspace.miljonsales.ui.add_details_dashboard.*

@Dao
interface DaoDB {
    //region CONSUMPTION_TYPES
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConsumptionList(geolist: List<FetchConsumptionTypes>)

    @Query("SELECT * From CONSUMPTION_TYPES WHERE COMPANY_NAME = 'GUFIC' GROUP BY MOLECULE")
    fun getAllConsumption(): LiveData<List<FetchMolecule>>

    @Query("SELECT * From CONSUMPTION_TYPES WHERE MOLECULE IN (:molecule) AND COMPANY_NAME = 'GUFIC' GROUP BY STRENGTH")
    fun getAllStrength(molecule : List<String>): LiveData<List<FetchMolecule>>

    @Query("SELECT * From CONSUMPTION_TYPES WHERE STRENGTH= :strength AND COMPANY_NAME = 'GUFIC' ORDER BY BRAND")
    fun getAllBrand(strength : String): LiveData<List<FetchMolecule>>

    @Query("SELECT * From CONSUMPTION_TYPES WHERE STRENGTH= :strength AND COMPANY_NAME NOT IN ('GUFIC') ORDER BY BRAND")
    fun getAllCompetitorBrand(strength : String): LiveData<List<FetchMolecule>>
    //endregion

    // region HOSPITAL_SUMMARY
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospitalSummary(geolist: List<FetchHospitalSummmary>)

    @Query("SELECT * From HOSPITALSUMMARY")
    fun getAllHospSummary(): LiveData<List<FetchHospitalSummmary>>
    //endregion

    // region HOSPITAL_DETAILS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospitalDetails(geolist: List<FetchHospitalDetails>)

    @Query("SELECT * From hospitaldetails")
    fun getAllHospDetails(): LiveData<List<DashboardData>>
    //endregion

    // region HOSPITALFACILITY_DETAILS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospitalFacilityDetails(geolist: List<FetchHospitalFacilityDetails>)
    //endregion

    // region HOSPITALSPECIALITY_DETAILS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospitalSpecialityDetails(geolist: List<FetchHospitalSpecialityDetails>)
    //endregion

    // region HOSPITALSTRENGTH_DETAILS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospitalStrengthDetails(geolist: List<FetchHospitalStrengthDetails>)
    //endregion

    // region HOSPITALMOLECULE_DETAILS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospitalMoleculeDetails(geolist: List<FetchHospitalMoleculeDetails>)
    //endregion

    // region HOSPITALCONSUMPTION_DETAILS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospitalConsumptionDetails(geolist: List<FetchHospitalConsumptionDetails>)
    //endregion

    // region HOSPITALSTOCKIST_DETAILS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospitalStockistDetails(geolist: List<FetchHospitalStockistDetails>)
    //endregion

    //region delete all table
    @Query("DELETE FROM CONSUMPTION_TYPES")
    fun deleteCONSUMPTION_TYPES(): Int

    @Query("DELETE FROM HOSPITALSUMMARY")
    fun deleteHOSPITALSUMMARY(): Int

    @Query("DELETE FROM hospitaldetails")
    fun deletehospitaldetails(): Int

    @Query("DELETE FROM hospitalfacility")
    fun deletehospitalfacility(): Int

    @Query("DELETE FROM hospitalspeciality")
    fun deletehospitalspeciality(): Int

    @Query("DELETE FROM hospitalstrength")
    fun deletehospitalstrength(): Int

    @Query("DELETE FROM hospitalmolecule")
    fun deletehospitalmolecule(): Int

    @Query("DELETE FROM hospitalconsumption")
    fun deletehospitalconsumption(): Int

    @Query("DELETE FROM hospitalstockist")
    fun deletehospitalstockist(): Int

    //endregion

}
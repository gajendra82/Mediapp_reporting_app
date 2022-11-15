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

    //region CITY MASTER
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(geolist: List<FetchGeography>)

    @Query("SELECT * From CITY_MASTER GROUP BY STATE_CODE")
    fun getAllState(): LiveData<List<FetchGeography>>

    @Query("SELECT * From CITY_MASTER Where STATE_CODE = :statecode")
    fun getAllStateCity(statecode: Int): LiveData<List<FetchGeography>>
    //endregion

    //region HOSPITAL_TYPES
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospitalList(geolist: List<FetchHospital>)

    @Query("SELECT * From HOSPITAL_TYPES")
    fun getAllHospital(): LiveData<List<FetchHospital>>
    //endregion

    //region HOSPITAL_FACILITY
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospitalFacility(geolist: List<FetchHospitalFacility>)

    @Query("SELECT * From FACILITY")
    fun getAllFacility(): LiveData<List<FetchFacility>>
    //endregion

    // region SPECIALITY_TYPES
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospSpeciality(geolist: List<FetchSpecialityTypes>)

    @Query("SELECT * From SPECIALITY_TYPES")
    fun getAllSpeciality(): LiveData<List<FetchSpeciality>>
    //endregion

    //region CONSUMPTION_TYPES
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConsumptionList(geolist: List<FetchConsumptionTypes>)

    @Query("SELECT * From CONSUMPTION_TYPES WHERE COMPANY_NAME = 'GUFIC' GROUP BY MOLECULE")
    fun getAllConsumption(): LiveData<List<FetchMolecule>>

    @Query("SELECT * From CONSUMPTION_TYPES WHERE MOLECULE IN (:molecule) AND COMPANY_NAME = 'GUFIC' GROUP BY STRENGTH")
    fun getAllStrength(molecule : List<String>): LiveData<List<FetchMolecule>>

    @Query("SELECT * From CONSUMPTION_TYPES WHERE STRENGTH= :strength AND COMPANY_NAME = 'GUFIC'")
    fun getAllBrand(strength : String): LiveData<List<FetchMolecule>>

    @Query("SELECT * From CONSUMPTION_TYPES WHERE STRENGTH= :strength AND COMPANY_NAME NOT IN ('GUFIC')")
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
    @Query("DELETE FROM CITY_MASTER")
    fun deleteCITY_MASTER(): Int

    @Query("DELETE FROM HOSPITAL_TYPES")
    fun deleteHOSPITAL_TYPES(): Int

    @Query("DELETE FROM FACILITY")
    fun deleteFACILITY(): Int

    @Query("DELETE FROM SPECIALITY_TYPES")
    fun deleteSPECIALITY_TYPES(): Int

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
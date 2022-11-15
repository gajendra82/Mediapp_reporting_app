package com.globalspace.miljonsales.ui.add_details_dashboard

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.globalspace.miljonsales.local_db.database.AppDatabase
import com.globalspace.miljonsales.local_db.entity.FetchHospitalSummmary
import com.globalspace.miljonsales.retrofit.ApiInterfaceNew
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddDetailsDashboardRepository @Inject constructor(
    private val apiInterfaceNew: ApiInterfaceNew,
    private val context: Context,
    private val db: AppDatabase
) {

    fun FetchHospitalSummary() = db.daoDb().getAllHospSummary()
    fun FetchAllHospital() = db.daoDb().getAllHospDetails()


    suspend fun InsertHospitalSummmary(progressBarDashboard: ProgressBar) {
        CoroutineScope(Dispatchers.IO).async {
            try {
                val result = apiInterfaceNew.GetHospitalSummaryData("GetHospitalSummeryData")
                Log.i("tag", "summary data  ${result.body().toString()}")
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        progressBarDashboard.visibility = View.GONE
                        result.body()?.let {
                            if (it.status.equals("200")) {
                                withContext(Dispatchers.IO) {
                                    db.daoDb().deleteHOSPITALSUMMARY()
                                    db.daoDb().insertHospitalSummary(it.response)
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    progressBarDashboard.visibility = View.GONE
                }
                val msg = e.message
                Log.i("tag", e.message.toString())
            }
        }.await()
    }

    suspend fun InsertHospitalDetails(progressBarDashboard: ProgressBar) {
        CoroutineScope(Dispatchers.IO).async {
            try {
                val result = apiInterfaceNew.GetHospitalDetailsData("GetAllHospitalDetails")
                Log.i("tag", "hospital details data  ${result.body().toString()}")
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        progressBarDashboard.visibility = View.GONE
                        result.body()?.let {
                            if (it.status.equals("200")) {
                                withContext(Dispatchers.IO) {
                                    it.response.let {
                                        db.daoDb().deletehospitaldetails()
                                        db.daoDb().deletehospitalfacility()
                                        db.daoDb().deletehospitalspeciality()
                                        db.daoDb().deletehospitalstrength()
                                        db.daoDb().deletehospitalmolecule()
                                        db.daoDb().deletehospitalconsumption()
                                        db.daoDb().deletehospitalstockist()

                                        db.daoDb().insertHospitalDetails(it[0].hospitaldetails)
                                        db.daoDb().insertHospitalFacilityDetails(it[1].hospitalfacility)
                                        db.daoDb().insertHospitalSpecialityDetails(it[2].hospitalspeciality)
                                        db.daoDb().insertHospitalStrengthDetails(it[3].hospitalstrength)
                                        db.daoDb().insertHospitalMoleculeDetails(it[4].hospitalmolecule)
                                        db.daoDb().insertHospitalConsumptionDetails(it[5].hospitalconsumption)
                                        db.daoDb().insertHospitalStockistDetails(it[6].hospitalstockist)

                                    }
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    progressBarDashboard.visibility = View.GONE
                }
                val msg = e.message
                Log.i("tag", e.message.toString())
            }
        }.await()
    }

    suspend fun FetchGeoList(progressBarDashboard: ProgressBar) {
        withContext(Dispatchers.IO) {
            try {
                val result = apiInterfaceNew.add_details_citymaster("GetCityMaster")
                Log.i("tag", "geo list data  ${result.body().toString()}")
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        progressBarDashboard.visibility = View.GONE
                        result.body()?.let {
                            if (it.status.equals("200")) {
                                withContext(Dispatchers.IO) {
                                    db.daoDb().deleteCITY_MASTER()
                                    db.daoDb().insertList(it.response)
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    progressBarDashboard.visibility = View.GONE
                }
                val msg = e.message
                Log.i("tag", e.message.toString())
            }
        }
    }

    suspend fun FetchHospitalList(progressBarDashboard: ProgressBar) {
        withContext(Dispatchers.IO) {
            try {
                val result = apiInterfaceNew.add_details_hospital("GetHospitalTypes")
                Log.i("tag", "type of hospital data  ${result.body().toString()}")
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        progressBarDashboard.visibility = View.GONE
                        result.body()?.let {
                            if (it.status.equals("200")) {
                                withContext(Dispatchers.IO) {
                                    db.daoDb().deleteHOSPITAL_TYPES()
                                    db.daoDb().insertHospitalList(it.response)
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    progressBarDashboard.visibility = View.GONE
                }
                val msg = e.message
                Log.i("tag", e.message.toString())
            }
        }
    }

    suspend fun FetchFacilityList(progressBarDashboard: ProgressBar) {
        withContext(Dispatchers.IO) {
            try {
                val result = apiInterfaceNew.add_details_facility("GetHospitalFacility")
                Log.i("tag", "facility data  ${result.body().toString()}")
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        progressBarDashboard.visibility = View.GONE
                        result.body()?.let {
                            if (it.status.equals("200")) {
                                withContext(Dispatchers.IO) {
                                    db.daoDb().deleteFACILITY()
                                    db.daoDb().insertHospitalFacility(it.response)
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    progressBarDashboard.visibility = View.GONE
                }
                val msg = e.message
                Log.i("tag", e.message.toString())
            }
        }
    }

    suspend fun FetchSpecialityList(progressBarDashboard: ProgressBar) {
        withContext(Dispatchers.IO) {
            try {
                val result = apiInterfaceNew.add_details_speciality("GetHospitalSpeciality")
                Log.i("tag", "Speciality data  ${result.body().toString()}")
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        progressBarDashboard.visibility = View.GONE
                        result.body()?.let {
                            if (it.status.equals("200")) {
                                withContext(Dispatchers.IO) {
                                    db.daoDb().deleteSPECIALITY_TYPES()
                                    db.daoDb().insertHospSpeciality(it.response)
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    progressBarDashboard.visibility = View.GONE
                }
                val msg = e.message
                Log.i("tag", e.message.toString())
            }
        }
    }

    suspend fun FetchConsumptionList(progressBarDashboard: ProgressBar) {
        withContext(Dispatchers.IO) {
            try {
                val result = apiInterfaceNew.add_details_consumtion(
                    "GetHospitalCompositionData",
                    "",
                    "",
                    "",
                    ""
                )
                Log.i("tag", "Consumption data  ${result.body().toString()}")
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        progressBarDashboard.visibility = View.GONE
                        result.body()?.let {
                            if (it.status.equals("200")) {
                                withContext(Dispatchers.IO) {
                                    db.daoDb().deleteCONSUMPTION_TYPES()
                                    db.daoDb().insertConsumptionList(it.response)
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    progressBarDashboard.visibility = View.GONE
                }
                val msg = e.message
                Log.i("tag", e.message.toString())
            }
        }
    }
}
package com.globalspace.miljonsales.ui.add_details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.globalspace.miljonsales.interface_.di.AppPreference
import com.globalspace.miljonsales.interface_.di.GrantPermission
import com.globalspace.miljonsales.local_db.database.AppDatabase
import com.globalspace.miljonsales.retrofit.ApiInterfaceNew
import com.globalspace.miljonsales.ui.add_details_dashboard.AddImages
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchFacility
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchSpeciality
import com.globalspace.miljonsales.ui.add_details_dashboard.setHospitalDetails
import com.globalspace.miljonsales.utils.Internet
import com.google.gson.JsonObject
import com.karumi.dexter.PermissionToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AddDetailsRepository @Inject constructor(
    private val apiInterfaceNew: ApiInterfaceNew,
    private val context: Context,
    private val db: AppDatabase,
    private val appPreference: AppPreference,
    private val permission: GrantPermission
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val flag_checkpermission = MutableLiveData<Boolean>()
    val flag_checklocationpermission = MutableLiveData<Boolean>()
    var return_FacilityResponse = MutableLiveData<List<FetchFacility>>()
    var return_SpecialityResponse = MutableLiveData<List<FetchSpeciality>>()
    var return_HospitalResponse = MutableLiveData<List<FetchHospital>>()
    var return_StateResponse = MutableLiveData<List<FetchGeography>>()
    var return_CityResponse = MutableLiveData<List<FetchGeography>>()

    suspend fun FetchHospFacility(progressBar: ProgressBar) = FetchFacilityList(progressBar)
    suspend fun FetchSpeciality(progressBar: ProgressBar) = FetchSpecialityList(progressBar)
    suspend fun FetchHospital(progressBar: ProgressBar) = FetchHospitalList(progressBar)
    suspend fun FetchState(progressBar: ProgressBar) = FetchGeoList(progressBar)
    suspend fun FetchCity(progressBar: ProgressBar,state: String) = FetchCityList(progressBar,state)

  /*  fun FetchHospFacility() = db.daoDb().getAllFacility()
    fun FetchSpeciality() = db.daoDb().getAllSpeciality()
    fun FetchHospital() = db.daoDb().getAllHospital()
     fun FetchState() = db.daoDb().getAllState()*/

    fun FetchConsumption() = db.daoDb().getAllConsumption()
    fun FetchStrength(molecule: List<String>) = db.daoDb().getAllStrength(molecule)
    fun FetchBrand(strength: String) = db.daoDb().getAllBrand(strength)
    fun FetchCompetitorBrand(strength: String) = db.daoDb().getAllCompetitorBrand(strength)

    suspend fun SubmitRCPAImages(
        path: String,
        text: String,
        flag: String,
        addDetailsProgressbar: ProgressBar
    ) {
        if (Internet.checkConnection(context)) {
            val file = File(path)
            val requestfile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body =
                MultipartBody.Part.createFormData("image", file.name, requestfile)
            val fn_name =
                RequestBody.create(
                    MediaType.parse("multipart/form-data"),
                    "SetHospitalImagesData"
                )
            val empID = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                appPreference.getUserId()
            )
            val imagetext =
                RequestBody.create(MediaType.parse("multipart/form-data"), text)
            val strflag =
                RequestBody.create(MediaType.parse("multipart/form-data"), flag)
            withContext(Dispatchers.IO) {
                try {
                    val result = apiInterfaceNew.submit_HospitalImages(
                        body,
                        fn_name,
                        empID,
                        imagetext,
                        strflag
                    )
                    Log.i("tag", "init data  ${result.body().toString()}")
                    withContext(Dispatchers.Main) {
                        // addDetailsProgressbar.visibility = View.GONE
                        if (result.isSuccessful) {
                            result.body()?.let {
                                if (it.status.equals("200")) {
                                    Toast.makeText(
                                        context,
                                        it.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else
                                    Toast.makeText(
                                        context,
                                        it.errorMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                            }
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        //  addDetailsProgressbar.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "failure ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    val msg = e.message
                    Log.i("tag", e.message.toString())
                }
            }
        } else {
            Toast.makeText(
                context,
                "Please check Internet Connection",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    suspend fun SubmitRCPA(
        activity: AddDetailsActivity,
        all_data: JsonObject,
        addDetailsProgressbar: ProgressBar
    ) {
        if (Internet.checkConnection(context)) {
            var data = setHospitalDetails(all_data)
            withContext(Dispatchers.IO) {
                try {
                    val result = apiInterfaceNew.submit_HospitalDetails(data)
                    Log.i("tag", "init data  ${result.body().toString()}")
                    withContext(Dispatchers.Main) {
                        if (result.isSuccessful) {
                            addDetailsProgressbar.visibility = View.GONE
                            result.body()?.let {
                                if (it.status.equals("200")) {
                                    Toast.makeText(
                                        context,
                                        it.response.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        addDetailsProgressbar.visibility = View.GONE
                        activity.finish()
                    }
                    val msg = e.message
                    Log.i("exception", e.message.toString())
                }
            }
        } else {
            addDetailsProgressbar.visibility = View.GONE
            Toast.makeText(
                context,
                "Please check Internet Connection",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    suspend fun FetchFacilityList(progressBarDashboard: ProgressBar) : LiveData<List<FetchFacility>>{
        withContext(Dispatchers.IO) {
            try {
                val result = apiInterfaceNew.add_details_facility("GetHospitalFacility")
                Log.i("tag", "facility data  ${result.body().toString()}")
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        progressBarDashboard.visibility = View.GONE
                        result.body()?.let {
                            if (it.status.equals("200")) {
                                return_FacilityResponse.value = it.response
                                /*withContext(Dispatchers.IO) {
                                    db.daoDb().deleteFACILITY()
                                    db.daoDb().insertHospitalFacility(it.response)
                                }*/
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
        return return_FacilityResponse
    }

    suspend fun FetchSpecialityList(progressBarDashboard: ProgressBar) : LiveData<List<FetchSpeciality>> {
        withContext(Dispatchers.IO) {
            try {
                val result = apiInterfaceNew.add_details_speciality("GetHospitalSpeciality")
                Log.i("tag", "Speciality data  ${result.body().toString()}")
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        progressBarDashboard.visibility = View.GONE
                        result.body()?.let {
                            if (it.status.equals("200")) {
                                return_SpecialityResponse.value = it.response
                               /* withContext(Dispatchers.IO) {
                                    db.daoDb().deleteSPECIALITY_TYPES()
                                    db.daoDb().insertHospSpeciality(it.response)
                                }*/
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
        return return_SpecialityResponse
    }

    suspend fun FetchHospitalList(progressBarDashboard: ProgressBar) : LiveData<List<FetchHospital>> {
        withContext(Dispatchers.IO) {
            try {
                val result = apiInterfaceNew.add_details_hospital("GetHospitalTypes")
                Log.i("tag", "type of hospital data  ${result.body().toString()}")
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        progressBarDashboard.visibility = View.GONE
                        result.body()?.let {
                            if (it.status.equals("200")) {
                                return_HospitalResponse.value = it.response
                               /* withContext(Dispatchers.IO) {
                                    db.daoDb().deleteHOSPITAL_TYPES()
                                    db.daoDb().insertHospitalList(it.response)
                                }*/
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
        return return_HospitalResponse
    }

    suspend fun FetchGeoList(progressBarDashboard: ProgressBar) : LiveData<List<FetchGeography>>{
        withContext(Dispatchers.IO) {
            try {
                val result = apiInterfaceNew.add_details_citymaster("GetStateMaster")
                Log.i("tag", "geo list data  ${result.body().toString()}")
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        progressBarDashboard.visibility = View.GONE
                        result.body()?.let {
                            if (it.status.equals("200")) {
                                return_StateResponse.value = it.response
                                /*withContext(Dispatchers.IO) {
                                    db.daoDb().deleteCITY_MASTER()
                                    db.daoDb().insertList(it.response)
                                }*/
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
        return return_StateResponse
    }
    suspend fun FetchCityList(progressBarDashboard: ProgressBar,state: String) : LiveData<List<FetchGeography>>{
        withContext(Dispatchers.IO) {
            try {
                val result = apiInterfaceNew.add_details_citymaster("GetCityMaster", state)
                Log.i("tag", "geo list data  ${result.body().toString()}")
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        progressBarDashboard.visibility = View.GONE
                        result.body()?.let {
                            if (it.status.equals("200")) {
                                return_CityResponse.value = it.response
                                /*withContext(Dispatchers.IO) {
                                    db.daoDb().deleteCITY_MASTER()
                                    db.daoDb().insertList(it.response)
                                }*/
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
        return return_CityResponse
    }

}


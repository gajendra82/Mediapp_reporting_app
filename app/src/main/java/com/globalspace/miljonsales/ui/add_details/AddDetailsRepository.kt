package com.globalspace.miljonsales.ui.add_details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.MutableLiveData
import com.globalspace.miljonsales.interface_.di.GrantPermission
import com.globalspace.miljonsales.local_db.database.AppDatabase
import com.globalspace.miljonsales.retrofit.ApiInterfaceNew
import com.globalspace.miljonsales.ui.add_details_dashboard.setHospitalDetails
import com.globalspace.miljonsales.utils.Internet
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    private val permission: GrantPermission
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val flag_checkpermission = MutableLiveData<Boolean>()
    val flag_checkgallerypermission = MutableLiveData<Boolean>()

    fun FetchState() = db.daoDb().getAllState()

    fun FetchStateCity(statecode: Int) = db.daoDb().getAllStateCity(statecode)

    fun FetchHospital() = db.daoDb().getAllHospital()

    fun FetchHospFacility() = db.daoDb().getAllFacility()

    fun FetchSpeciality() = db.daoDb().getAllSpeciality()

    fun FetchConsumption() = db.daoDb().getAllConsumption()
    fun FetchStrength(molecule: List<String>) = db.daoDb().getAllStrength(molecule)
    fun FetchBrand(strength : String) = db.daoDb().getAllBrand(strength)
    fun FetchCompetitorBrand(strength : String) = db.daoDb().getAllCompetitorBrand(strength)

    suspend fun SubmitRCPAImages(
        employeeID: String,
        path: String,
        text: String,
        flag : String,
        addDetailsProgressbar: ProgressBar
    ) {
        if (Internet.checkConnection(context)) {
            val file = File(path)
            val namae = file.name
            val requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("image", file.name, requestfile)
            val fn_name =
                RequestBody.create(MediaType.parse("multipart/form-data"), "SetHospitalImagesData")
            val empID = RequestBody.create(MediaType.parse("multipart/form-data"), employeeID)
            val imagetext = RequestBody.create(MediaType.parse("multipart/form-data"), text)
            val strflag = RequestBody.create(MediaType.parse("multipart/form-data"), flag)
            withContext(Dispatchers.IO) {
                try {
                    val result = apiInterfaceNew.submit_HospitalImages(body, fn_name, empID,imagetext,strflag)
                    Log.i("tag", "init data  ${result.body().toString()}")
                    withContext(Dispatchers.Main) {
                        addDetailsProgressbar.visibility = View.GONE
                        if (result.isSuccessful) {
                            result.body()?.let {
                                if (it.status.equals("200")) {
                                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                                }else
                                    Toast.makeText(context,it.errorMessage,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        addDetailsProgressbar.visibility = View.GONE
                        Toast.makeText(context,"failure ${e.message}",Toast.LENGTH_SHORT).show()
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
                    withContext(Dispatchers.Main){
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

}


package com.globalspace.miljonsales.ui.add_details

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.interface_.di.AppPreference
import com.globalspace.miljonsales.interface_.di.GrantPermission
import com.globalspace.miljonsales.local_db.database.AppDatabase
import com.globalspace.miljonsales.local_db.entity.FetchGeography
import com.globalspace.miljonsales.local_db.entity.FetchHospital
import com.globalspace.miljonsales.ui.add_details_dashboard.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject
import kotlin.collections.ArrayList

class AddDetailsViewModel @Inject constructor(
    private val context: Context,
    private val repository: AddDetailsRepository,
    private val db: AppDatabase,
    private val appPreference: AppPreference,
    private val permission: GrantPermission
) : ViewModel() {

    val lstconsum_strength = MutableLiveData<FetchMolecule?>()
    val lstcompetitorstrength = MutableLiveData<FetchMolecule?>()
    val lstbrand = MutableLiveData<FetchMolecule?>()
    val lstcompetitorBrand = MutableLiveData<FetchMolecule?>()
    val lstdata = MutableLiveData<FetchGeography>()
    val lststockdata = MutableLiveData<FetchGeography?>()
    val lststockcitydata = MutableLiveData<FetchGeography?>()
    val lstcitydata = MutableLiveData<FetchGeography>()
    val lsthospdata = MutableLiveData<FetchHospital>()
    val lstfacility = ArrayList<FetchFacility>()
    val lstspeciality = ArrayList<FetchSpeciality>()
    val lstmolecule = ArrayList<FetchMolecule>()
    val lststrength = ArrayList<FetchMolecule>()
    val lstfacilitydata = MutableLiveData<List<FetchFacility>>()
    val lstspecialitydata = MutableLiveData<List<FetchSpeciality>>()
    val lstmoleculedata = MutableLiveData<List<FetchMolecule>>()
    val lststrengthdata = MutableLiveData<List<FetchMolecule>>()
    val lstconsumptiondata = MutableLiveData<List<Add_Consumption_items>>()
    val lstcbdata = MutableLiveData<List<Add_CompetitorBrand>>()
    val lstpurchase_authoritydata = MutableLiveData<List<Add_Purchase_Authority_items>>()
    val lststockistdata = MutableLiveData<List<Add_Stockist_items>>()
    val boardimage = MutableLiveData<Uri>()
    val physicianimage = MutableLiveData<Uri>()
    var CurrentFlag: String = "AddDetails"
    val checkpermission: LiveData<Boolean>
        get() = repository.flag_checkpermission
    val checklocationpermission: LiveData<Boolean>
        get() = repository.flag_checklocationpermission
    private lateinit var sPref: SharedPreferences

    var lst_strmolecule = ArrayList<String>()

    var data = ArrayList<Add_Consumption_items>()
    var datapurchase = ArrayList<Add_Purchase_Authority_items>()
    var datastockist = ArrayList<Add_Stockist_items>()
    var datacompetitorbrand = ArrayList<Add_CompetitorBrand>()

    var strcomment = MutableLiveData<String>()
    var strobservation = MutableLiveData<String>()

    var strgstno = MutableLiveData<String>()
    var strpanno = MutableLiveData<String>()

    var strname = MutableLiveData<String>()
    var straddress = MutableLiveData<String>()
    var strpincode = MutableLiveData<String>()
    var strrooms = MutableLiveData<String>()
    var strbeds = MutableLiveData<String>()

    var strotherreason = MutableLiveData<String?>()
    var strradionselected = MutableLiveData<String?>()
    var strreasonselected = MutableLiveData<String?>()

    var strquantity = MutableLiveData<String>()
    var strrate = MutableLiveData<String>()
    var strmrp = MutableLiveData<String>()

    var strquantitycb = MutableLiveData<String>()
    var strmrpcb = MutableLiveData<String>()

    var strstockname = MutableLiveData<String>()
    var strstockaddress = MutableLiveData<String>()
    var strstockpincode = MutableLiveData<String>()
    var strstockcontactnum = MutableLiveData<String>()
    var strstockpersonalname = MutableLiveData<String>()

    var strpurch_authorityname = MutableLiveData<String>()
    var strpurch_authoritydesignation = MutableLiveData<String>()
    var strpurch_authoritycontactnumber = MutableLiveData<String>()

    val lstimagesdata = ArrayList<AddImages>()
    val lstimagesflag = ArrayList<String>()
    val mutablelstimagesdata = MutableLiveData<List<AddImages>>()

    val lstloc = ArrayList<Double>()
    var locationdata = MutableLiveData<Location>()

    //region addImages
    fun addImageData(addImages: AddImages) {
        if (lstimagesflag.size > 0) {
            if (!lstimagesflag.contains(addImages.Flag)) {
                lstimagesflag.add(addImages.Flag)
                lstimagesdata.add(addImages)
            } else {
                val pos = lstimagesflag.indexOf(addImages.Flag)
                lstimagesflag.remove(lstimagesflag[pos])
                lstimagesdata.remove(lstimagesdata[pos])

                lstimagesflag.add(pos, addImages.Flag)
                lstimagesdata.add(pos, addImages)
            }
        } else {
            lstimagesflag.add(addImages.Flag)
            lstimagesdata.add(addImages)
        }
        mutablelstimagesdata.value = lstimagesdata
    }

    //region facility add remove
    fun addFacility(fetchFacility: FetchFacility) {
        if (!lstfacility.contains(fetchFacility)) {
            lstfacility.add(fetchFacility)
            lstfacilitydata.value = lstfacility
        }
    }

    fun removeFacility(fetchFacility: FetchFacility) {
        lstfacility.remove(fetchFacility)
        lstfacilitydata.value = lstfacility
    }
    //endregion

    //region speciality add remove
    fun addSpeciality(fetchSpeciality: FetchSpeciality) {
        if (!lstspeciality.contains(fetchSpeciality)) {
            lstspeciality.add(fetchSpeciality)
            lstspecialitydata.value = lstspeciality
        }
    }

    fun removeSpeciality(fetchSpeciality: FetchSpeciality) {
        lstspeciality.remove(fetchSpeciality)
        lstspecialitydata.value = lstspeciality
    }
    //endregion

    // region molecule add remove
    fun addMolecule(fetchMolecule: FetchMolecule) {
        if (!lstmolecule.contains(fetchMolecule)) {
            lstmolecule.add(fetchMolecule)
            lst_strmolecule.add(fetchMolecule.MOLECULE!!)
            lstmoleculedata.value = lstmolecule
        }
    }

    fun removeMolecule(fetchMolecule: FetchMolecule) {
        lstmolecule.remove(fetchMolecule)
        lst_strmolecule.remove(fetchMolecule.MOLECULE!!)
        lstmoleculedata.value = lstmolecule
    }
    //endregion

    // region molecule add remove
    fun addStrength(fetchStrength: FetchMolecule) {
        if (!lststrength.contains(fetchStrength)) {
            lststrength.add(fetchStrength)
            lststrengthdata.value = lststrength
        }
    }

    fun removeStrength(fetchStrength: FetchMolecule) {
        lststrength.remove(fetchStrength)
        lststrengthdata.value = lststrength
    }
    //endregion


    fun ValidateName(text: String): Boolean {
        if (text.equals("") || text == null || text.equals("null")) {
            return false
        } else
            return true
    }

    fun ValidateContactNumber(text: String): Boolean {
        if (text.length < 10) {
            return false
        } else if (text.length == 0) {
            return false
        } else {
            return true
        }
    }

    fun ValidatePincode(text: String): Boolean {
        if (text.length < 6) {
            return false
        } else if (text.length == 0) {
            return false
        } else {
            return true
        }
    }

    fun ValidateState(): Boolean {
        lstdata.value?.let {
            return true
        }
        return false
    }

    fun ValidateCity(): Boolean {
        lstcitydata.value?.let {
            return true
        }
        return false
    }

    fun ValidateStockState(): Boolean {
        lststockdata.value?.let {
            return true
        }
        return false
    }

    fun ValidateStockCity(): Boolean {
        lststockcitydata.value?.let {
            return true
        }
        return false
    }

    fun ValidateHospital(): Boolean {
        lsthospdata.value?.let {
            return true
        }
        return false
    }

    fun ValidateBoardImage(): Boolean {
        boardimage.value?.let {
            return true
        }
        return false
    }

    fun ValidatePhysicianImage(): Boolean {
        physicianimage.value?.let {
            return true
        }
        return false
    }


    fun ValidatePincode(text: String, textinp_pincode: TextInputLayout) {
        if (text.length < 6) {
            textinp_pincode.error = "Please Enter 6 digit pincode"
        } else if (text.length == 0) {
            textinp_pincode.error = "Please Enter Pincode"
        } else {
            textinp_pincode.error = null
        }
    }

    fun ValidateContactNumber(text: String, textinp_cn: TextInputLayout) {
        if (text.length < 10) {
            textinp_cn.error = "Please Enter 10 digit Contact Number"
        } else if (text.length == 0) {
            textinp_cn.error = "Please Enter Contact Number"
        } else {
            textinp_cn.error = null
        }
    }

    fun ValidatePanNo(text: String, textinp_cn: TextInputLayout) {
        //It should be ten characters long.
        //The first five characters should be any upper case alphabets.
        //The next four-characters should be any number from 0 to 9.
        //The last(tenth) character should be any upper case alphabet.
        //It should not contain any white spaces.
        val regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}"
        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(text)
        if (matcher.matches() || text.length == 0 || text == null || text.equals("null")) {
            textinp_cn.error = null
        } else {
            textinp_cn.error = "Please Enter Valid PAN No"
        }
    }

    fun ValidateGstNo(text: String, textinp_cn: TextInputLayout) {
        //It should be 15 characters long.
        //The first 2 characters should be a number.
        //The next 10 characters should be the PAN number of the taxpayer.
        //The 13th character (entity code) should be a number from 1-9 or an alphabet.
        //The 14th character should be Z.
        //The 15th character should be an alphabet or a number.
        val regex = ("^[0-9]{2}[A-Z]{5}[0-9]{4}"
                + "[A-Z]{1}[1-9A-Z]{1}"
                + "Z[0-9A-Z]{1}$")
        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(text)
        if (matcher.matches() || text.length == 0 || text.equals("null")) {
            textinp_cn.error = null
        } else {
            textinp_cn.error = "Please Enter Valid GST No"
        }

    }

    fun ValidateEmailAddress(text: String, textinp_cn: TextInputLayout) {
        val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";

        if (EMAIL_REGEX.toRegex().matches(text)) {
            textinp_cn.error = null
        } else
            textinp_cn.error = "Please Enter Valid Email ID"
    }

    fun ValidateGstNo(text: String): Boolean {
        //It should be 15 characters long.
        //The first 2 characters should be a number.
        //The next 10 characters should be the PAN number of the taxpayer.
        //The 13th character (entity code) should be a number from 1-9 or an alphabet.
        //The 14th character should be Z.
        //The 15th character should be an alphabet or a number.
        val regex = ("^[0-9]{2}[A-Z]{5}[0-9]{4}"
                + "[A-Z]{1}[1-9A-Z]{1}"
                + "Z[0-9A-Z]{1}$")
        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(text)
        if (matcher.matches() || text.length == 0 || text.equals("null")) {
            return true
        } else {
            return false
        }

    }

    fun ValidatePanNo(text: String): Boolean {
        //It should be ten characters long.
        //The first five characters should be any upper case alphabets.
        //The next four-characters should be any number from 0 to 9.
        //The last(tenth) character should be any upper case alphabet.
        //It should not contain any white spaces.
        val regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}"
        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(text)
        if (matcher.matches() || text.length == 0 || text == null || text.equals("null")) {
            return true
        } else {
            return false
        }
    }

    fun ValidateEmailAddress(text: String): Boolean {
        val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";

        if (EMAIL_REGEX.toRegex().matches(text)) {
            return true
        } else
            return false
    }


    fun ValidateCompetitorBrand(): Boolean {
        lstcompetitorBrand.value?.let {
            return true
        }
        return false
    }

    fun ValidateCompetitorStrength(): Boolean {
        lstcompetitorstrength.value?.let {
            return true
        }
        return false
    }

    fun ValidateStrength(): Boolean {
        lstconsum_strength.value?.let {
            return true
        }
        return false
    }

    fun ValidateBrand(): Boolean {
        lstbrand.value?.let {
            return true
        }
        return false
    }

    internal fun fetchStateData(): LiveData<List<FetchGeography>>? {
        return repository.FetchState()
    }

    fun fetchStateData(state: String): LiveData<FetchGeography>? {
        return repository.FetchState(state)
    }

    fun fetchCityData(city: String): LiveData<FetchGeography>? {
        return repository.FetchCity(city)
    }

    internal fun fetchStateCityData(statecode: Int): LiveData<List<FetchGeography>>? {
        return repository.FetchStateCity(statecode)
    }

    internal fun fetchHospData(): LiveData<List<FetchHospital>>? {
        Log.i("tag", "viewmodel")
        return repository.FetchHospital()
    }

    internal fun fetchFacilityData(): LiveData<List<FetchFacility>>? {
        return repository.FetchHospFacility()
    }

    internal fun fetchSpecialityData(): LiveData<List<FetchSpeciality>>? {
        return repository.FetchSpeciality()
    }

    internal fun fetchMoleculeData(): LiveData<List<FetchMolecule>>? {
        return repository.FetchConsumption()
    }

    internal fun fetchStrengthData(molecule: List<String>): LiveData<List<FetchMolecule>>? {
        return repository.FetchStrength(molecule)
    }

    internal fun fetchAllBrandData(strength: String): LiveData<List<FetchMolecule>>? {
        return repository.FetchBrand(strength)
    }

    internal fun fetchAllCompetitorBrandData(strength: String): LiveData<List<FetchMolecule>>? {
        return repository.FetchCompetitorBrand(strength)
    }

    fun callpermission(ctx: Context, getImage: ActivityResultLauncher<Uri>) {
        permission.validateCameraPermission(ctx, getImage, repository)
    }

    fun callgallerypermission(ctx: Context, getGalleryImage: ActivityResultLauncher<Intent>) {
        permission.validateGalleryPermission(ctx, getGalleryImage, repository)
    }

    fun calllocationpermission(
        ctx: Context,
        activity: FragmentActivity,
        requestPermissionLauncher: ActivityResultLauncher<String>
    ) {
        permission.validateLocationPermission(ctx, activity, requestPermissionLauncher, repository)
    }

    fun fetchStrengthData(): ArrayList<FetchMolecule> {
        var lststate = ArrayList<FetchMolecule>()

        lstmolecule.forEach {
            lststate.add(it.copy(isCheckFlag = false))
        }

        if (lststrength != null && lststrength.size > 0) {
            lststrength.forEachIndexed { index, fetchFacility1 ->
                for (i in 0..lststate.size - 1) {
                    if (lststate[i].STRENGTH.equals(fetchFacility1.STRENGTH)) {
                        lststate.remove(lststate[i])
                        lststate.add(i, fetchFacility1)
                    }
                }
            }
        }
        return lststate
    }

    fun addCompetitorBrand() {
        datacompetitorbrand.add(
            Add_CompetitorBrand(
                lstcompetitorstrength.value!!.STRENGTH.toString(),
                lstcompetitorBrand.value!!.BRAND.toString(),
                strquantitycb.value.toString(),
                strmrpcb.value.toString(),
            )
        )
        lstcbdata.value = datacompetitorbrand
    }

    fun addConsumption() {
        data.add(
            Add_Consumption_items(
                lstconsum_strength.value!!.STRENGTH.toString(),
                lstbrand.value!!.BRAND.toString(),
                strradionselected.value.toString(),
                strreasonselected.value.toString(),
                strotherreason.value.toString(),
                strquantity.value.toString(),
                strrate.value.toString(),
                strmrp.value.toString(),
            )
        )
        lstconsumptiondata.value = data
    }

    fun addStockist() {
        datastockist.add(
            Add_Stockist_items(
                strstockname.value.toString(),
                strstockaddress.value.toString(),
                lststockdata.value!!.STATE_NAME.toString(),
                lststockcitydata.value!!.STATE_NAME.toString(),
                strstockpersonalname.value.toString(),
                strstockcontactnum.value.toString(),
                strstockpincode.value.toString(),
            )
        )
        lststockistdata.value = datastockist
    }

    fun addPurchaseAuthority() {
        datapurchase.add(
            Add_Purchase_Authority_items(
                strpurch_authorityname.value.toString(),
                strpurch_authoritydesignation.value.toString(),
                strpurch_authoritycontactnumber.value.toString(),
            )
        )
        lstpurchase_authoritydata.value = datapurchase
    }

    fun clearCompetitorBrand() {
        strquantitycb.value = ""
        strmrpcb.value = ""
        lstcompetitorstrength.value = null
        lstcompetitorBrand.value = null
    }

    fun clearConsumption() {
        strquantity.value = ""
        strrate.value = ""
        strmrp.value = ""
        strotherreason.value = ""
        strreasonselected.value = ""
        lstconsum_strength.value = null
        lstbrand.value = null
    }

    fun clearStockist() {
        strstockname.value = ""
        strstockaddress.value = ""
        strstockcontactnum.value = ""
        strstockpersonalname.value = ""
        strstockpincode.value = ""
        lststockdata.value = null
        lststockcitydata.value = null
    }

    fun clearPurchaseAuthority() {
        strpurch_authorityname.value = ""
        strpurch_authoritydesignation.value = ""
        strpurch_authoritycontactnumber.value = ""
    }

    fun SubmitRCPAData(
        activity: AddDetailsActivity,
        addDetailsProgressbar: ProgressBar
    ) {
        viewModelScope.async {
            val obj_data = JsonObject()
            obj_data.addProperty("name", strname.value.toString())
            obj_data.addProperty("address", straddress.value.toString())
            obj_data.addProperty("statecode", lstdata.value!!.STATE_CODE.toString())
            obj_data.addProperty("state", lstdata.value!!.STATE_NAME.toString())
            obj_data.addProperty("citycode", lstcitydata.value!!.CITY_ID.toString())
            obj_data.addProperty("city", lstcitydata.value!!.CITY_NAME.toString())
            obj_data.addProperty("pincode", strpincode.value.toString())
            obj_data.addProperty("hospital", lsthospdata.value!!.HospitalType.toString())
            obj_data.addProperty("rooms", strrooms.value.toString())
            obj_data.addProperty("beds", strbeds.value.toString())

            val arrayfacility = JsonArray()
            lstfacilitydata.value!!.forEach {
                var objfacility_data = JsonObject()
                objfacility_data.addProperty("facility_id", it.ID)
                objfacility_data.addProperty("facility_name", it.Facility)
                arrayfacility.add(objfacility_data)
            }
            val arrayspeciality = JsonArray()
            lstspecialitydata.value!!.forEach {
                var objspeciality_data = JsonObject()
                objspeciality_data.addProperty("speciality_id", it.ID)
                objspeciality_data.addProperty("speciality_name", it.Speciality)
                arrayspeciality.add(objspeciality_data)
            }
            val arraymolecule = JsonArray()
            lstmoleculedata.value!!.forEach {
                var objmolecule_data = JsonObject()
                objmolecule_data.addProperty("product_id", it.PRODUCT_ID)
                objmolecule_data.addProperty("molecule", it.MOLECULE)
                arraymolecule.add(objmolecule_data)
            }
            val arraystrength = JsonArray()
            lststrengthdata.value!!.forEach {
                var objstrength_data = JsonObject()
                objstrength_data.addProperty("product_id", it.PRODUCT_ID)
                objstrength_data.addProperty("strength", it.STRENGTH)
                arraystrength.add(objstrength_data)
            }
            val arrayconsumption = JsonArray()
            lstconsumptiondata.value!!.forEach {
                var objconsumption_data = JsonObject()
                objconsumption_data.addProperty("consumption_strength", it.strength)
                objconsumption_data.addProperty("consumption_brand", it.brand)
                objconsumption_data.addProperty("consumption_availability", it.availability)
                objconsumption_data.addProperty("consumption_reason", it.reason)
                objconsumption_data.addProperty("consumption_reasonother", it.reasonother)
                objconsumption_data.addProperty("consumption_quantity", it.quantity)
                objconsumption_data.addProperty("consumption_rate", it.rate)
                objconsumption_data.addProperty("consumption_mrp", it.mrp)
                arrayconsumption.add(objconsumption_data)
            }

            val arraycompetitor = JsonArray()
            lstcbdata.value!!.forEach {
                var objcompetitorbrand = JsonObject()
                objcompetitorbrand.addProperty("competitorstrength", it.cbstrength)
                objcompetitorbrand.addProperty("competitiorbrand", it.cbbrand)
                objcompetitorbrand.addProperty("quantity", it.cbquantity)
                objcompetitorbrand.addProperty("mrp", it.cbmrp)
                arraycompetitor.add(objcompetitorbrand)
            }

            val arraypurchase_authority = JsonArray()
            lstpurchase_authoritydata.value!!.forEach {
                var objpurchase_authority_data = JsonObject()
                objpurchase_authority_data.addProperty("pa_name", it.paname)
                objpurchase_authority_data.addProperty("pa_designation", it.padesignation)
                objpurchase_authority_data.addProperty("pa_contactnumber", it.pacontactnumber)
                arraypurchase_authority.add(objpurchase_authority_data)
            }

            val obj_commentobservation = JsonObject()
            obj_commentobservation.addProperty("comment", strcomment.value.toString())
            obj_commentobservation.addProperty("observation", strobservation.value.toString())

            val arraystockist = JsonArray()
            lststockistdata.value!!.forEach {
                var objstockist_data = JsonObject()
                objstockist_data.addProperty("stockist_name", it.stockistname)
                objstockist_data.addProperty("stockist_address", it.stockistaddress)
                objstockist_data.addProperty(
                    "stockist_statecode",
                    lststockdata.value!!.STATE_CODE.toString()
                )
                objstockist_data.addProperty(
                    "stockist_state",
                    lststockdata.value!!.STATE_NAME.toString()
                )
                objstockist_data.addProperty(
                    "stockist_citycode",
                    lststockcitydata.value!!.CITY_ID.toString()
                )
                objstockist_data.addProperty(
                    "stockist_city",
                    lststockcitydata.value!!.CITY_NAME.toString()
                )
                objstockist_data.addProperty("stockist_personalname", it.stockistpersonalname)
                objstockist_data.addProperty("stockist_number", it.stockistcontactnum)
                objstockist_data.addProperty("stockist_pincode", it.stockistpincode)
                arraystockist.add(objstockist_data)
            }

            val obj_kycdata = JsonObject()
            obj_kycdata.addProperty("gstno", strgstno.value.toString())
            obj_kycdata.addProperty("panno", strpanno.value.toString())

            sPref = context.getSharedPreferences(
                context.getResources().getString(R.string.app_name), Context.MODE_PRIVATE
            )
            val all_data = JsonObject()
            all_data.addProperty("function_name", "SetHospitalAllData")
            all_data.addProperty(
                "username", appPreference.getUserId()
            )
            all_data.add("hospitaldetails", obj_data)
            all_data.add("hospitalfacility", arrayfacility)
            all_data.add("hospitalspeciality", arrayspeciality)
            all_data.add("hospitalmolecule", arraymolecule)
            all_data.add("hospitalstrength", arraystrength)
            all_data.add("hospitalconsumption", arrayconsumption)
            all_data.add("hospitalcompetitor", arraycompetitor)
            all_data.add("hospitalpurchase_authority", arraypurchase_authority)
            all_data.add("hospitalcommentobservation", obj_commentobservation)
            all_data.add("hospitalstockist", arraystockist)
            all_data.add("hospitalkyc", obj_kycdata)

            Log.i("jsondata", all_data.toString())
            //endregion
            viewModelScope.async {
                repository.SubmitRCPA(
                    activity, all_data, addDetailsProgressbar
                )
            }.await()
            UploadMultiPartImages(addDetailsProgressbar, activity)
        }
    }

    fun UploadMultiPartImages(addDetailsProgressbar: ProgressBar, activity: AddDetailsActivity) {
        viewModelScope.launch {
            try {
                addDetailsProgressbar.visibility = View.VISIBLE
                sPref = context.getSharedPreferences(
                    context.getResources().getString(R.string.app_name), Context.MODE_PRIVATE
                )
                for (i in lstimagesdata.indices) {
                    val flag: String = lstimagesdata[i].Flag
                    val path: String = lstimagesdata[i].ImagePath
                    val text: String = lstimagesdata[i].ImageText

                    //Uploading code
                    try {
                        viewModelScope.async {
                            repository.SubmitRCPAImages(
                                path, text, flag, addDetailsProgressbar
                            )
                        }.await()
                        activity.finish()
                        activity.getWindow()
                            .clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    } catch (exc: Exception) {
                        exc.printStackTrace()
                        /* Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();*/
                    }
                }
            } catch (e: Exception) {
                val msg = e.message
            }
        }
    }

     val locationRequest = LocationRequest.create().apply {
         interval = 3000
         fastestInterval = 3000
         priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
         maxWaitTime = 5000
     }

    var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(loctionResult: LocationResult) {
            val locationlist = loctionResult.locations
            if (locationlist.isNotEmpty()) {
                val location = locationlist.last()
                val latitude = location.latitude
                val longitude = location.longitude
                Log.d("tag", "location : ${latitude + longitude}")
                try {
                    /*lstloc.add(0, latitude)
                    lstloc.add(1, longitude)*/
                    locationdata.value = location
                } catch (e: Exception) {
                    Log.d("tag", "location : ${e.message}")
                }
            }else{

            }
        }
    }

}
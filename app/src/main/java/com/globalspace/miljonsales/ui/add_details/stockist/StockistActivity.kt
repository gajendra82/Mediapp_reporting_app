package com.globalspace.miljonsales.ui.add_details.stockist

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.globalspace.miljonsales.MyApplication
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.ActivityConsumptionBinding
import com.globalspace.miljonsales.databinding.ActivityStockistBinding
import com.globalspace.miljonsales.ui.add_details.AddDetaillsDialog
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.consumptions.ConsumptionFragment
import com.globalspace.miljonsales.utils.WindowBar
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*
import javax.inject.Inject

class StockistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockistBinding
    private var addDetailsViewModel: AddDetailsViewModel? = null
    internal val dialog = AddDetaillsDialog()

    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var geocoder: Geocoder
    var check_flag = "new"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stockist)
        binding.lifecycleOwner = this
        WindowBar.setStatusColor(window, this@StockistActivity)
        (application as MyApplication).applicationComponent.inject(this)
        try {
            this.addDetailsViewModel = StockistFragment.addDetailsViewModel
        } catch (e: Exception) {
            val msg = e.message
        }
        binding.stockistData = addDetailsViewModel
        binding.toolbarStockist.setNavigationOnClickListener {
            onBackPressed()
        }
        geocoder = Geocoder(this, Locale.getDefault())
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        binding?.let{
            it.edittextstockState.setOnClickListener {
                Log.i("tag", "clicked")
                dialog.newInstance(addDetailsViewModel!!, "StockistState")
                dialog.show(supportFragmentManager, "Dialog")
            }
            it.edittextstockCity.setOnClickListener {
                Log.i("tag", "clicked")
                dialog.newInstance(addDetailsViewModel!!, "StockistCity")
                dialog.show(supportFragmentManager, "Dialog")
            }
            it.edittextstockName.doOnTextChanged { text, start, count, after ->
                if (addDetailsViewModel!!.ValidateName(text.toString())) {
                    it.textinpstockName.error = null
                } else
                    it.textinpstockName.error = "Please Enter Stockist Name"
            }
            it.edittextstockAddress.doOnTextChanged { text, start, count, after ->
                if (addDetailsViewModel!!.ValidateName(text.toString())) {
                    it.textinpstockAddress.error = null
                } else
                    it.textinpstockAddress.error = "Please Enter Address"
            }
            it.edittextstockPincode.doOnTextChanged { text, start, count, after ->
                addDetailsViewModel!!.ValidatePincode(text.toString(), it.textinpstockPincode)
            }
            it.edittextcontactName.doOnTextChanged { text, start, count, after ->
                if (addDetailsViewModel!!.ValidateName(text.toString())) {
                    it.textinpcontactName.error = null
                } else
                    it.textinpcontactName.error = "Please Enter Contact Name"
            }
            it.edittextContactnum.doOnTextChanged { text, start, count, after ->
                addDetailsViewModel!!.ValidateContactNumber(text.toString(), it.textinpContactnum)
            }

            it.edittextstockAddress.setOnTouchListener(View.OnTouchListener { v, event ->
                val DRAWABLE_RIGHT = 2
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= it.edittextstockAddress.getRight() - it.edittextstockAddress.getCompoundDrawables()
                            .get(DRAWABLE_RIGHT).getBounds().width()
                    ) {
                        check_flag = "address"
                        addDetailsViewModel!!.calllocationpermission(
                            applicationContext,this@StockistActivity,
                            requestPermissionLauncher
                        )
                        return@OnTouchListener true
                    }
                }
                false
            })
            addDetailsViewModel!!.checklocationpermission.observe(this, Observer {
                if (it == true && check_flag.equals("address")) {
                    getCurrentLocation()
                }
            })

            addDetailsViewModel!!.lststockdata.observe(this, Observer {
                if (it == null)
                    binding!!.edittextstockState.setText("")
                else
                    binding!!.edittextstockState.setText(it!!.STATE_NAME.toString())
            })
            addDetailsViewModel!!.lststockcitydata.observe(this, Observer {
                if (it == null)
                    binding!!.edittextstockCity.setText("")
                else
                    binding!!.edittextstockCity.setText(it.CITY_NAME.toString())
            })
            it.btnAdd.setOnClickListener {
                AddDetailsValidation()
            }
        }

    }

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getCurrentLocation()
        } else {
            handlePermanentDeniedPermission()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getCurrentLocation() {
        if (isLocationEnabled()) {
            try {
                if (ActivityCompat.checkSelfPermission(
                       this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    //requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
                val lastLoction = fusedLocationProviderClient.lastLocation
                lastLoction.addOnSuccessListener {
                    val latitude = it.latitude
                    val longitude = it.longitude
                    Log.d("tag", "location : ${latitude + longitude}")

                    val address = geocoder.getFromLocation(latitude, longitude, 1)
                    binding!!.edittextstockAddress.setText(address[0].getAddressLine(0))
                    binding!!.edittextstockPincode.setText(address[0].postalCode)
                    addDetailsViewModel?.let {
                        it.fetchStateData(address[0].adminArea)!!.observe(this, Observer { data->
                            it.lststockdata.value = data

                        })

                        it.fetchCityData(address[0].locality)!!.observe(this, Observer { data->
                            it.lststockcitydata.value = data
                        })
                    }
                }
                lastLoction.addOnFailureListener {
                    Log.d("tag", "Failed to load location")
                }
            } catch (e: Exception) {
                val msg = e.message
            }
        } else {
            Toast.makeText(this, "Turn on Location", Toast.LENGTH_SHORT).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    fun handlePermanentDeniedPermission() {
        AlertDialog.Builder(this@StockistActivity)
            .setMessage(
                "It looks that you have turned off " +
                        "permissions required for these features. It can be enabled under " +
                        "applications settings"
            )
            .setPositiveButton("GO TO SETTINGS", DialogInterface.OnClickListener { dialog, i ->
                openSettings()
                dialog.dismiss()
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, i ->
                dialog.dismiss()
            })
            .show()
    }

    fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package",packageName, null)
        intent.data = uri
        startActivity(intent)
    }
    private fun AddDetailsValidation() {
        addDetailsViewModel?.let {
            if (it.ValidateName(it.strstockname.value.toString())) {
                if (it.ValidateName(it.strstockaddress.value.toString())) {
                    if(it.ValidateStockState()){
                        if(it.ValidateStockCity()){
                            if (it.ValidateName(it.strstockpersonalname.value.toString())) {
                                if (it.ValidateContactNumber(it.strstockcontactnum.value.toString())) {
                                    if (it.ValidatePincode(it.strstockpincode.value.toString())) {
                                        it.addStockist()
                                        onBackPressed()
                                    }else {
                                        Toast.makeText(this, "Please Enter Pincode", Toast.LENGTH_SHORT).show()
                                    }
                                }else {
                                    Toast.makeText(this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show()
                                }
                            }else {
                                Toast.makeText(this, "Please Enter Contact Name", Toast.LENGTH_SHORT).show()
                            }
                        }else {
                            Toast.makeText(this, "Please Select City", Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        Toast.makeText(this, "Please Select State", Toast.LENGTH_SHORT).show()
                    }
                }else {
                    Toast.makeText(this, "Please Enter Stockist Address", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "Please Enter Stockist Name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}
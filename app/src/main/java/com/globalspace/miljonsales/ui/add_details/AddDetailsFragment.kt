package com.globalspace.miljonsales.ui.add_details

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.*
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.globalspace.miljonsales.BuildConfig
import com.globalspace.miljonsales.MyApplication
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.FragmentAddDetailsBinding
import com.globalspace.miljonsales.local_db.entity.FetchGeography
import com.globalspace.miljonsales.ui.URIPathHelper
import com.globalspace.miljonsales.ui.add_details_dashboard.AddImages
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class AddDetailsFragment : Fragment(R.layout.fragment_add_details) {

    private var _binding: FragmentAddDetailsBinding? = null
    private val binding get() = _binding
    var uri: Uri? = null
    var flag_check: String = "new"
    lateinit var textRecognizer: TextRecognizer
    lateinit var geocoder: Geocoder
    var strImages = ""

    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var addDetailsViewModel: AddDetailsViewModel
    private lateinit var locationCallback: LocationCallback
    internal val dialog = AddDetaillsDialog()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            _binding = FragmentAddDetailsBinding.bind(view)
            (requireActivity().application as MyApplication).applicationComponent.inject(this)
            val map = (requireActivity().application as MyApplication).applicationComponent.getMap()
            addDetailsViewModel =
                ViewModelProvider(requireActivity(), mainviewmodelFactory).get(
                    AddDetailsViewModel::class.java
                )
            binding!!.addDetails = addDetailsViewModel
            addDetailsViewModel.CurrentFlag = "Add Details"
            geocoder = Geocoder(requireContext(), Locale.getDefault())
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext())

            addDetailsViewModel.locationdata?.observe(viewLifecycleOwner){
                if(it != null) {
                    binding!!.addressProgressbar.visibility = View.GONE
                    fusedLocationProviderClient.removeLocationUpdates(addDetailsViewModel.locationCallback)
                    val address = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                    binding!!.edittextAddress.setText(address[0].getAddressLine(0))
                    binding!!.edittextPincode.setText(address[0].postalCode)
                    addDetailsViewModel.fetchStateData(address[0].adminArea)!!
                        .observe(viewLifecycleOwner) {
                            addDetailsViewModel.lstdata.value = it
                        }
                    addDetailsViewModel.fetchCityData(address[0].locality)!!
                        .observe(viewLifecycleOwner) {
                            addDetailsViewModel.lstcitydata.value = it
                        }
                }
            }

            addDetailsViewModel.lstdata.observe(viewLifecycleOwner) {
                binding!!.edittextState.setText(it.STATE_NAME.toString())
            }
            addDetailsViewModel.lstcitydata.observe(viewLifecycleOwner) {
                if (it == null)
                    binding!!.edittextCity.setText("")
                else
                    binding!!.edittextCity.setText(it.CITY_NAME.toString())
            }
            addDetailsViewModel.lsthospdata.observe(viewLifecycleOwner) {
                binding!!.edittextHospital.setText(it.HospitalType.toString())
            }
            addDetailsViewModel.boardimage.observe(viewLifecycleOwner) {
                binding!!.imgViewer.visibility = View.VISIBLE
                binding!!.imgViewer.setImageURI(it)
            }
            addDetailsViewModel.physicianimage.observe(viewLifecycleOwner) {
                binding!!.imgViewerPhy.visibility = View.VISIBLE
                binding!!.imgViewerPhy.setImageURI(it)
            }
            addDetailsViewModel.checkpermission.observe(viewLifecycleOwner, Observer {
                if (it == true && !flag_check.equals("new")) {
                    invokeCamera()
                }
            })
            addDetailsViewModel.checklocationpermission.observe(viewLifecycleOwner, Observer {
                if (it == true) {
                    getCurrentLocation()
                }
            })

            binding?.let {
                it.edittextName.doOnTextChanged { text, start, count, after ->
                    if (addDetailsViewModel.ValidateName(text.toString())) {
                        it.textinpName.error = null
                    } else
                        it.textinpName.error = "Please Enter Name"
                }

                it.edittextAddress.doOnTextChanged { text, start, count, after ->
                    if (addDetailsViewModel.ValidateName(text.toString())) {
                        it.textinpAddress.error = null
                    } else
                        it.textinpAddress.error = "Please Enter Address"
                }

                it.edittextPincode.doOnTextChanged { text, start, count, after ->
                    addDetailsViewModel.ValidatePincode(text.toString(), it.textinpPincode)
                }

                it.edittextState.setOnClickListener {
                    Log.i("tag", "clicked")
                    dialog.newInstance(addDetailsViewModel, "State")
                    dialog.show((context as AppCompatActivity).supportFragmentManager, "Dialog")
                }

                it.edittextCity.setOnClickListener {
                    Log.i("tag", "clicked")
                    dialog.newInstance(addDetailsViewModel, "City")
                    dialog.show((context as AppCompatActivity).supportFragmentManager, "Dialog")
                }
                it.edittextHospital.setOnClickListener {
                    Log.i("tag", "clicked")
                    dialog.newInstance(addDetailsViewModel, "Hospital")
                    dialog.show((context as AppCompatActivity).supportFragmentManager, "Dialog")
                }

                it.tvborad.setOnClickListener {
                    flag_check = "board"
                    addDetailsViewModel.callpermission(requireContext(), getImage)
                }
                it.tvphysician.setOnClickListener {
                    flag_check = "physician"
                    addDetailsViewModel.callpermission(requireContext(), getImage)
                }

                it.edittextAddress.setOnTouchListener(OnTouchListener { v, event ->
                    val DRAWABLE_RIGHT = 2
                    if (event.action == MotionEvent.ACTION_UP) {
                        if (event.rawX >= it.edittextAddress.getRight() - it.edittextAddress.getCompoundDrawables()
                                .get(DRAWABLE_RIGHT).getBounds().width()
                        ) {
                            addDetailsViewModel.calllocationpermission(
                                requireContext(),
                                requireActivity(),
                                requestPermissionLauncher
                            )
                            return@OnTouchListener true
                        }
                    }
                    false
                })
            }

        } catch (e: Exception) {
            val msg = e.message
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "reporting_${timestamp}",
            null
        )
        return Uri.parse(path)
    }

    val getImage = registerForActivityResult(
        ActivityResultContracts.TakePicture(),
        ActivityResultCallback { success ->
            if (success) {
                val bitmap = BitmapFactory.decodeFile(strImages)
                val uriPathHelper = URIPathHelper()
                val filePath = uriPathHelper.getPath(
                    requireContext(),
                    getImageUri(requireContext(), bitmap)!!
                )
                if (flag_check.equals("board")) {
                    binding!!.imgViewer.visibility = View.VISIBLE
                    binding!!.imgViewer.setImageURI(uri)
                    addDetailsViewModel.boardimage.value = uri
                    FetchTextFromImage(flag_check, uri!!, filePath)
                } else {
                    binding!!.imgViewerPhy.visibility = View.VISIBLE
                    binding!!.imgViewerPhy.setImageURI(uri)
                    addDetailsViewModel.physicianimage.value = uri
                    FetchTextFromImage(flag_check, uri!!, filePath)
                }

                flag_check = "new"

            } else {
                // Toast.makeText(context, "Errorr", Toast.LENGTH_LONG).show()
                if (flag_check.equals("board"))
                    binding!!.imgViewer.visibility = View.GONE
                else
                    binding!!.imgViewerPhy.visibility = View.GONE
                flag_check = "new"
            }
        }
    )
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getCurrentLocation()
        } else {
            handlePermanentDeniedPermission()
        }
    }

    private fun FetchTextFromImage(flag: String, uri: Uri, filepath: String?) {
        try {
            textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val inputImage = InputImage.fromFilePath(requireContext(), uri)
            val texttaskResult = textRecognizer.process(inputImage)
                .addOnSuccessListener {
                    val text = it.text
                    addDetailsViewModel.addImageData(AddImages(flag, filepath!!, text))
                }.addOnFailureListener {

                }
        } catch (e: Exception) {
            val msg = e.message
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getCurrentLocation() {
       /* if (isLocationEnabled()) {*/
            try {
                if (ActivityCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }else{

                    val builder = LocationSettingsRequest.Builder().addLocationRequest(addDetailsViewModel.locationRequest)
                    val task = LocationServices.getSettingsClient(requireActivity()).checkLocationSettings(builder.build())
                    task.addOnSuccessListener{
                            response->
                        val states = response.locationSettingsStates
                        if(states!!.isLocationPresent){
                            binding!!.addressProgressbar.visibility = View.VISIBLE
                            fusedLocationProviderClient?.requestLocationUpdates(addDetailsViewModel.locationRequest,addDetailsViewModel.locationCallback,
                                Looper.getMainLooper())
                        }
                    }.addOnFailureListener { e->
                        val statuscode = (e as ResolvableApiException).statusCode
                        if(statuscode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED){
                            try{
                                val intentSenderRequest = IntentSenderRequest.Builder(e.resolution).build()
                                resultLauncherGPS.launch(intentSenderRequest)
                            }catch (sendEx: IntentSender.SendIntentException){}
                        }
                    }
                  /* fusedLocationProviderClient?.requestLocationUpdates(addDetailsViewModel.locationRequest,addDetailsViewModel.locationCallback,
                   Looper.myLooper()!!)*/
                }
               /* val lastLoction = fusedLocationProviderClient.lastLocation
                lastLoction.addOnSuccessListener {
                    if(it != null){
                        val latitude = it.latitude
                        val longitude = it.longitude
                        Log.d("tag", "location : ${latitude + longitude}")

                        val address = geocoder.getFromLocation(latitude, longitude, 1)
                        binding!!.edittextAddress.setText(address[0].getAddressLine(0))
                        binding!!.edittextPincode.setText(address[0].postalCode)
                        addDetailsViewModel.fetchStateData(address[0].adminArea)!!
                            .observe(viewLifecycleOwner) {
                                addDetailsViewModel.lstdata.value = it
                            }
                        addDetailsViewModel.fetchCityData(address[0].locality)!!
                            .observe(viewLifecycleOwner) {
                                addDetailsViewModel.lstcitydata.value = it
                            }
                    }else{
                      Toast.makeText(context,"Failed to load location",Toast.LENGTH_SHORT).show()
                    }
                }
                lastLoction.addOnFailureListener {
                    Log.d("tag", "Failed to load location")
                }*/
            } catch (e: Exception) {
                val msg = e.message
            }
        /*} else {
            Toast.makeText(requireContext(), "Turn on Location", Toast.LENGTH_SHORT).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }*/
    }

    val resultLauncherGPS =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data?.data
                getCurrentLocation()
            }
        }
    private fun invokeCamera() {
        val file = CreateImageFile(requireContext())
        try {
            uri =
                FileProvider.getUriForFile(
                    requireContext(),
                    "${BuildConfig.APPLICATION_ID}.provider",
                    file
                )
        } catch (e: Exception) {
            val msg = e.message
        }
        strImages = file.absolutePath
        // addDetailsViewModel.strlstimages.add(file.absolutePath)
        getImage.launch(uri)
    }

    private fun CreateImageFile(context: Context): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val tmpfile = File.createTempFile("reporting_${timestamp}", ".jpg", imageDirectory)
        return tmpfile
    }

    fun handlePermanentDeniedPermission() {
        AlertDialog.Builder(context)
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
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        requireActivity().startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
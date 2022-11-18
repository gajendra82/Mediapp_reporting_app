package com.globalspace.miljonsales.utils.di

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.globalspace.miljonsales.interface_.di.GrantPermission
import com.globalspace.miljonsales.ui.add_details.AddDetailsRepository
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import javax.inject.Inject

class CheckPermission @Inject constructor() :
    GrantPermission {

    override fun validateCameraPermission(
        context: Context,
        getImage: ActivityResultLauncher<Uri>,
        repository: AddDetailsRepository
    ) {
        Dexter.withContext(context)
            // we will use with permission method since we are working with many permissions
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {

                        if (report.areAllPermissionsGranted()) {
                            /* Toast.makeText(context, "Permissions Granted", Toast.LENGTH_SHORT)
                                 .show()*/
                            repository.flag_checkpermission.value = true
                        } else {
                            Toast.makeText(
                                context,
                                "Please Grant Permissions to use the app",
                                Toast.LENGTH_SHORT
                            ).show()
                            showRationalDialogForPermissions(context)
                            repository.flag_checkpermission.value = false
                        }

                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

            }).withErrorListener {
                Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
            }.onSameThread().check()
    }

    override fun validateGalleryPermission(
        context: Context,
        getGalleryImage: ActivityResultLauncher<Intent>,
        repository: AddDetailsRepository
    ) {
        Dexter.withContext(context).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    getGalleryImage.launch(intent)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    showRationalDialogForPermissions(context)
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

            }).withErrorListener {
                Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
            }.onSameThread().check()
    }

    override fun validateLocationPermission(
        context: Context,
        activity: FragmentActivity,
        requestPermissionLauncher: ActivityResultLauncher<String>,
        repository: AddDetailsRepository
    ) {
        val fineLocation =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (fineLocation == PackageManager.PERMISSION_GRANTED && coarseLocation == PackageManager.PERMISSION_GRANTED) {
            repository.flag_checklocationpermission.value = true
        } else {
            if (fineLocation != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
            if (coarseLocation != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                ) {

                } else {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                }
            }
        }
    }

    private fun showRationalDialogForPermissions(context: Context) {
        AlertDialog.Builder(context).setMessage(
            "It looks that you have turned off " +
                    "permissions required for these features. It can be enabled under " +
                    "applications settings"
        )
            .setPositiveButton("GO TO SETTINGS")
            { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

}
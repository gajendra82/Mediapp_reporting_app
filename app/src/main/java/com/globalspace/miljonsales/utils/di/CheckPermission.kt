package com.globalspace.miljonsales.utils.di

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
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
                            //invokeCamera(context, getImage, repository)
                        } else {
                            Toast.makeText(
                                context,
                                "Please Grant Permissions to use the app",
                                Toast.LENGTH_SHORT
                            ).show()
                            repository.flag_checkpermission.value = false
                        }

                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                    repository.flag_checkpermission.value = true
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
            .withListener(object : PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    getGalleryImage.launch(intent)
                   // repository.flag_checkgallerypermission.value = true
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    TODO("Not yet implemented")
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    TODO("Not yet implemented")
                }

            }).withErrorListener {
                Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
            }.onSameThread().check()
    }

}
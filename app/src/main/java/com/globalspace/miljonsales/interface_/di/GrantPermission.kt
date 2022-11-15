package com.globalspace.miljonsales.interface_.di

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Window
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.MutableLiveData
import com.globalspace.miljonsales.ui.add_details.AddDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow

interface GrantPermission {

    fun validateCameraPermission(
        context: Context,
        getImage: ActivityResultLauncher<Uri>,
        repository: AddDetailsRepository
    )

    fun validateGalleryPermission(
        context: Context,
        getGalleryImage: ActivityResultLauncher<Intent>,
        repository: AddDetailsRepository
    )

}
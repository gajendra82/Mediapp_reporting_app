package com.globalspace.miljonsales.utils

import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.globalspace.miljonsales.R

object WindowBar {
     lateinit var window: Window
    fun setStatusColor(window_bar : Window,activity: AppCompatActivity){
        this.window = window_bar
        this.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.window.statusBarColor = activity.resources.getColor(R.color.colorPrimary)
    }
}
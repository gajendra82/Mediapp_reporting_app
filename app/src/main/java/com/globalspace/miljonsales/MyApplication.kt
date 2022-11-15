package com.globalspace.miljonsales

import android.app.Application
import android.graphics.Typeface
import android.view.Window
import android.view.WindowManager
import com.globalspace.miljonsales.di.ApplicationComponent
import com.globalspace.miljonsales.di.DaggerApplicationComponent

class MyApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
    var solidFont: Typeface? = null


    override fun onCreate() {
        super.onCreate()
         solidFont = Typeface.createFromAsset(assets, "fa-solid-900.ttf")
        applicationComponent = DaggerApplicationComponent.factory().create(this)

    }
}
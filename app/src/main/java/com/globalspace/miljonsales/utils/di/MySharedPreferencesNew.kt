package com.globalspace.miljonsales.utils.di

import android.content.Context
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.interface_.di.AppPreference
import javax.inject.Inject

class MySharedPreferencesNew @Inject constructor(private val context: Context) : AppPreference {

    private var appSharedPrefs =
        context.getSharedPreferences(
            context.getResources().getString(R.string.app_name), Context.MODE_PRIVATE
        )
    private var prefsEditor = appSharedPrefs.edit()

    override fun setUserId(userIDvalue: String) {
        prefsEditor.putString(context.getResources().getString(R.string.employee_id), userIDvalue)
        prefsEditor.commit()
    }

    override fun getUserId(): String? {
        return appSharedPrefs.getString(context.getResources().getString(R.string.employee_id), "")
    }
}
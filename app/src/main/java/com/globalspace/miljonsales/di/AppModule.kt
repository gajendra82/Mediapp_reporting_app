package com.globalspace.miljonsales.di

import com.globalspace.miljonsales.interface_.di.AppPreference
import com.globalspace.miljonsales.interface_.di.GrantPermission
import com.globalspace.miljonsales.utils.di.CheckPermission
import com.globalspace.miljonsales.utils.di.MySharedPreferencesNew
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun bindPermission(checkPermission: CheckPermission): GrantPermission

    @Binds
    abstract fun bindSharedPreferences(mySharedPreferences: MySharedPreferencesNew): AppPreference

}
package com.globalspace.miljonsales.interface_.di

interface AppPreference {

    fun setUserId(userIDvalue: String)
    fun getUserId(): String?
}
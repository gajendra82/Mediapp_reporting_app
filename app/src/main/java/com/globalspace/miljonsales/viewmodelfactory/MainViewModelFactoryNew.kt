package com.globalspace.miljonsales.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MainViewModelFactoryNew @Inject constructor(private val map : Map<Class<*>, @JvmSuppressWildcards ViewModel>) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return map[modelClass] as T
    }
}
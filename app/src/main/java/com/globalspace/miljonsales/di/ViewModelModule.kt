package com.globalspace.miljonsales.di

import androidx.lifecycle.ViewModel
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details_dashboard.AddDetailsDashboardViewModel
import com.globalspace.miljonsales.ui.add_details_dashboard.AddDetailsModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @ClassKey(AddDetailsDashboardViewModel::class)
    @IntoMap
    abstract fun viewmodeladd_details(addDetailsDashboardViewModel: AddDetailsDashboardViewModel) : ViewModel

    @Binds
    @ClassKey(AddDetailsViewModel::class)
    @IntoMap
    abstract fun viewmodeladddetails(addDetailsViewModel: AddDetailsViewModel) : ViewModel

}

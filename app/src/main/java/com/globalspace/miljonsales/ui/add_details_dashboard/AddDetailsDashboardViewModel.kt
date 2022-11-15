package com.globalspace.miljonsales.ui.add_details_dashboard

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globalspace.miljonsales.local_db.entity.FetchHospitalSummmary
import com.globalspace.miljonsales.utils.Internet
import kotlinx.coroutines.async
import javax.inject.Inject

class AddDetailsDashboardViewModel @Inject constructor(
    private val context: Context,
    private val addDetailsRepository: AddDetailsDashboardRepository
) : ViewModel() {

    fun StartGeoSync(progressBarDashboard: ProgressBar) {
        viewModelScope.async {
            if (Internet.checkConnection(context)) {
                progressBarDashboard.visibility = View.VISIBLE
                addDetailsRepository.InsertHospitalSummmary(progressBarDashboard)
                addDetailsRepository.InsertHospitalDetails(progressBarDashboard)
                addDetailsRepository.FetchGeoList(progressBarDashboard)
                addDetailsRepository.FetchHospitalList(progressBarDashboard)
                addDetailsRepository.FetchFacilityList(progressBarDashboard)
                addDetailsRepository.FetchSpecialityList(progressBarDashboard)
                addDetailsRepository.FetchConsumptionList(progressBarDashboard)
            } else
                Toast.makeText(
                    context, "Please check Internet Connection",
                    Toast.LENGTH_SHORT
                )
                    .show()
        }
    }

    internal fun fetchHospSummaryData(): LiveData<List<FetchHospitalSummmary>>? {
        Log.i("tag", "viewmodel")
        return addDetailsRepository.FetchHospitalSummary()
    }
    internal fun fetchHospDetailsData(): LiveData<List<DashboardData>>? {
        Log.i("tag", "viewmodel")
        return addDetailsRepository.FetchAllHospital()
    }
}
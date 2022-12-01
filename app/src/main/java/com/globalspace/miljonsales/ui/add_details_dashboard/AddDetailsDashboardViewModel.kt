package com.globalspace.miljonsales.ui.add_details_dashboard

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.text.bold
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.interface_.di.AppPreference
import com.globalspace.miljonsales.local_db.entity.FetchHospitalSummmary
import com.globalspace.miljonsales.utils.Internet
import kotlinx.coroutines.async
import java.time.format.TextStyle
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
        return addDetailsRepository.FetchHospitalSummary()
    }

    internal fun fetchHospDetailsData(): LiveData<List<DashboardData>>? {
        return addDetailsRepository.FetchAllHospital()
    }

    fun setSpannableString(strtitle : String,strtext: String): SpannableString {
        val spannable = SpannableString(strtitle + strtext)
        spannable.setSpan(
            ForegroundColorSpan(context.getColor(R.color.black)),
            0, // start
            strtitle.length, // end
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            RelativeSizeSpan(1.1f),
            0, // start
            strtitle.length, // end
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }
}
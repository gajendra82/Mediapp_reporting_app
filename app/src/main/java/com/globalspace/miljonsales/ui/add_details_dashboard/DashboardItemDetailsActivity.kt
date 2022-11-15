package com.globalspace.miljonsales.ui.add_details_dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.globalspace.miljonsales.MyApplication
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.LayoutDashboardItemActivityBinding
import com.globalspace.miljonsales.local_db.entity.FetchHospitalStockistDetails
import com.globalspace.miljonsales.utils.WindowBar
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import javax.inject.Inject


class DashboardItemDetailsActivity: AppCompatActivity() {

    private lateinit var binding: LayoutDashboardItemActivityBinding
    var facilityvalue = ""
    var specialityvalue = ""
    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.layout_dashboard_item_activity)
        binding.lifecycleOwner = this
        WindowBar.setStatusColor(window, this@DashboardItemDetailsActivity)
        (application as MyApplication).applicationComponent.inject(this)
        val map = (application as MyApplication).applicationComponent.getMap()
        val intent = this.intent
        val bundle = intent.extras

        val dashboardDatadetail = bundle!!.getSerializable("rv_selected_data") as DashboardData?
        binding.dashboardData = dashboardDatadetail
        dashboardDatadetail!!.hospitalfacility!!.forEach {
             facilityvalue = facilityvalue + it.facility_name +","
            binding.tvfacilityvalue.text = facilityvalue
        }
        addRecyclerview()
        dashboardDatadetail!!.fetchHospitalSpecialityDetails!!.forEach {
            specialityvalue = specialityvalue + it.speciality_name +","
            binding.tvspvalue.text = specialityvalue
        }
        val adapter = DashboardConsumptionAdapter(dashboardDatadetail!!.fetchHospitalConsumptionDetails!!)
        binding!!.rvconsumption.adapter = adapter

        val adapterstokist = DashboardStockistAdapter(dashboardDatadetail!!.fetchHospitalStockistDetails!!)
        binding!!.rvstockist.adapter = adapterstokist

        binding.toolbarDashboard.setNavigationOnClickListener {
            onBackPressed()
        }
        val boardimg = dashboardDatadetail.hospitalDetails!!.board_image
        if (boardimg !== null) {
            Glide.with(this)
                .load(boardimg)
                .into(binding.imgboard)

        } else {
            binding.imgboard.setImageResource(R.drawable.ic_launcher_background)
        }

        val physicianimg = dashboardDatadetail.hospitalDetails!!.physician_list
        if (physicianimg !== null) {
            Glide.with(this)
                .load(physicianimg)
                .into(binding.imgphysician)

        } else {
            binding.imgphysician.setImageResource(R.drawable.ic_launcher_background)
        }
    }

    private fun addRecyclerview() {
        val layoutManager = LinearLayoutManager(applicationContext)
        binding!!.rvconsumption.layoutManager = layoutManager

        val layoutManagerstockist = LinearLayoutManager(applicationContext)
        binding!!.rvstockist.layoutManager = layoutManagerstockist
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
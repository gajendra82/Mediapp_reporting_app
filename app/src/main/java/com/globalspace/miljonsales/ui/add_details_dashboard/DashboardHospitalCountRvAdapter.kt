package com.globalspace.miljonsales.ui.add_details_dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.LayoutHospitalCountBinding
import com.globalspace.miljonsales.local_db.entity.FetchHospitalSummmary

class DashboardHospitalCountRvAdapter(
    private val lstdata: List<FetchHospitalSummmary>
) :
    RecyclerView.Adapter<DashboardHospitalCountRvAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutHospitalCountBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutHospitalCountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.hospitalData = lstdata[position]
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return lstdata!!.size
    }
}
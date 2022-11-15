package com.globalspace.miljonsales.ui.add_details.facility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.LayoutFacilityAdapterBinding
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchFacility

class FacilityRvAdapterr(
    private val lstdata: List<FetchFacility>,
    private val  addDetailsViewModel: AddDetailsViewModel
) :
    RecyclerView.Adapter<FacilityRvAdapterr.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutFacilityAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutFacilityAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.facilityData = lstdata[position]
            binding.facilityDatamodel = addDetailsViewModel
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return lstdata!!.size
    }
}
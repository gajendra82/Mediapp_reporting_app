package com.globalspace.miljonsales.ui.add_details.consumptions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.LayoutConsumptionAdapterBinding
import com.globalspace.miljonsales.databinding.LayoutFacilityAdapterBinding
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.facility.FacilityRvAdapterr
import com.globalspace.miljonsales.ui.add_details_dashboard.Add_Consumption_items
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchFacility

class ConsumptionAdapter(
    private val lstdata: List<Add_Consumption_items>,
    private val  addDetailsViewModel: AddDetailsViewModel
) :
    RecyclerView.Adapter<ConsumptionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutConsumptionAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutConsumptionAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.consumptionData = lstdata[position]
            binding.rvconsumptionData = addDetailsViewModel
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return lstdata!!.size
    }

}
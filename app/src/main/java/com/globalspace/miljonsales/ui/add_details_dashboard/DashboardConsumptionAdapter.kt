package com.globalspace.miljonsales.ui.add_details_dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.LayoutConsumptionAdapterBinding
import com.globalspace.miljonsales.databinding.LayoutDashboardConsuAdapterBinding
import com.globalspace.miljonsales.local_db.entity.FetchHospitalConsumptionDetails
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.consumptions.ConsumptionAdapter

class DashboardConsumptionAdapter(
    private val lstdata: List<FetchHospitalConsumptionDetails>
) :
    RecyclerView.Adapter<DashboardConsumptionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutDashboardConsuAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutDashboardConsuAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.tvbrandvalue.text = lstdata[position].consumption_brand
            binding.tvstrengthvalue.text = lstdata[position].consumption_strength
            binding.tvqtyvalue.text = lstdata[position].consumption_quantity
            binding.tvmrpvalue.text = lstdata[position].consumption_mrp
            binding.tvratevalue.text = lstdata[position].consumption_rate        }
    }

    override fun getItemCount(): Int {
        return lstdata!!.size
    }

}
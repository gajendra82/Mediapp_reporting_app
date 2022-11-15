package com.globalspace.miljonsales.ui.add_details_dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.LayoutDashboardConsuAdapterBinding
import com.globalspace.miljonsales.databinding.LayoutDashboardStockistAdapterBinding
import com.globalspace.miljonsales.local_db.entity.FetchHospitalConsumptionDetails
import com.globalspace.miljonsales.local_db.entity.FetchHospitalStockistDetails

class DashboardStockistAdapter(
    private val lstdata: List<FetchHospitalStockistDetails>
) :
    RecyclerView.Adapter<DashboardStockistAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutDashboardStockistAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutDashboardStockistAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.tvbrandvalue.text = lstdata[position].stockist_name
            binding.tvstrengthvalue.text =
                lstdata[position].stockist_address + " " + lstdata[position].stockist_state + " " + lstdata[position].stockist_city
            binding.tvcpvalue.text = lstdata[position].stockist_personalname
            binding.tvcnvalue.text = lstdata[position].stockist_number
        }
    }

    override fun getItemCount(): Int {
        return lstdata!!.size
    }
}
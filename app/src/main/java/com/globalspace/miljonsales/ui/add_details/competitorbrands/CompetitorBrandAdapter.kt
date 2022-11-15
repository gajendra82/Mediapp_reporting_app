package com.globalspace.miljonsales.ui.add_details.competitorbrands

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.LayoutCompetitorBrandBinding
import com.globalspace.miljonsales.databinding.LayoutConsumptionAdapterBinding
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.consumptions.ConsumptionAdapter
import com.globalspace.miljonsales.ui.add_details_dashboard.Add_CompetitorBrand
import com.globalspace.miljonsales.ui.add_details_dashboard.Add_Consumption_items

class CompetitorBrandAdapter(
    private val lstdata: List<Add_CompetitorBrand>,
    private val  addDetailsViewModel: AddDetailsViewModel
) :
    RecyclerView.Adapter<CompetitorBrandAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutCompetitorBrandBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutCompetitorBrandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.cmpbrandData = lstdata[position]
            binding.rvcmpbrandData = addDetailsViewModel
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return lstdata!!.size
    }
}
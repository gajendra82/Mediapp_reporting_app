package com.globalspace.miljonsales.ui.add_details.stockist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.LayoutConsumptionAdapterBinding
import com.globalspace.miljonsales.databinding.LayoutStockistAdapterBinding
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.consumptions.ConsumptionAdapter
import com.globalspace.miljonsales.ui.add_details_dashboard.Add_Consumption_items
import com.globalspace.miljonsales.ui.add_details_dashboard.Add_Stockist_items

class StockistAdapter(
    private val lstdata: List<Add_Stockist_items>,
    private val  addDetailsViewModel: AddDetailsViewModel
) :
    RecyclerView.Adapter<StockistAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutStockistAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutStockistAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.stockistData = lstdata[position]
            binding.rvstockistData = addDetailsViewModel
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return lstdata!!.size
    }

}
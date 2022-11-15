package com.globalspace.miljonsales.ui.add_details.purchaseauthority

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.LayoutConsumptionAdapterBinding
import com.globalspace.miljonsales.databinding.LayoutPurchaseAuthorityAdapterBinding
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.consumptions.ConsumptionAdapter
import com.globalspace.miljonsales.ui.add_details_dashboard.Add_Consumption_items
import com.globalspace.miljonsales.ui.add_details_dashboard.Add_Purchase_Authority_items

class PurchaseAuthorityAdapter(
    private val lstdata: List<Add_Purchase_Authority_items>,
    private val  addDetailsViewModel: AddDetailsViewModel
) :
    RecyclerView.Adapter<PurchaseAuthorityAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutPurchaseAuthorityAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutPurchaseAuthorityAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.paData = lstdata[position]
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return lstdata!!.size
    }

}
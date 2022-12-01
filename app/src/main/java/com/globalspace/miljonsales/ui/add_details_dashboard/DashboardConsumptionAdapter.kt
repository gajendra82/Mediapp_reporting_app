package com.globalspace.miljonsales.ui.add_details_dashboard

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.LayoutConsumptionAdapterBinding
import com.globalspace.miljonsales.databinding.LayoutDashboardConsuAdapterBinding
import com.globalspace.miljonsales.local_db.entity.FetchHospitalConsumptionDetails
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.consumptions.ConsumptionAdapter

class DashboardConsumptionAdapter(
    private val lstdata: List<FetchHospitalConsumptionDetails>,
    val addDetailsDashboardViewModel: AddDetailsDashboardViewModel
) :
    RecyclerView.Adapter<DashboardConsumptionAdapter.ViewHolder>() {

    lateinit var binding: LayoutDashboardConsuAdapterBinding

    inner class ViewHolder(val binding: LayoutDashboardConsuAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
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
            //binding.tvbrandvalue.text = addDetailsDashboardViewModel.setSpannableString("Brand : ",lstdata[position].consumption_brand!!)
           // binding.tvstrengthvalue.text = addDetailsDashboardViewModel.setSpannableString("Strength : ",lstdata[position].consumption_strength!!)
            binding.tvqtyvalue.text = addDetailsDashboardViewModel.setSpannableString("QTY : ",lstdata[position].consumption_quantity!!)
            binding.tvmrpvalue.text = addDetailsDashboardViewModel.setSpannableString("MRP : ",lstdata[position].consumption_mrp!!)
            binding.tvratevalue.text = addDetailsDashboardViewModel.setSpannableString("RATE : ",lstdata[position].consumption_rate!!)
           // binding.tvqtyvalue.text = lstdata[position].consumption_quantity
            //binding.tvmrpvalue.text = lstdata[position].consumption_mrp
            //binding.tvratevalue.text = lstdata[position].consumption_rate
        }
    }

    override fun getItemCount(): Int {
        return lstdata!!.size
    }



    }
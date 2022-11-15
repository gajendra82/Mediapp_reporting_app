package com.globalspace.miljonsales.ui.add_details.strength

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.LayoutSpecialityAdapterBinding
import com.globalspace.miljonsales.databinding.LayoutStrengthAdapterBinding
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.speciality.SpecialityRvAdapter
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchMolecule

class StrengthRvAdapter(
    private val lstdata: List<FetchMolecule>,
    private val  addDetailsViewModel: AddDetailsViewModel
) :
    RecyclerView.Adapter<StrengthRvAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutStrengthAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutStrengthAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.strengthData = lstdata[position]
            binding.strengthDatamodel = addDetailsViewModel
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return lstdata!!.size
    }
}

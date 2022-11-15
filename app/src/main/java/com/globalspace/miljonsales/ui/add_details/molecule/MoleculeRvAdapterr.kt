package com.globalspace.miljonsales.ui.add_details.molecule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.LayoutFacilityAdapterBinding
import com.globalspace.miljonsales.databinding.LayoutMoleculeAdapterBinding
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.facility.FacilityRvAdapterr
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchFacility
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchMolecule

class MoleculeRvAdapterr (
    private val lstdata: List<FetchMolecule>,
    private val  addDetailsViewModel: AddDetailsViewModel
) :
    RecyclerView.Adapter<MoleculeRvAdapterr.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutMoleculeAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutMoleculeAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.moleculeData = lstdata[position]
            binding.moleculeDatamodel = addDetailsViewModel
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return lstdata!!.size
    }
}
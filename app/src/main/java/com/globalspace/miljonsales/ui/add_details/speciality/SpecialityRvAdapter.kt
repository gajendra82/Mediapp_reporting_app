package com.globalspace.miljonsales.ui.add_details.speciality

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.LayoutFacilityAdapterBinding
import com.globalspace.miljonsales.databinding.LayoutSpecialityAdapterBinding
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.facility.FacilityRvAdapterr
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchFacility
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchSpeciality

class SpecialityRvAdapter(
    private val lstdata: List<FetchSpeciality>,
    private val  addDetailsViewModel: AddDetailsViewModel
) :
    RecyclerView.Adapter<SpecialityRvAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutSpecialityAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutSpecialityAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.specialityData = lstdata[position]
            binding.specialityDatamodel = addDetailsViewModel
            binding.executePendingBindings()

            if(lstdata[position].Speciality!!.lowercase().equals("others")){
                addDetailsViewModel?.let {
                    if(it.strspecialityOtherdata.value == null || it.strspecialityOtherdata.value.equals("")){
                        binding!!.tvFacilityName.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                    }else{
                        binding.tvFacilityName.setText(it.strspecialityOtherdata.value.toString().uppercase())
                        binding!!.tvFacilityName.setCompoundDrawablesWithIntrinsicBounds(0,0,
                            R.drawable.ic_edit,0)
                    }

                }
            }else{
                binding!!.tvFacilityName.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            }
        }
    }

    override fun getItemCount(): Int {
        return lstdata!!.size
    }
}

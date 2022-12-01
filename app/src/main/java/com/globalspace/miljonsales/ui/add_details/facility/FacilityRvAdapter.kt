package com.globalspace.miljonsales.ui.add_details.facility

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.LayoutFacilityAdapterBinding
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.AlertDialogFragment
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchFacility

class FacilityRvAdapterr(
    private val context : Context,
    private val lstdata: List<FetchFacility>,
    private val  addDetailsViewModel: AddDetailsViewModel
) :
    RecyclerView.Adapter<FacilityRvAdapterr.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutFacilityAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutFacilityAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.facilityData = lstdata[position]
            binding.facilityDatamodel = addDetailsViewModel
            binding.executePendingBindings()

            if(lstdata[position].Facility!!.lowercase().equals("others")){
               addDetailsViewModel?.let {
                   if(it.strfacilityOtherdata.value == null || it.strfacilityOtherdata.value.equals("")){
                       binding!!.tvFacilityName.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                   }else{
                       binding.tvFacilityName.setText(it.strfacilityOtherdata.value.toString().uppercase())
                       binding!!.tvFacilityName.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_edit,0)
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
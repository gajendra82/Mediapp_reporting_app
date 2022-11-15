package com.globalspace.miljonsales.ui.add_details.speciality

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.DialogAdapterBinding
import com.globalspace.miljonsales.ui.add_details.facility.AddDetailsDialogFacilityAdapter
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchFacility
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchSpeciality

class AddDetailsDialogSpecialityAdapter(
    private val lstdata: List<FetchSpeciality>,
    private val listener: onItemSpecialityClickListener
) :
    RecyclerView.Adapter<AddDetailsDialogSpecialityAdapter.ViewHolder>(), Filterable {
    var filteredList: MutableList<FetchSpeciality>? = null

    init {
        filteredList = lstdata.toMutableList()
    }

    inner class ViewHolder(val binding: DialogAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DialogAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.chkItem.visibility = View.VISIBLE
            with(filteredList!![position]) {
                binding.tvStateName.text = this.Speciality
                if (this.isCheckFlag == null)
                    binding.chkItem.isChecked = false
                else
                    binding.chkItem.isChecked = this.isCheckFlag!!
                binding.chkItem.setOnCheckedChangeListener { compoundButton, b ->
                    if(b){
                        filteredList!![adapterPosition].isCheckFlag = true
                        listener.onItemSpecialityClickListener(adapterPosition, filteredList!![adapterPosition],true)
                    }else{
                        filteredList!![adapterPosition].isCheckFlag = false
                        listener.onItemSpecialityClickListener(adapterPosition, filteredList!![adapterPosition],false)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList!!.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                filteredList!!.clear()
                var results = FilterResults()
                if (p0!!.isEmpty()) {
                    filteredList!!.addAll(lstdata)
                } else {
                    var filteredString = p0.toString().lowercase().trim()
                    for (items in lstdata) {
                        if (items.Speciality!!.lowercase().contains(filteredString)) {
                            filteredList!!.add(items)
                        }
                    }
                }
                results.values = filteredList
                results.count = filteredList!!.size
                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredList = p1?.values as ArrayList<FetchSpeciality>
                notifyDataSetChanged()
            }

        }
    }

    interface onItemSpecialityClickListener {
        fun onItemSpecialityClickListener(position: Int, fetchSpeciality: FetchSpeciality, b: Boolean)
    }

}
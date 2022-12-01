package com.globalspace.miljonsales.ui.add_details.facility

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.DialogAdapterBinding
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.AlertDialogFragment
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchFacility

class AddDetailsDialogFacilityAdapter(
    private val addDetailsViewModel: AddDetailsViewModel,
    private val lstdata: List<FetchFacility>,
    private val listener: onItemFacilityClickListener,
) :
    RecyclerView.Adapter<AddDetailsDialogFacilityAdapter.ViewHolder>(), Filterable {
    var filteredList: MutableList<FetchFacility>? = null
    internal val alertdialog = AlertDialogFragment()
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
                binding.tvStateName.text = this.Facility
                if (this.isCheckFlag == null)
                    binding.chkItem.isChecked = false
                else
                    binding.chkItem.isChecked = this.isCheckFlag!!

                binding.chkItem.setOnCheckedChangeListener { compoundButton, b ->
                    if (b) {
                        filteredList!![adapterPosition].isCheckFlag = true
                        listener.onItemFacilityClickListener(
                            adapterPosition,
                            filteredList!![adapterPosition],
                            true
                        )
                        if((addDetailsViewModel.strfacilityOtherdata.value == null || addDetailsViewModel.strfacilityOtherdata.value.equals("")) && filteredList!![adapterPosition].Facility!!.lowercase().equals("others")){
                            addDetailsViewModel.facilityotherposition = filteredList!![adapterPosition]
                            addDetailsViewModel.CallAlert("Facility","Enter Other Facility")
                        }
                    } else {
                        filteredList!![adapterPosition].isCheckFlag = false
                        listener.onItemFacilityClickListener(
                            adapterPosition,
                            filteredList!![adapterPosition],
                            false
                        )
                        if(filteredList!![adapterPosition].Facility!!.lowercase().equals("others")){
                            addDetailsViewModel.strfacilityOtherdata.value = ""
                        }
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
                        if (items.Facility!!.lowercase().contains(filteredString)) {
                            filteredList!!.add(items)
                        }
                    }
                }
                results.values = filteredList
                results.count = filteredList!!.size
                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredList = p1?.values as ArrayList<FetchFacility>
                notifyDataSetChanged()
            }

        }
    }

    interface onItemFacilityClickListener {
        fun onItemFacilityClickListener(position: Int, fetchState: FetchFacility, b: Boolean)
    }

}
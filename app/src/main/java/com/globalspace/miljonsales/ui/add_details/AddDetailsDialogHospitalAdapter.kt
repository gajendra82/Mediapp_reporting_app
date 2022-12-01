package com.globalspace.miljonsales.ui.add_details

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.DialogAdapterBinding

class AddDetailsDialogHospitalAdapter(
    private val lstdata: List<FetchHospital>,
    private val listener: onItemHospitalClickListener,
    private val strflag: String
) :
    RecyclerView.Adapter<AddDetailsDialogHospitalAdapter.ViewHolder>(), Filterable {
    var filteredList: MutableList<FetchHospital>? = null

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
            with(filteredList!![position]) {
                binding.tvStateName.text = this.HospitalType
            }
            holder.itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    listener.onItemHospitalClick(adapterPosition, filteredList!![adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList!!.size
    }

    interface onItemHospitalClickListener {
        fun onItemHospitalClick(position: Int, fetchState: FetchHospital)
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
                        if (items.HospitalType!!.lowercase().contains(filteredString)) {
                            filteredList!!.add(items)
                        }
                    }
                }
                results.values = filteredList
                results.count = filteredList!!.size
                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredList = p1?.values as ArrayList<FetchHospital>
                notifyDataSetChanged()
            }

        }
    }

}
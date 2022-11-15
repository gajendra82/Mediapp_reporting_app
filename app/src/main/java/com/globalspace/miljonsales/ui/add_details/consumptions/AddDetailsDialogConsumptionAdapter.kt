package com.globalspace.miljonsales.ui.add_details.consumptions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.DialogAdapterBinding
import com.globalspace.miljonsales.local_db.entity.FetchGeography
import com.globalspace.miljonsales.ui.add_details.AddDetailsDialogAdapter
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchMolecule

class AddDetailsDialogConsumptionAdapter(
    private val lstdata: List<FetchMolecule>,
    private val listener: onItemConsumptionClick,
    private val strflag : String
) :
    RecyclerView.Adapter<AddDetailsDialogConsumptionAdapter.ViewHolder>(), Filterable {
    var filteredList: MutableList<FetchMolecule>? = null

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
                if (strflag.equals("ConsumptionStrength"))
                    binding.tvStateName.text = this.STRENGTH
                else
                    binding.tvStateName.text = this.BRAND
            }
            holder.itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    listener.onItemConsumptionClick(adapterPosition, filteredList!![adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList!!.size
    }

    interface onItemConsumptionClick {
        fun onItemConsumptionClick(position: Int, fetchGeography: FetchMolecule)
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
                        if (strflag.equals("ConsumptionStrength")) {
                            if (items.STRENGTH!!.lowercase().contains(filteredString)) {
                                filteredList!!.add(items)
                            }
                        } else {
                            if (items.BRAND!!.lowercase().contains(filteredString)) {
                                filteredList!!.add(items)
                            }
                        }

                    }
                }
                results.values = filteredList
                results.count = filteredList!!.size
                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredList = p1?.values as ArrayList<FetchMolecule>
                notifyDataSetChanged()
            }

        }
    }
}
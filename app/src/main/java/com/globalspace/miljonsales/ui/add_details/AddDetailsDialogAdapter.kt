package com.globalspace.miljonsales.ui.add_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.DialogAdapterBinding
import com.globalspace.miljonsales.local_db.entity.FetchGeography

class AddDetailsDialogAdapter(
    private val lstdata: List<FetchGeography>,
    private val listener: onItemClickListener,
    private val strflag: String
) :
    RecyclerView.Adapter<AddDetailsDialogAdapter.ViewHolder>(), Filterable {
    var filteredList: MutableList<FetchGeography>? = null

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
                if (strflag.equals("State") || strflag.equals("StockistState"))
                    binding.tvStateName.text = this.STATE_NAME
                else
                    binding.tvStateName.text = this.CITY_NAME
            }
            holder.itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    listener.onItemClick(adapterPosition, filteredList!![adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList!!.size
    }

    interface onItemClickListener {
        fun onItemClick(position: Int, fetchGeography: FetchGeography)
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
                        if (strflag.equals("State") || strflag.equals("StockistState")) {
                            if (items.STATE_NAME!!.lowercase().contains(filteredString)) {
                                filteredList!!.add(items)
                            }
                        } else {
                            if (items.CITY_NAME!!.lowercase().contains(filteredString)) {
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
                filteredList = p1?.values as ArrayList<FetchGeography>
                notifyDataSetChanged()
            }

        }
    }
}
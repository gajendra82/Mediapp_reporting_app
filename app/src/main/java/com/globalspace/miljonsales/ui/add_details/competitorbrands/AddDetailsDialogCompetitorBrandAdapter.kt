package com.globalspace.miljonsales.ui.add_details.competitorbrands

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.compose.ui.text.toLowerCase
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.databinding.DialogAdapterBinding
import com.globalspace.miljonsales.ui.add_details.consumptions.AddDetailsDialogConsumptionAdapter
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchMolecule

class AddDetailsDialogCompetitorBrandAdapter(
    private val lstdata: List<FetchMolecule>,
    private val listener: onItemCompetitorBrandClick,
    private val strflag: String
) :
    RecyclerView.Adapter<AddDetailsDialogCompetitorBrandAdapter.ViewHolder>(), Filterable {
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
                if (strflag.equals("CompetitorStrength"))
                    binding.tvStateName.text = this.STRENGTH
                else {
                    if (!this.BRAND!!.lowercase().equals("gufic")) {
                        binding.tvStateName.text = this.BRAND
                    }
                }

            }
            holder.itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    listener.onItemCompetitorBrandClick(
                        adapterPosition,
                        filteredList!![adapterPosition]
                    )
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList!!.size
    }

    interface onItemCompetitorBrandClick {
        fun onItemCompetitorBrandClick(position: Int, fetchGeography: FetchMolecule)
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
                        if (strflag.equals("CompetitorStrength")) {
                            if (items.STRENGTH!!.lowercase().contains(filteredString)) {
                                filteredList!!.add(items)
                            }
                        } else {
                            if (items.BRAND!!.lowercase().contains(filteredString)) {
                                if(!items.equals("gufic")){
                                    filteredList!!.add(items)
                                }
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
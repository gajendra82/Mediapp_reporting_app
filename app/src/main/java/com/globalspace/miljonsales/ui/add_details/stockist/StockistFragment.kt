package com.globalspace.miljonsales.ui.add_details.stockist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.globalspace.miljonsales.MyApplication
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.LayoutConsumptionFragmentBinding
import com.globalspace.miljonsales.ui.add_details.AddDetaillsDialog
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.consumptions.ConsumptionActivity
import com.globalspace.miljonsales.ui.add_details.consumptions.ConsumptionAdapter
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import kotlinx.android.synthetic.main.add_details_activity.*
import javax.inject.Inject

class StockistFragment: Fragment(R.layout.layout_consumption_fragment) {


    private var _binding: LayoutConsumptionFragmentBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew

    internal val dialog = AddDetaillsDialog()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            _binding = LayoutConsumptionFragmentBinding.bind(view)
            (requireActivity().application as MyApplication).applicationComponent.inject(this)
            val map = (requireActivity().application as MyApplication).applicationComponent.getMap()
            addRecyclerview()
            addDetailsViewModel =
                ViewModelProvider(requireActivity(), mainviewmodelFactory).get(
                    AddDetailsViewModel::class.java
                )
            addDetailsViewModel.CurrentFlag = "Stockist"
            addDetailsViewModel.lststockistdata.observe(viewLifecycleOwner){
                val adapter = StockistAdapter(it!!,addDetailsViewModel)
                binding!!.rvconsumption.adapter = adapter
            }
            binding!!.tvcreate.setOnClickListener {
                try {
                    addDetailsViewModel.clearStockist()
                    val intent = Intent(requireActivity(), StockistActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    val msg  = e.message
                }
            }
        } catch (e: Exception) {
            val msg = e.message
        }
    }

    private fun addRecyclerview() {
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        binding!!.rvconsumption.layoutManager = layoutManager

    }
    companion object{
        lateinit var addDetailsViewModel: AddDetailsViewModel
    }
}
package com.globalspace.miljonsales.ui.add_details.consumptions

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.globalspace.miljonsales.MyApplication
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.FragmentFacilityBinding
import com.globalspace.miljonsales.databinding.LayoutConsumptionFragmentBinding
import com.globalspace.miljonsales.ui.add_details.AddDetaillsDialog
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.facility.FacilityRvAdapterr
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import javax.inject.Inject

class ConsumptionFragment : Fragment(R.layout.layout_consumption_fragment) {


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
            addDetailsViewModel.CurrentFlag = "Consumption"
            addDetailsViewModel.lstconsumptiondata.observe(viewLifecycleOwner){
                val adapter = ConsumptionAdapter(it,addDetailsViewModel)
                binding!!.rvconsumption.adapter = adapter
            }
            binding!!.tvcreate.setOnClickListener {
                try {
                    addDetailsViewModel.clearConsumption()
                    val intent = Intent(requireActivity(),ConsumptionActivity::class.java)
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
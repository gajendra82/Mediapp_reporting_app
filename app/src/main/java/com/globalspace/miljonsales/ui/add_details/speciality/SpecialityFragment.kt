package com.globalspace.miljonsales.ui.add_details.speciality

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.globalspace.miljonsales.MyApplication
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.FragmentFacilityBinding
import com.globalspace.miljonsales.ui.add_details.AddDetaillsDialog
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.facility.FacilityRvAdapterr
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import javax.inject.Inject

class SpecialityFragment : Fragment(R.layout.fragment_facility){


    private var _binding: FragmentFacilityBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew
    private lateinit var addDetailsViewModel: AddDetailsViewModel
    internal val dialog = AddDetaillsDialog()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            _binding = FragmentFacilityBinding.bind(view)
            (requireActivity().application as MyApplication).applicationComponent.inject(this)
            val map = (requireActivity().application as MyApplication).applicationComponent.getMap()
            addRecyclerview()
            addDetailsViewModel =
                ViewModelProvider(requireActivity(), mainviewmodelFactory).get(
                    AddDetailsViewModel::class.java
                )
            addDetailsViewModel.CurrentFlag = "Speciality"
            binding?.let {
                it.edittextFacilityItem.setOnClickListener {
                    dialog.newInstance(addDetailsViewModel, "Speciality")
                    dialog.show((context as AppCompatActivity).supportFragmentManager, "Dialog")
                }
            }

            addDetailsViewModel.lstspecialitydata.observe(viewLifecycleOwner){
                val adapter = SpecialityRvAdapter(it,addDetailsViewModel)
                binding!!.rvfacility.adapter = adapter
            }

        } catch (e: Exception) {
            val msg = e.message
        }
    }

    private fun addRecyclerview() {
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        binding!!.rvfacility.layoutManager = layoutManager

    }
}
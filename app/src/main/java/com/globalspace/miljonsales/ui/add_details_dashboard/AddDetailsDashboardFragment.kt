package com.globalspace.miljonsales.ui.add_details_dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.globalspace.miljonsales.MyApplication
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.AddDetailsFragmentBinding
import com.globalspace.miljonsales.local_db.entity.FetchHospitalSummmary
import com.globalspace.miljonsales.ui.add_details.AddDetailsActivity
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class AddDetailsDashboardFragment : Fragment(R.layout.add_details_fragment) {

    private lateinit var dashboardViewModel: AddDetailsDashboardViewModel

    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew
    lateinit var floatingActionButton: FloatingActionButton
    private var _binding: AddDetailsFragmentBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            _binding = AddDetailsFragmentBinding.bind(view)
            floatingActionButton = requireActivity().findViewById<View>(R.id.fab) as FloatingActionButton
            (requireActivity().application as MyApplication).applicationComponent.inject(this)
            val map = (requireActivity().application as MyApplication).applicationComponent.getMap()
            dashboardViewModel =
                ViewModelProvider(requireActivity(), mainviewmodelFactory).get(
                    AddDetailsDashboardViewModel::class.java
                )
            dashboardViewModel.StartGeoSync(binding!!.progressBarDashboard)
            addRecyclerview()
            addrvHosp()
            dashboardViewModel.fetchHospSummaryData()
                ?.observe(viewLifecycleOwner, object : Observer<List<FetchHospitalSummmary>> {
                    override fun onChanged(t: List<FetchHospitalSummmary>?) {
                        t?.let {
                            Log.i("tag", it.toString())
                            binding!!.rvhospcount.visibility = View.VISIBLE
                            val adapter =
                                DashboardHospitalCountRvAdapter(
                                    it
                                )
                            binding!!.rvhospcount.adapter = adapter
                        }

                    }
                })

            dashboardViewModel.fetchHospDetailsData()
                ?.observe(viewLifecycleOwner, object : Observer<List<DashboardData>> {
                    override fun onChanged(t: List<DashboardData>?) {
                        t?.let {
                            try {
                                Log.i("tag", it.toString())
                                binding!!.rvhosplist.visibility = View.VISIBLE
                                val adapter =
                                    DashboardRvAdapter(
                                        it,requireActivity()
                                    )
                                binding!!.rvhosplist.adapter = adapter
                            } catch (e: Exception) {
                                val m = e.message
                            }
                        }

                    }
                })
            floatingActionButton.setOnClickListener {
                callDetailsActivity()
            }
        } catch (e: Exception) {
            val msg = e.message
        }
    }

    internal fun callDetailsActivity() {
        activity?.let{
            val intent = Intent(it,AddDetailsActivity::class.java)
            it.startActivity(intent)

        }
    }

    private fun addRecyclerview() {
        val  layoutManager = GridLayoutManager(context,2)
        binding!!.rvhospcount.layoutManager = layoutManager
    }
    private fun addrvHosp() {
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        binding!!.rvhosplist.layoutManager = layoutManager
    }


}
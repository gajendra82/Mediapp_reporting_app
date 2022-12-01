package com.globalspace.miljonsales.ui.add_details_dashboard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.globalspace.miljonsales.MyApplication
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.activity.Dashboard
import com.globalspace.miljonsales.databinding.AddDetailsFragmentBinding
import com.globalspace.miljonsales.interface_.di.AppPreference
import com.globalspace.miljonsales.local_db.entity.FetchHospitalSummmary
import com.globalspace.miljonsales.ui.add_details.AddDetailsActivity
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class AddDetailsDashboardFragment : Fragment(R.layout.add_details_fragment) {

    private lateinit var dashboardViewModel: AddDetailsDashboardViewModel
    private lateinit var layoutManager : GridLayoutManager
    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew
    lateinit var floatingActionButton: FloatingActionButton
    private var _binding: AddDetailsFragmentBinding? = null
    private val binding get() = _binding
    private var sPref: SharedPreferences? = null
    @Inject
    lateinit var appPreference: AppPreference

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
            Dashboard.flagdashboard = "new"
            sPref = requireContext().getSharedPreferences(
                requireContext().getResources().getString(R.string.app_name), Context.MODE_PRIVATE
            )
            val userID = sPref!!.getString(requireContext().getResources().getString(R.string.employee_id), "")
                .toString()
            Log.i("tag"," user ID ${appPreference.getUserId().toString()}")
            if(appPreference.getUserId().equals(""))
            appPreference.setUserId(userID)
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
        try {
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

            var width = displayMetrics.widthPixels
            var height = displayMetrics.heightPixels
            Log.i("tag", "width: ${width}")
            if (width == 2000) {
                layoutManager = GridLayoutManager(context,3)
            } else {
                layoutManager = GridLayoutManager(context,2)
            }

        } catch (e: Exception) {
            Log.e("TAG", "error : ${e.message}")
        }
       // val  layoutManager = GridLayoutManager(context,2)
        binding!!.rvhospcount.layoutManager = layoutManager
    }

    private fun initialiseAdapter() {
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.setJustifyContent(JustifyContent.FLEX_START)
        layoutManager.setFlexWrap(FlexWrap.WRAP)
        binding!!.rvhospcount.layoutManager = layoutManager
    }

    private fun addrvHosp() {
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        binding!!.rvhosplist.layoutManager = layoutManager
    }


}
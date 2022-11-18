package com.globalspace.miljonsales.ui.add_details

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.DialogSearchableSpinnerBinding
import com.globalspace.miljonsales.local_db.entity.FetchGeography
import com.globalspace.miljonsales.local_db.entity.FetchHospital
import com.globalspace.miljonsales.ui.add_details.competitorbrands.AddDetailsDialogCompetitorBrandAdapter
import com.globalspace.miljonsales.ui.add_details.consumptions.AddDetailsDialogConsumptionAdapter
import com.globalspace.miljonsales.ui.add_details.consumptions.AddDetailsReasonAdapter
import com.globalspace.miljonsales.ui.add_details.facility.AddDetailsDialogFacilityAdapter
import com.globalspace.miljonsales.ui.add_details.molecule.AddDetailsDialogMoleculeAdapter
import com.globalspace.miljonsales.ui.add_details.speciality.AddDetailsDialogSpecialityAdapter
import com.globalspace.miljonsales.ui.add_details.strength.AddDetailsDialogStrengthAdapter
import com.globalspace.miljonsales.ui.add_details_dashboard.*
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class AddDetaillsDialog : DialogFragment(), AddDetailsDialogAdapter.onItemClickListener,
    AddDetailsDialogHospitalAdapter.onItemHospitalClickListener,
    AddDetailsDialogFacilityAdapter.onItemFacilityClickListener,
    AddDetailsDialogSpecialityAdapter.onItemSpecialityClickListener,
    AddDetailsDialogMoleculeAdapter.onItemMoleculeClickListener,
    AddDetailsDialogStrengthAdapter.onItemStrengthClickListener,
    AddDetailsDialogConsumptionAdapter.onItemConsumptionClick,
    AddDetailsDialogCompetitorBrandAdapter.onItemCompetitorBrandClick {

    private var _binding: DialogSearchableSpinnerBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew
    private lateinit var addDetailsViewModel: AddDetailsViewModel
    lateinit var rvAdapter: AddDetailsDialogAdapter
    lateinit var rvconsumpAdapter: AddDetailsDialogConsumptionAdapter
    lateinit var rvcompbrandAdapter: AddDetailsDialogCompetitorBrandAdapter
    lateinit var adapter: AddDetailsDialogHospitalAdapter
    lateinit var adapterfacility: AddDetailsDialogFacilityAdapter
    lateinit var adapterspeciality: AddDetailsDialogSpecialityAdapter
    lateinit var adaptermolecule: AddDetailsDialogMoleculeAdapter
    lateinit var adapterstrength: AddDetailsDialogStrengthAdapter

    var strflag: String = "State"
    var strcheckdata: Int = 0

    fun newInstance(addetailsViewModel: AddDetailsViewModel, flag: String) {
        this.addDetailsViewModel = addetailsViewModel
        this.strflag = flag
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DialogSearchableSpinnerBinding.inflate(LayoutInflater.from(context), container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setTextFilter()
    }


    private fun setupView() {
        try {

            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
            binding!!.listView.setLayoutManager(layoutManager)
            strcheckdata = 0
            binding!!.editText.setText("")
            if (strflag.equals("Hospital")) {
                binding!!.tvtitle.setText(resources.getString(R.string.hint_hospital))
                addDetailsViewModel.fetchHospData()
                    ?.observe(viewLifecycleOwner, object : Observer<List<FetchHospital>> {
                        override fun onChanged(t: List<FetchHospital>?) {
                            t?.let {
                                Log.i("tag", it.toString())
                                strcheckdata = it.size
                                adapter =
                                    AddDetailsDialogHospitalAdapter(
                                        it,
                                        this@AddDetaillsDialog,
                                        strflag
                                    )
                                binding!!.listView.adapter = adapter
                            }

                        }
                    })
            } else if (strflag.equals("Facility")) {
                binding!!.tvtitle.setText(resources.getString(R.string.hint_item))
                addDetailsViewModel.fetchFacilityData()
                    ?.observe(viewLifecycleOwner, object : Observer<List<FetchFacility>> {
                        override fun onChanged(t: List<FetchFacility>?) {
                            t?.let {
                                strcheckdata = it.size
                                var lstfacility = it as ArrayList<FetchFacility>
                                if (addDetailsViewModel.lstfacility != null && addDetailsViewModel.lstfacility.size > 0) {
                                    addDetailsViewModel.lstfacility.forEachIndexed { index, fetchFacility1 ->
                                        for (i in 0..lstfacility.size - 1) {
                                            if (lstfacility[i].Facility.equals(fetchFacility1.Facility)) {
                                                lstfacility.remove(lstfacility[i])
                                                lstfacility.add(i, fetchFacility1)
                                            }
                                        }
                                    }
                                }
                                adapterfacility =
                                    AddDetailsDialogFacilityAdapter(
                                        lstfacility,
                                        this@AddDetaillsDialog,
                                        strflag
                                    )
                                binding!!.listView.adapter = adapterfacility
                            }

                        }
                    })
            } else if (strflag.equals("Speciality")) {
                binding!!.tvtitle.setText(resources.getString(R.string.hint_item))
                addDetailsViewModel.fetchSpecialityData()
                    ?.observe(viewLifecycleOwner, object : Observer<List<FetchSpeciality>> {
                        override fun onChanged(t: List<FetchSpeciality>?) {
                            t?.let {
                                strcheckdata = it.size
                                var lstspeciality = it as ArrayList<FetchSpeciality>
                                if (addDetailsViewModel.lstspeciality != null && addDetailsViewModel.lstspeciality.size > 0) {
                                    addDetailsViewModel.lstspeciality.forEachIndexed { index, fetchFacility1 ->
                                        for (i in 0..lstspeciality.size - 1) {
                                            if (lstspeciality[i].Speciality.equals(fetchFacility1.Speciality)) {
                                                lstspeciality.remove(lstspeciality[i])
                                                lstspeciality.add(i, fetchFacility1)
                                            }
                                        }
                                    }
                                }
                                adapterspeciality =
                                    AddDetailsDialogSpecialityAdapter(
                                        lstspeciality,
                                        this@AddDetaillsDialog
                                    )
                                binding!!.listView.adapter = adapterspeciality
                            }

                        }
                    })

            } else if (strflag.equals("Molecule")) {
                binding!!.tvtitle.setText(resources.getString(R.string.hint_item))
                addDetailsViewModel.fetchMoleculeData()
                    ?.observe(viewLifecycleOwner, object : Observer<List<FetchMolecule>> {
                        override fun onChanged(t: List<FetchMolecule>?) {
                            t?.let {
                                strcheckdata = it.size
                                var lstmolecule = it as ArrayList<FetchMolecule>
                                if (addDetailsViewModel.lstmolecule != null && addDetailsViewModel.lstmolecule.size > 0) {
                                    addDetailsViewModel.lstmolecule.forEachIndexed { index, fetchFacility1 ->
                                        for (i in 0..lstmolecule.size - 1) {
                                            if (lstmolecule[i].MOLECULE.equals(fetchFacility1.MOLECULE)) {
                                                lstmolecule.remove(lstmolecule[i])
                                                lstmolecule.add(i, fetchFacility1)
                                            }
                                        }
                                    }
                                }
                                adaptermolecule =
                                    AddDetailsDialogMoleculeAdapter(
                                        lstmolecule,
                                        this@AddDetaillsDialog
                                    )
                                binding!!.listView.adapter = adaptermolecule
                            }

                        }
                    })

            } else if (strflag.equals("Strength")) {
                binding!!.tvtitle.setText(resources.getString(R.string.hint_item))
                addDetailsViewModel.fetchStrengthData(addDetailsViewModel.lst_strmolecule!!)
                    ?.observe(viewLifecycleOwner, object : Observer<List<FetchMolecule>> {
                        override fun onChanged(t: List<FetchMolecule>?) {
                            t?.let {
                                strcheckdata = it.size
                                var lststrength = it as ArrayList<FetchMolecule>
                                if (addDetailsViewModel.lststrength != null && addDetailsViewModel.lststrength.size > 0) {
                                    addDetailsViewModel.lststrength.forEachIndexed { index, fetchFacility1 ->
                                        for (i in 0..lststrength.size - 1) {
                                            if (lststrength[i].STRENGTH.equals(fetchFacility1.STRENGTH)) {
                                                lststrength.remove(lststrength[i])
                                                lststrength.add(i, fetchFacility1)
                                            }
                                        }
                                    }
                                }
                                adapterstrength =
                                    AddDetailsDialogStrengthAdapter(
                                        lststrength,
                                        this@AddDetaillsDialog
                                    )
                                binding!!.listView.adapter = adapterstrength
                            }

                        }
                    })


            } else if (strflag.equals("State") || strflag.equals("StockistState")) {
                binding!!.tvtitle.setText(resources.getString(R.string.hint_state))
                addDetailsViewModel.fetchStateData()
                    ?.observe(viewLifecycleOwner, object : Observer<List<FetchGeography>> {
                        override fun onChanged(t: List<FetchGeography>?) {
                            t?.let {
                                strcheckdata = it.size
                                rvAdapter =
                                    AddDetailsDialogAdapter(it, this@AddDetaillsDialog, strflag)
                                binding!!.listView.adapter = rvAdapter
                            }

                        }
                    })
            } else if (strflag.equals("ConsumptionStrength")) {
                binding!!.tvtitle.setText(resources.getString(R.string.hint_cons_strength))
                strcheckdata = 1
                rvconsumpAdapter =
                    AddDetailsDialogConsumptionAdapter(
                        addDetailsViewModel.lststrength,
                        this@AddDetaillsDialog,
                        strflag
                    )
                binding!!.listView.adapter = rvconsumpAdapter
            } else if (strflag.equals("ConsumptionBrand")) {
                binding!!.tvtitle.setText(resources.getString(R.string.hint_cons_brand))
                addDetailsViewModel.fetchAllBrandData(addDetailsViewModel.lstconsum_strength.value!!.STRENGTH!!)
                    ?.observe(viewLifecycleOwner, object : Observer<List<FetchMolecule>> {
                        override fun onChanged(t: List<FetchMolecule>?) {
                            t?.let {
                                strcheckdata = it.size
                                Log.i("tag", it.toString())
                                rvconsumpAdapter =
                                    AddDetailsDialogConsumptionAdapter(
                                        it,
                                        this@AddDetaillsDialog,
                                        strflag
                                    )
                                binding!!.listView.adapter = rvconsumpAdapter
                            }

                        }
                    })
            } else if (strflag.equals("CompetitorStrength")) {
                binding!!.tvtitle.setText(resources.getString(R.string.hint_cons_strength))
                strcheckdata = 1
                Log.i("strength", addDetailsViewModel.lststrength.toString())
                rvcompbrandAdapter =
                    AddDetailsDialogCompetitorBrandAdapter(
                        addDetailsViewModel.lststrength,
                        this@AddDetaillsDialog,
                        strflag
                    )
                binding!!.listView.adapter = rvcompbrandAdapter
            } else if (strflag.equals("CompetitorBrand")) {
                binding!!.tvtitle.setText(resources.getString(R.string.hint_comp_brand))
                addDetailsViewModel.fetchAllCompetitorBrandData(addDetailsViewModel.lstcompetitorstrength.value!!.STRENGTH!!)
                    ?.observe(viewLifecycleOwner, object : Observer<List<FetchMolecule>> {
                        override fun onChanged(t: List<FetchMolecule>?) {
                            t?.let {
                                strcheckdata = it.size
                                Log.i("tag", it.toString())
                                rvcompbrandAdapter =
                                    AddDetailsDialogCompetitorBrandAdapter(
                                        it,
                                        this@AddDetaillsDialog, strflag
                                    )
                                binding!!.listView.adapter = rvcompbrandAdapter
                            }

                        }
                    })
            } else if (strflag.equals("StockistCity")) {
                binding!!.tvtitle.setText(resources.getString(R.string.hint_city))
                addDetailsViewModel.fetchStateCityData(addDetailsViewModel.lststockdata.value!!.STATE_CODE!!)
                    ?.observe(viewLifecycleOwner, object : Observer<List<FetchGeography>> {
                        override fun onChanged(t: List<FetchGeography>?) {
                            t?.let {
                                strcheckdata = it.size
                                rvAdapter =
                                    AddDetailsDialogAdapter(it, this@AddDetaillsDialog, strflag)
                                binding!!.listView.adapter = rvAdapter
                            }

                        }
                    })
            } else if (strflag.equals("ConsumptionReasons")) {
                binding!!.tvtitle.setText(resources.getString(R.string.hint_reason))
                strcheckdata = 1
                val lstreasons = resources.getStringArray(R.array.Reasons)
                /*rvAdapter =
                    AddDetailsReasonAdapter(lstreasons)*/
                binding!!.listView.adapter = rvAdapter

            } else {
                binding!!.tvtitle.setText(resources.getString(R.string.hint_city))
                addDetailsViewModel.fetchStateCityData(addDetailsViewModel.lstdata.value!!.STATE_CODE!!)
                    ?.observe(viewLifecycleOwner, object : Observer<List<FetchGeography>> {
                        override fun onChanged(t: List<FetchGeography>?) {
                            t?.let {
                                strcheckdata = it.size
                                rvAdapter =
                                    AddDetailsDialogAdapter(it, this@AddDetaillsDialog, strflag)
                                binding!!.listView.adapter = rvAdapter
                            }

                        }
                    })
            }

        } catch (e: Exception) {
            val msg = e.message
        }
    }

    private fun setTextFilter() {
        binding!!.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (strcheckdata > 0) {
                    if (strflag.equals("Hospital"))
                        adapter.getFilter().filter(s)
                    else if (strflag.equals("Facility"))
                        adapterfacility.getFilter().filter(s)
                    else if (strflag.equals("Speciality"))
                        adapterspeciality.getFilter().filter(s)
                    else if (strflag.equals("Molecule"))
                        adaptermolecule.getFilter().filter(s)
                    else if (strflag.equals("Strength"))
                        adapterstrength.getFilter().filter(s)
                    else if (strflag.equals("ConsumptionStrength") || strflag.equals("ConsumptionBrand"))
                        rvconsumpAdapter.getFilter().filter(s)
                    else if (strflag.equals("CompetitorStrength") || strflag.equals("CompetitorBrand"))
                        rvcompbrandAdapter.getFilter().filter(s)
                    else
                        rvAdapter.getFilter().filter(s)
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        binding!!.editText.setText("")
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int, fetchGeography: FetchGeography) {
        if (strflag.equals("State")) {
            addDetailsViewModel.lstdata.value = fetchGeography
            addDetailsViewModel.lstcitydata.value = null
        }
        else if (strflag.equals("StockistState")) {
            addDetailsViewModel.lststockdata.value = fetchGeography
            addDetailsViewModel.lststockcitydata.value = null
        }
        else if (strflag.equals("StockistCity"))
            addDetailsViewModel.lststockcitydata.value = fetchGeography
        else
            addDetailsViewModel.lstcitydata.value = fetchGeography
        binding!!.editText.setText("")
        dialog!!.dismiss()

    }

    override fun onItemHospitalClick(position: Int, fetchHospital: FetchHospital) {
        addDetailsViewModel.lsthospdata.value = fetchHospital
        binding!!.editText.setText("")
        dialog!!.dismiss()
    }

    override fun onItemFacilityClickListener(position: Int, fetchState: FetchFacility, b: Boolean) {
        if (b == true) {
            addDetailsViewModel.addFacility(fetchState)
        } else {
            addDetailsViewModel.removeFacility(fetchState)
        }
    }

    override fun onItemSpecialityClickListener(
        position: Int,
        fetchSpeciality: FetchSpeciality,
        b: Boolean
    ) {
        if (b == true) {
            addDetailsViewModel.addSpeciality(fetchSpeciality)
        } else {
            addDetailsViewModel.removeSpeciality(fetchSpeciality)
        }
    }

    override fun onItemMoleculeClickListener(
        position: Int,
        fetchMolecule: FetchMolecule,
        b: Boolean
    ) {
        if (b == true) {
            addDetailsViewModel.addMolecule(fetchMolecule)
        } else {
            addDetailsViewModel.removeMolecule(fetchMolecule)
        }
    }

    override fun onItemStrengthClickListener(
        position: Int,
        fetchStrength: FetchMolecule,
        b: Boolean
    ) {
        if (b == true) {
            addDetailsViewModel.addStrength(fetchStrength)
        } else {
            addDetailsViewModel.removeStrength(fetchStrength)
        }
    }

    override fun onItemConsumptionClick(position: Int, fetchGeography: FetchMolecule) {
        if (strflag.equals("ConsumptionStrength")) {
            addDetailsViewModel.lstconsum_strength.value = fetchGeography
        } else {
            addDetailsViewModel.lstbrand.value = fetchGeography
        }
        binding!!.editText.setText("")
        dialog!!.dismiss()
    }

    override fun onItemCompetitorBrandClick(position: Int, fetchGeography: FetchMolecule) {
        if (strflag.equals("CompetitorStrength"))
            addDetailsViewModel.lstcompetitorstrength.value = fetchGeography
        else
            addDetailsViewModel.lstcompetitorBrand.value = fetchGeography
        binding!!.editText.setText("")
        dialog!!.dismiss()
    }

}
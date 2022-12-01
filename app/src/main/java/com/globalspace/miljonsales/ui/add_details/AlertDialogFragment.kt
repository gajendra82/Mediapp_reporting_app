package com.globalspace.miljonsales.ui.add_details

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.globalspace.miljonsales.databinding.DialogSearchableSpinnerBinding
import com.globalspace.miljonsales.databinding.LayoutOtherDialogBinding
import com.globalspace.miljonsales.databinding.LayoutOtherDialogFragmentBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.layout_other_dialog_fragment.view.*
import javax.inject.Inject

class AlertDialogFragment @Inject constructor() : DialogFragment() {
    private lateinit var addDetailsViewModel: AddDetailsViewModel
    var strflag: String = "Hospital"
    var strtitle: String = "Enter Other Hospital"
    private var _binding: LayoutOtherDialogFragmentBinding? = null
    private val binding get() = _binding

    fun newInstance(
        addetailsViewModel: AddDetailsViewModel,
        flag: String,
        reasontitle: String
    ) {
        this.addDetailsViewModel = addetailsViewModel
        this.strflag = flag
        this.strtitle = reasontitle
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            LayoutOtherDialogFragmentBinding.inflate(LayoutInflater.from(context), container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        try {
            binding?.let {
                it.tvtitle.setText(strtitle)
                it.editText.hint = strflag

                it.btnOk.setOnClickListener {
                    try {
                        if (!binding!!.etother.text.toString().equals("")) {
                            addDetailsViewModel.setDataonModel(strflag,binding!!.etother.text.toString())
                            dialog?.dismiss()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Please Enter ${strflag}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        val msg = e.message
                        Log.i("tag", "error ${e.message}")
                    }

                }
                it.btnCancel.setOnClickListener { it1 ->
                    it.etother.setText("")
                    addDetailsViewModel.removeDataonModel(strflag)
                    dialog?.dismiss()
                }
            }
        } catch (e: Exception) {
            val msg = e.message
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog!!.setCancelable(false) //to stop closing dialog on touch outside
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        setDataOnEdittext()
    }


    private fun setDataOnEdittext() {
        when (strflag) {
            "Hospital" ->
                if (!addDetailsViewModel.strhospOtherdata.equals("")) {
                    binding!!.etother.setText(addDetailsViewModel.strhospOtherdata.value).toString()
                }
            "Facility" ->
                if (!addDetailsViewModel.strfacilityOtherdata.equals("")) {
                    binding!!.etother.setText(addDetailsViewModel.strfacilityOtherdata.value)
                        .toString()
                }
            "Speciality" ->
                if (!addDetailsViewModel.strspecialityOtherdata.equals("")) {
                    binding!!.etother.setText(addDetailsViewModel.strspecialityOtherdata.value)
                        .toString()
                }
        }
    }
}
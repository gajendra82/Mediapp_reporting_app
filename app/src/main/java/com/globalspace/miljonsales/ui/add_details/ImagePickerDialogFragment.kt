package com.globalspace.miljonsales.ui.add_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.DialogFragment
import com.globalspace.miljonsales.databinding.DialogSearchableSpinnerBinding
import com.globalspace.miljonsales.databinding.LayoutImagepickerBinding
import com.globalspace.miljonsales.ui.add_details.kyc.KYCFragment

class ImagePickerDialogFragment : DialogFragment() {

    private var _binding: LayoutImagepickerBinding? = null
    private val binding get() = _binding

    private lateinit var kycFragment: KYCFragment

    fun newInstance(kycFragment: KYCFragment) {
        this.kycFragment = kycFragment
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            LayoutImagepickerBinding.inflate(LayoutInflater.from(context), container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView(){
        try{
            binding?.let {
                it.cancelButton.setOnClickListener {
                    dialog!!.dismiss()
                }

                it.tvcamera.setOnClickListener {
                    kycFragment.callCamera()
                    dialog!!.dismiss()
                }

                it.tvgallery.setOnClickListener {
                    kycFragment.callGallery()
                    dialog!!.dismiss()
                }
            }

        }catch (e : Exception){
            Log.e("tag","DialogFragment ${e.message}")
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
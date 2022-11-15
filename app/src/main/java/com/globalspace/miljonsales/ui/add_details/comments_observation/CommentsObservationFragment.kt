package com.globalspace.miljonsales.ui.add_details.comments_observation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.globalspace.miljonsales.BuildConfig
import com.globalspace.miljonsales.MyApplication
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.FragmentAddDetailsBinding
import com.globalspace.miljonsales.databinding.FragmentCommentsObservationBinding
import com.globalspace.miljonsales.ui.add_details.AddDetaillsDialog
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CommentsObservationFragment: Fragment(R.layout.fragment_comments_observation) {

    private var _binding: FragmentCommentsObservationBinding? = null
    private val binding get() = _binding
    var uri: Uri? = null
    var flag_check: String = "new"

    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew
    private lateinit var addDetailsViewModel: AddDetailsViewModel
    internal val dialog = AddDetaillsDialog()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            _binding = FragmentCommentsObservationBinding.bind(view)
            (requireActivity().application as MyApplication).applicationComponent.inject(this)
            val map = (requireActivity().application as MyApplication).applicationComponent.getMap()
            addDetailsViewModel =
                ViewModelProvider(requireActivity(), mainviewmodelFactory).get(
                    AddDetailsViewModel::class.java
                )
            binding!!.comment = addDetailsViewModel
            addDetailsViewModel.CurrentFlag = "CommentObservation"

            binding?.let {
                it.edittextObservation.doOnTextChanged { text, start, count, after ->
                    if(addDetailsViewModel.ValidateName(text.toString())){
                        it.textinpObservation.error = null
                    }else
                        it.textinpObservation.error =  "Please Enter Observation"
                }
            }

        } catch (e: Exception) {
            val msg = e.message
        }
    }
}
package com.globalspace.miljonsales.ui.add_details.kyc

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.globalspace.miljonsales.BuildConfig
import com.globalspace.miljonsales.MyApplication
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.LayoutKycFragmentBinding
import com.globalspace.miljonsales.ui.URIPathHelper
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.ImagePickerDialogFragment
import com.globalspace.miljonsales.ui.add_details_dashboard.AddImages
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class KYCFragment : Fragment(R.layout.layout_kyc_fragment) {


    private var _binding: LayoutKycFragmentBinding? = null
    private val binding get() = _binding
    var uri: Uri? = null
    var strImages = ""
    private lateinit var sPref: SharedPreferences
    private lateinit var employeeID: String
    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew
    private lateinit var addDetailsViewModel: AddDetailsViewModel
    internal val dialog = ImagePickerDialogFragment()
    var flag_check: String = "new"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            _binding = LayoutKycFragmentBinding.bind(view)
            (requireActivity().application as MyApplication).applicationComponent.inject(this)
            val map = (requireActivity().application as MyApplication).applicationComponent.getMap()
            addDetailsViewModel =
                ViewModelProvider(requireActivity(), mainviewmodelFactory).get(
                    AddDetailsViewModel::class.java
                )
            addDetailsViewModel.CurrentFlag = "KYC"
            binding!!.kycdetails = addDetailsViewModel
            sPref = requireContext().getSharedPreferences(
                requireContext().getResources().getString(R.string.app_name), Context.MODE_PRIVATE
            )
            employeeID = sPref!!.getString(
                requireContext().getResources().getString(R.string.employee_id),
                ""
            )
                .toString()
            addDetailsViewModel.checkpermission.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    if (it == true && !flag_check.equals("new")) {
                        invokeCamera()
                    }
                })
            binding?.let {
                it.imggstno.setOnClickListener {
                    flag_check = "GstNo"
                    dialog.newInstance(this)
                    dialog.show((context as AppCompatActivity).supportFragmentManager, "Dialog")
                }
                it.imgpanNo.setOnClickListener {
                    flag_check = "PanNo"
                    dialog.newInstance(this)
                    dialog.show((context as AppCompatActivity).supportFragmentManager, "Dialog")
                }
                it.edittextgstno.doOnTextChanged { text, start, count, after ->
                    addDetailsViewModel!!.ValidateGstNo(text.toString(), it.txtgstno)
                }
                it.etPanNo.doOnTextChanged { text, start, count, after ->
                    addDetailsViewModel!!.ValidatePanNo(text.toString(), it.txtpanno)
                }
            }

        } catch (e: Exception) {
            val msg = e.message
        }
    }


    fun callCamera() {
        addDetailsViewModel.callpermission(requireContext(), getImage)
    }

     fun callGallery(){
        addDetailsViewModel.callgallerypermission(requireContext(), getGalleryImage)
    }

    private fun invokeCamera() {
        val file = CreateImageFile(requireContext())
        try {
            uri =
                FileProvider.getUriForFile(
                    requireContext(),
                    "${BuildConfig.APPLICATION_ID}.provider",
                    file
                )
        } catch (e: Exception) {
            val msg = e.message
        }
        strImages = file.absolutePath

        // addDetailsViewModel.strlstimages.add(file.absolutePath)
        getImage.launch(uri)
    }

    private fun CreateImageFile(context: Context): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val tmpfile = File.createTempFile("reporting_${timestamp}", ".jpg", imageDirectory)
        return tmpfile
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            flag_check+"_${employeeID}",
            null
        )
        return Uri.parse(path)
    }

    val getImage = registerForActivityResult(
        ActivityResultContracts.TakePicture(),
        ActivityResultCallback { success ->
            if (success) {
                val bitmap = BitmapFactory.decodeFile(strImages)
                val uriPathHelper = URIPathHelper()
                val filePath = uriPathHelper.getPath(requireContext(),
                    getImageUri(requireContext(),bitmap)!!
                )
              /*  if(flag_check.equals("GstNo"))
                    setImageURI(binding!!.imgGstno,uri!!)
                   else
                    setImageURI(binding!!.imgPanno,uri!!)*/

                addDetailsViewModel.addImageData(AddImages(flag_check,filePath!!,""))
                flag_check = "new"
            } else {
                flag_check = "new"
               // Toast.makeText(context, "Errorr", Toast.LENGTH_LONG).show()
            }
        }
    )

    private fun setImageURI(imageView: ImageView,uri: Uri){
        imageView.visibility = View.VISIBLE
        imageView.setImageURI(uri)
    }

    val getGalleryImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data?.data
                val uriPathHelper = URIPathHelper()
                val filePath = uriPathHelper.getPath(requireContext(), data!!)
             /*   if(flag_check.equals("GstNo"))
                    setImageURI(binding!!.imgGstno,data)
                else
                    setImageURI(binding!!.imgPanno,data)*/
                addDetailsViewModel.addImageData(AddImages("KYC",filePath!!,""))
            }
        }

}
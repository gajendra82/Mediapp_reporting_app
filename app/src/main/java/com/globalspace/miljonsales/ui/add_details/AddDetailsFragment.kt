package com.globalspace.miljonsales.ui.add_details

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.globalspace.miljonsales.BuildConfig
import com.globalspace.miljonsales.MyApplication
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.FragmentAddDetailsBinding
import com.globalspace.miljonsales.ui.URIPathHelper
import com.globalspace.miljonsales.ui.add_details_dashboard.AddImages
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class AddDetailsFragment : Fragment(R.layout.fragment_add_details){

    private var _binding: FragmentAddDetailsBinding? = null
    private val binding get() = _binding
    var uri: Uri? = null
    var flag_check: String = "new"
    lateinit var textRecognizer: TextRecognizer
    var strImages = ""

    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew
    private lateinit var addDetailsViewModel: AddDetailsViewModel
    internal val dialog = AddDetaillsDialog()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            _binding = FragmentAddDetailsBinding.bind(view)
            (requireActivity().application as MyApplication).applicationComponent.inject(this)
            val map = (requireActivity().application as MyApplication).applicationComponent.getMap()
            addDetailsViewModel =
                ViewModelProvider(requireActivity(), mainviewmodelFactory).get(
                    AddDetailsViewModel::class.java
                )
            binding!!.addDetails = addDetailsViewModel
            addDetailsViewModel.CurrentFlag = "Add Details"

            addDetailsViewModel.lstdata.observe(viewLifecycleOwner) {
                binding!!.edittextState.setText(it.STATE_NAME.toString())
            }
            addDetailsViewModel.lstcitydata.observe(viewLifecycleOwner) {
                binding!!.edittextCity.setText(it.CITY_NAME.toString())
            }
            addDetailsViewModel.lsthospdata.observe(viewLifecycleOwner) {
                binding!!.edittextHospital.setText(it.HospitalType.toString())
            }
            addDetailsViewModel.boardimage.observe(viewLifecycleOwner) {
                binding!!.imgViewer.visibility = View.VISIBLE
                binding!!.imgViewer.setImageURI(it)
            }
            addDetailsViewModel.physicianimage.observe(viewLifecycleOwner) {
                binding!!.imgViewerPhy.visibility = View.VISIBLE
                binding!!.imgViewerPhy.setImageURI(it)
            }
            addDetailsViewModel.checkpermission.observe(viewLifecycleOwner, Observer {
                if (it == true && !flag_check.equals("new")) {
                    invokeCamera()
                }
            })

            binding?.let {
                it.edittextName.doOnTextChanged { text, start, count, after ->
                    if (addDetailsViewModel.ValidateName(text.toString())) {
                        it.textinpName.error = null
                    } else
                        it.textinpName.error = "Please Enter Name"
                }

                it.edittextAddress.doOnTextChanged { text, start, count, after ->
                    if (addDetailsViewModel.ValidateName(text.toString())) {
                        it.textinpAddress.error = null
                    } else
                        it.textinpAddress.error = "Please Enter Address"
                }

                it.edittextPincode.doOnTextChanged { text, start, count, after ->
                    addDetailsViewModel.ValidatePincode(text.toString(), it.textinpPincode)
                }

                it.edittextState.setOnClickListener {
                    Log.i("tag", "clicked")
                    dialog.newInstance(addDetailsViewModel, "State")
                    dialog.show((context as AppCompatActivity).supportFragmentManager, "Dialog")
                }

                it.edittextCity.setOnClickListener {
                    Log.i("tag", "clicked")
                    dialog.newInstance(addDetailsViewModel, "City")
                    dialog.show((context as AppCompatActivity).supportFragmentManager, "Dialog")
                }
                it.edittextHospital.setOnClickListener {
                    Log.i("tag", "clicked")
                    dialog.newInstance(addDetailsViewModel, "Hospital")
                    dialog.show((context as AppCompatActivity).supportFragmentManager, "Dialog")
                }
                it.tvborad.setOnClickListener {
                    flag_check = "board"
                    addDetailsViewModel.callpermission(requireContext(), getImage)
                }
                it.tvphysician.setOnClickListener {
                    flag_check = "physician"
                    addDetailsViewModel.callpermission(requireContext(), getImage)
                }
            }

        } catch (e: Exception) {
            val msg = e.message
        }
    }
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "reporting_${timestamp}",
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
                if (flag_check.equals("board")) {
                    binding!!.imgViewer.visibility = View.VISIBLE
                    binding!!.imgViewer.setImageURI(uri)
                    addDetailsViewModel.boardimage.value = uri
                    FetchTextFromImage(flag_check,uri!!,filePath)
                } else {
                    binding!!.imgViewerPhy.visibility = View.VISIBLE
                    binding!!.imgViewerPhy.setImageURI(uri)
                    addDetailsViewModel.physicianimage.value = uri
                    FetchTextFromImage(flag_check,uri!!,filePath)
                }

                flag_check = "new"

            } else {
               // Toast.makeText(context, "Errorr", Toast.LENGTH_LONG).show()
                if (flag_check.equals("board"))
                    binding!!.imgViewer.visibility = View.GONE
                else
                    binding!!.imgViewerPhy.visibility = View.GONE
                flag_check = "new"
            }
        }
    )

    private fun FetchTextFromImage(flag : String,uri: Uri,filepath : String?) {
        try {
            textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val inputImage = InputImage.fromFilePath(requireContext(),uri)
            val texttaskResult = textRecognizer.process(inputImage)
                .addOnSuccessListener {
                    val text = it.text
                    addDetailsViewModel.addImageData(AddImages(flag,filepath!!,text))
                }.addOnFailureListener {

                }
        } catch (e: Exception) {
            val msg =e.message
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
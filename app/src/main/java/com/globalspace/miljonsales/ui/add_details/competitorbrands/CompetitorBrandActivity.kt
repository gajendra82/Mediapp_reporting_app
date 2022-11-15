package com.globalspace.miljonsales.ui.add_details.competitorbrands

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.globalspace.miljonsales.MyApplication
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.ActivityCompetitorBrandBinding
import com.globalspace.miljonsales.ui.add_details.AddDetaillsDialog
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.AlertDialogActivity
import com.globalspace.miljonsales.utils.WindowBar
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import javax.inject.Inject

class CompetitorBrandActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCompetitorBrandBinding
    private var addDetailsViewModel: AddDetailsViewModel? = null
    internal val dialog = AddDetaillsDialog()
    lateinit var radioButton: RadioButton

    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew
    private lateinit var alertdialog: AlertDialogActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_competitor_brand)
        binding.lifecycleOwner = this
        WindowBar.setStatusColor(window, this@CompetitorBrandActivity)
        (application as MyApplication).applicationComponent.inject(this)
        try {
            this.addDetailsViewModel = CompetitorBrandFragment.addDetailsViewModel
        } catch (e: Exception) {
            val msg = e.message
        }
        binding.competitorbrandData = addDetailsViewModel
        binding.toolbarConsumption.setNavigationOnClickListener {
            onBackPressed()
        }
        val lstreasons = resources.getStringArray(R.array.Reasons)
        binding.let {

            it.edittextCompstrength.setOnClickListener {
                Log.i("tag", "clicked")
                dialog.newInstance(addDetailsViewModel!!, "CompetitorStrength")
                dialog.show(supportFragmentManager, "Dialog")
            }

            it.edittextCompbrand.setOnClickListener {
                Log.i("tag", "clicked")
                dialog.newInstance(addDetailsViewModel!!, "CompetitorBrand")
                dialog.show(supportFragmentManager, "Dialog")
            }


            it.edittextQty.doOnTextChanged { text, start, count, after ->
                if(addDetailsViewModel!!.ValidateName(text.toString())){
                    it.textinpQty.error = null
                }else
                    it.textinpQty.error =  "Please Enter Quantity"
            }

            it.edittextMrp.doOnTextChanged { text, start, count, after ->
                if(addDetailsViewModel!!.ValidateName(text.toString())){
                    it.textinpMrp.error = null
                }else
                    it.textinpMrp.error =  "Please Enter Mrp Rate"
            }


            alertdialog = AlertDialogActivity(this)

            it.btnAdd.setOnClickListener {
                AddDetailsValidation()
            }
            addDetailsViewModel!!.lstcompetitorstrength.observe(this, Observer {
                if (it == null)
                    binding!!.edittextCompstrength.setText("")
                else
                    binding!!.edittextCompstrength.setText(it!!.STRENGTH.toString())
            })
            addDetailsViewModel!!.lstcompetitorBrand.observe(this, Observer {
                if (it == null)
                    binding!!.edittextCompbrand.setText("")
                else
                    binding!!.edittextCompbrand.setText(it!!.BRAND.toString())
            })
        }
    }


    private fun AddDetailsValidation() {
        addDetailsViewModel?.let {
            if (it.ValidateCompetitorStrength()) {
                if (it.ValidateCompetitorBrand()) {
                    if (it.ValidateName(it.strquantitycb.value.toString())) {
                        if (it.ValidateName(it.strmrpcb.value.toString())) {
                            it.addCompetitorBrand()
                            onBackPressed()
                        } else {
                            Toast.makeText(this, "Please Enter Mrp Rate", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Please Enter Quantity", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please Select Competitor Brand", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Please Select Strength", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}
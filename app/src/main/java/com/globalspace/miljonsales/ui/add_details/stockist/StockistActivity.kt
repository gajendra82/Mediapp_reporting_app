package com.globalspace.miljonsales.ui.add_details.stockist

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.globalspace.miljonsales.MyApplication
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.ActivityConsumptionBinding
import com.globalspace.miljonsales.databinding.ActivityStockistBinding
import com.globalspace.miljonsales.ui.add_details.AddDetaillsDialog
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.consumptions.ConsumptionFragment
import com.globalspace.miljonsales.utils.WindowBar
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import javax.inject.Inject

class StockistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockistBinding
    private var addDetailsViewModel: AddDetailsViewModel? = null
    internal val dialog = AddDetaillsDialog()

    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stockist)
        binding.lifecycleOwner = this
        WindowBar.setStatusColor(window, this@StockistActivity)
        (application as MyApplication).applicationComponent.inject(this)
        try {
            this.addDetailsViewModel = StockistFragment.addDetailsViewModel
        } catch (e: Exception) {
            val msg = e.message
        }
        binding.stockistData = addDetailsViewModel
        binding.toolbarStockist.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.let{
            it.edittextstockState.setOnClickListener {
                Log.i("tag", "clicked")
                dialog.newInstance(addDetailsViewModel!!, "StockistState")
                dialog.show(supportFragmentManager, "Dialog")
            }
            it.edittextstockCity.setOnClickListener {
                Log.i("tag", "clicked")
                dialog.newInstance(addDetailsViewModel!!, "StockistCity")
                dialog.show(supportFragmentManager, "Dialog")
            }
            it.edittextstockName.doOnTextChanged { text, start, count, after ->
                if (addDetailsViewModel!!.ValidateName(text.toString())) {
                    it.textinpstockName.error = null
                } else
                    it.textinpstockName.error = "Please Enter Stockist Name"
            }
            it.edittextstockAddress.doOnTextChanged { text, start, count, after ->
                if (addDetailsViewModel!!.ValidateName(text.toString())) {
                    it.textinpstockAddress.error = null
                } else
                    it.textinpstockAddress.error = "Please Enter Address"
            }
            it.edittextstockPincode.doOnTextChanged { text, start, count, after ->
                addDetailsViewModel!!.ValidatePincode(text.toString(), it.textinpstockPincode)
            }
            it.edittextcontactName.doOnTextChanged { text, start, count, after ->
                if (addDetailsViewModel!!.ValidateName(text.toString())) {
                    it.textinpcontactName.error = null
                } else
                    it.textinpcontactName.error = "Please Enter Contact Name"
            }
            it.edittextContactnum.doOnTextChanged { text, start, count, after ->
                addDetailsViewModel!!.ValidateContactNumber(text.toString(), it.textinpContactnum)
            }


            addDetailsViewModel!!.lststockdata.observe(this, Observer {
                if (it == null)
                    binding!!.edittextstockState.setText("")
                else
                    binding!!.edittextstockState.setText(it!!.STATE_NAME.toString())
            })
            addDetailsViewModel!!.lststockcitydata.observe(this, Observer {
                if (it == null)
                    binding!!.edittextstockCity.setText("")
                else
                    binding!!.edittextstockCity.setText(it.CITY_NAME.toString())
            })
            it.btnAdd.setOnClickListener {
                AddDetailsValidation()
            }
        }

    }

    private fun AddDetailsValidation() {
        addDetailsViewModel?.let {
            if (it.ValidateName(it.strstockname.value.toString())) {
                if (it.ValidateName(it.strstockaddress.value.toString())) {
                    if(it.ValidateStockState()){
                        if(it.ValidateStockCity()){
                            if (it.ValidateName(it.strstockpersonalname.value.toString())) {
                                if (it.ValidateContactNumber(it.strstockcontactnum.value.toString())) {
                                    if (it.ValidatePincode(it.strstockpincode.value.toString())) {
                                        it.addStockist()
                                        onBackPressed()
                                    }else {
                                        Toast.makeText(this, "Please Enter Pincode", Toast.LENGTH_SHORT).show()
                                    }
                                }else {
                                    Toast.makeText(this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show()
                                }
                            }else {
                                Toast.makeText(this, "Please Enter Contact Name", Toast.LENGTH_SHORT).show()
                            }
                        }else {
                            Toast.makeText(this, "Please Select City", Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        Toast.makeText(this, "Please Select State", Toast.LENGTH_SHORT).show()
                    }
                }else {
                    Toast.makeText(this, "Please Enter Stockist Address", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "Please Enter Stockist Name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}
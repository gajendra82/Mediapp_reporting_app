package com.globalspace.miljonsales.ui.add_details.consumptions

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
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
import com.globalspace.miljonsales.databinding.ActivityConsumptionBinding
import com.globalspace.miljonsales.ui.add_details.AddDetaillsDialog
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.ui.add_details.AlertDialogActivity
import com.globalspace.miljonsales.utils.WindowBar
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import org.w3c.dom.Text
import javax.inject.Inject


class ConsumptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConsumptionBinding
    private var addDetailsViewModel: AddDetailsViewModel? = null
    internal val dialog = AddDetaillsDialog()
    lateinit var radioButton: RadioButton

    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew
    private lateinit var alertdialog: AlertDialogActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_consumption)
        binding.lifecycleOwner = this
        WindowBar.setStatusColor(window, this@ConsumptionActivity)
        (application as MyApplication).applicationComponent.inject(this)
        try {
            this.addDetailsViewModel = ConsumptionFragment.addDetailsViewModel
        } catch (e: Exception) {
            val msg = e.message
        }
        binding.consumptionData = addDetailsViewModel
        binding.toolbarConsumption.setNavigationOnClickListener {
            onBackPressed()
        }
        val lstreasons = resources.getStringArray(R.array.Reasons)
        binding.let {
            it.edittextStrength.setOnClickListener {
                Log.i("tag", "clicked")
                dialog.newInstance(addDetailsViewModel!!, "ConsumptionStrength")
                dialog.show(supportFragmentManager, "Dialog")
            }

            it.edittextBrand.setOnClickListener {
                Log.i("tag", "clicked")
                dialog.newInstance(addDetailsViewModel!!, "ConsumptionBrand")
                dialog.show(supportFragmentManager, "Dialog")
            }
            it.edittextQty.doOnTextChanged { text, start, count, after ->
                if(addDetailsViewModel!!.ValidateName(text.toString())){
                    it.textinpQty.error = null
                }else
                    it.textinpQty.error =  "Please Enter Quantity"
            }
            it.edittextRate.doOnTextChanged { text, start, count, after ->
                if(addDetailsViewModel!!.ValidateName(text.toString())){
                    it.textinpRate.error = null
                }else
                    it.textinpRate.error =  "Please Enter Rate"
            }
            it.edittextMrp.doOnTextChanged { text, start, count, after ->
                if(addDetailsViewModel!!.ValidateName(text.toString())){
                    it.textinpMrp.error = null
                }else
                    it.textinpMrp.error =  "Please Enter Mrp Rate"
            }

            val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, lstreasons)
            it.autocompleteview.setAdapter(arrayAdapter)
            alertdialog = AlertDialogActivity(this)

            it.btnAdd.setOnClickListener {
                AddDetailsValidation()
            }
            addDetailsViewModel!!.lstconsum_strength.observe(this, Observer {
                if (it == null)
                    binding!!.edittextStrength.setText("")
                else
                    binding!!.edittextStrength.setText(it!!.STRENGTH.toString())
            })
            addDetailsViewModel!!.strradionselected.value = "Available"
            binding.radioGroup1.setOnCheckedChangeListener(object :
                RadioGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                    radioButton = findViewById(checkedId)
                    val value = radioButton.text.toString()
                    addDetailsViewModel!!.strradionselected.value = radioButton.text.toString()
                    if (value.equals("Not Available")) {
                        it.textinpReason.visibility = View.VISIBLE
                        val arrayAdapter = ArrayAdapter(
                            this@ConsumptionActivity,
                            R.layout.dropdown_item,
                            lstreasons
                        )
                        it.autocompleteview.setAdapter(arrayAdapter)
                    } else {
                        it.textinpReason.visibility = View.GONE
                    }
                }
            })

            binding.autocompleteview.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
                val value: String = binding.autocompleteview.text.toString()
                if (value.equals("Other")) {
                    addDetailsViewModel!!.strreasonselected.value =
                        binding.autocompleteview.text.toString()
                    alertdialog.othershowdialog(addDetailsViewModel!!)
                } else {
                    addDetailsViewModel!!.strreasonselected.value =
                        binding.autocompleteview.text.toString()
                }
            })

            addDetailsViewModel!!.lstbrand.observe(this, Observer {
                if (it == null)
                    binding!!.edittextBrand.setText("")
                else {
                    binding!!.edittextBrand.setText(it.BRAND.toString())
                    binding!!.radioGroup1.visibility = View.VISIBLE
                    GetSelectedRadio()
                }
            })
        }
    }


    private fun GetSelectedRadio() {
        try {
            val selectedOption: Int = binding!!.radioGroup1!!.checkedRadioButtonId
            if (selectedOption != -1) {
                radioButton = findViewById(selectedOption)
                addDetailsViewModel!!.strradionselected.value = radioButton.text.toString()
            }
        } catch (e: Exception) {
            val msg = e.message
        }
    }

    private fun AddDetailsValidation() {
        addDetailsViewModel?.let {
            if(it.ValidateStrength()){
                if(it.ValidateBrand()){
                    if (it.ValidateName(it.strquantity.value.toString())) {
                        if (it.ValidateName(it.strrate.value.toString())) {
                            if (it.ValidateName(it.strmrp.value.toString())) {
                                it.addConsumption()
                                onBackPressed()
                            }else {
                                Toast.makeText(this, "Please Enter Mrp Rate", Toast.LENGTH_SHORT).show()
                            }
                        }else {
                            Toast.makeText(this, "Please Enter Rate", Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        Toast.makeText(this, "Please Enter Quantity", Toast.LENGTH_SHORT).show()
                    }
                }else {
                    Toast.makeText(this, "Please Select Brand", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "Please Select Strength", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val lstreasons = resources.getStringArray(R.array.Reasons)
        val arrayAdapter =
            ArrayAdapter(this@ConsumptionActivity, R.layout.dropdown_item, lstreasons)
        binding.autocompleteview.setAdapter(arrayAdapter)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
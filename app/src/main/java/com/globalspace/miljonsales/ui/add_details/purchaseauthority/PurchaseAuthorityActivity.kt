package com.globalspace.miljonsales.ui.add_details.purchaseauthority

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.globalspace.miljonsales.MyApplication
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.LayoutActivityPurchaseBinding
import com.globalspace.miljonsales.ui.add_details.AddDetaillsDialog
import com.globalspace.miljonsales.ui.add_details.AddDetailsViewModel
import com.globalspace.miljonsales.utils.WindowBar
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import javax.inject.Inject

class PurchaseAuthorityActivity : AppCompatActivity() {

    private lateinit var binding: LayoutActivityPurchaseBinding
    private var addDetailsViewModel: AddDetailsViewModel? = null
    internal val dialog = AddDetaillsDialog()

    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.layout_activity_purchase)
        binding.lifecycleOwner = this
        WindowBar.setStatusColor(window, this@PurchaseAuthorityActivity)
        (application as MyApplication).applicationComponent.inject(this)
        try {
            this.addDetailsViewModel = PurchaseAuthorityFragment.addDetailsViewModel
        } catch (e: Exception) {
            val msg = e.message
        }
        binding.purchaseactivityData = addDetailsViewModel
        binding.toolbarConsumption.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.let {
            it.btnAdd.setOnClickListener {
                AddDetailsValidation()
            }
            it.edittextName.doOnTextChanged { text, start, count, after ->
                if (addDetailsViewModel!!.ValidateName(text.toString())) {
                    it.textinpName.error = null
                } else
                    it.textinpName.error = "Please Enter Name"
            }
            it.edittextDesignation.doOnTextChanged { text, start, count, after ->
                addDetailsViewModel!!.ValidateEmailAddress(text.toString(), it.textinpDesignation)
            }
            it.edittextContactnum.doOnTextChanged { text, start, count, after ->
                addDetailsViewModel!!.ValidateContactNumber(text.toString(), it.textinpContactnum)
            }
        }
    }

    private fun AddDetailsValidation() {
        addDetailsViewModel?.let {
            if (it.ValidateName(it.strpurch_authorityname.value.toString())) {
                if (it.ValidateEmailAddress(it.strpurch_authoritydesignation.value.toString())) {
                    if (it.ValidateContactNumber(it.strpurch_authoritycontactnumber.value.toString())) {
                        it.addPurchaseAuthority()
                        onBackPressed()
                    } else {
                        Toast.makeText(this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please Enter Valid Email ID", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
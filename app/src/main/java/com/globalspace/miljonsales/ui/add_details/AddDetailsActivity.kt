package com.globalspace.miljonsales.ui.add_details

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.globalspace.miljonsales.MyApplication
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.AddDetailsActivityBinding
import com.globalspace.miljonsales.ui.add_details.comments_observation.CommentsObservationFragment
import com.globalspace.miljonsales.ui.add_details.competitorbrands.CompetitorBrandFragment
import com.globalspace.miljonsales.ui.add_details.consumptions.ConsumptionFragment
import com.globalspace.miljonsales.ui.add_details.facility.FacilityFragment
import com.globalspace.miljonsales.ui.add_details.kyc.KYCFragment
import com.globalspace.miljonsales.ui.add_details.molecule.MoleculeFragment
import com.globalspace.miljonsales.ui.add_details.purchaseauthority.PurchaseAuthorityFragment
import com.globalspace.miljonsales.ui.add_details.speciality.SpecialityFragment
import com.globalspace.miljonsales.ui.add_details.stockist.StockistFragment
import com.globalspace.miljonsales.ui.add_details.strength.StrengthFragment
import com.globalspace.miljonsales.utils.WindowBar
import com.globalspace.miljonsales.viewmodelfactory.MainViewModelFactoryNew
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class AddDetailsActivity : AppCompatActivity() {

    private lateinit var binding: AddDetailsActivityBinding
    private lateinit var addDetailsViewModel: AddDetailsViewModel
    private lateinit var alertdialog: AlertDialogActivity

    @Inject
    lateinit var mainviewmodelFactory: MainViewModelFactoryNew
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.add_details_activity)
        binding.lifecycleOwner = this
        WindowBar.setStatusColor(window, this@AddDetailsActivity)
        (application as MyApplication).applicationComponent.inject(this)
        val map = (application as MyApplication).applicationComponent.getMap()
        alertdialog = AlertDialogActivity(this)
        addDetailsViewModel =
            ViewModelProvider(this, mainviewmodelFactory).get(AddDetailsViewModel::class.java)
        binding.dData = addDetailsViewModel
        setCurrentFragment(AddDetailsFragment())
        binding.btnNext.setOnClickListener {
            onNextValidation(addDetailsViewModel.CurrentFlag)
        }

        binding.toolbarSupplementinfo.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    internal fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager
            .beginTransaction().apply {
                replace(R.id.frame_add_details, fragment)
                    .commit();
            }


    private fun onNextValidation(strcurrentfrag: String) {
        if (strcurrentfrag.equals("Add Details")) {
            AddDetailsValidation()
        } else if (strcurrentfrag.equals("Facility")) {
            if (addDetailsViewModel.lstfacility.size > 0) {
                onNextFragment(addDetailsViewModel.CurrentFlag)
            } else {
                alertdialog.showdialog("Choose Facility")
            }
        } else if (strcurrentfrag.equals("Speciality")) {
            if (addDetailsViewModel.lstspeciality.size > 0) {
                onNextFragment(addDetailsViewModel.CurrentFlag)
            } else {
                alertdialog.showdialog("Choose Speciality")
            }
        } else if (strcurrentfrag.equals("Molecule")) {
            if (addDetailsViewModel.lstmolecule.size > 0) {
                onNextFragment(addDetailsViewModel.CurrentFlag)
            } else {
                alertdialog.showdialog("Choose Molecule")
            }
        } else if (strcurrentfrag.equals("Strength")) {
            if (addDetailsViewModel.lststrength.size > 0) {
                onNextFragment(addDetailsViewModel.CurrentFlag)
            } else {
                alertdialog.showdialog("Choose Strength")
            }
        } else if (strcurrentfrag.equals("Consumption")) {
            if (addDetailsViewModel.data.size > 0) {
                onNextFragment(addDetailsViewModel.CurrentFlag)
            } else {
                alertdialog.showdialog("Please Create Consumption")
            }
        } else if (strcurrentfrag.equals("CompetitorBrand")) {
            if (addDetailsViewModel.datacompetitorbrand.size > 0) {
                onNextFragment(addDetailsViewModel.CurrentFlag)
            } else {
                alertdialog.showdialog("Please Create Competitor Brand")
            }
        } else if (strcurrentfrag.equals("PurchaseAuthority")) {
            if (addDetailsViewModel.datapurchase.size > 0) {
                onNextFragment(addDetailsViewModel.CurrentFlag)
            } else {
                alertdialog.showdialog("Please Create Purchase Authority")
            }
        } else if (strcurrentfrag.equals("CommentObservation")) {
            CommentObservationValidation()
        } else if (strcurrentfrag.equals("Stockist")) {
            if (addDetailsViewModel.datastockist.size > 0) {
                onNextFragment(addDetailsViewModel.CurrentFlag)
            } else {
                alertdialog.showdialog("Please Create Stockist")
            }
        } else if (strcurrentfrag.equals("KYC")) {
            KycValidation()
        } else {
            onNextFragment(addDetailsViewModel.CurrentFlag)
        }
    }

    private fun onNextFragment(strcurrentfrag: String) {
        binding.btnNext.text = "Next"
        if (strcurrentfrag.equals("Add Details")) {
            binding.toolbarSupplementinfo.title = "Facility"
            setCurrentFragment(FacilityFragment())
        } else if (strcurrentfrag.equals("Facility")) {
            binding.toolbarSupplementinfo.title = "Speciality"
            setCurrentFragment(SpecialityFragment())
        } else if (strcurrentfrag.equals("Speciality")) {
            binding.toolbarSupplementinfo.title = "Molecule"
            setCurrentFragment(MoleculeFragment())
        } else if (strcurrentfrag.equals("Molecule")) {
            binding.toolbarSupplementinfo.title = "Strength"
            setCurrentFragment(StrengthFragment())
        } else if (strcurrentfrag.equals("Strength")) {
            binding.toolbarSupplementinfo.title = "Consumption"
            setCurrentFragment(ConsumptionFragment())
        } else if (strcurrentfrag.equals("Consumption")) {
            binding.toolbarSupplementinfo.title = "Competitor Brand"
            setCurrentFragment(CompetitorBrandFragment())
        } else if (strcurrentfrag.equals("CompetitorBrand")) {
            binding.toolbarSupplementinfo.title = "Purchase Authority"
            setCurrentFragment(PurchaseAuthorityFragment())
        } else if (strcurrentfrag.equals("PurchaseAuthority")) {
            binding.toolbarSupplementinfo.title = "Comment/Observation"
            setCurrentFragment(CommentsObservationFragment())
        }/* else if (strcurrentfrag.equals("CommentObservation")) {
            binding.btnNext.text = "SAVE"
            binding.toolbarSupplementinfo.title = "Stockist"
            setCurrentFragment(StockistFragment())
        }*/ else if (strcurrentfrag.equals("CommentObservation")) {
            binding.toolbarSupplementinfo.title = "Stockist"
            setCurrentFragment(StockistFragment())
        } else if (strcurrentfrag.equals("Stockist")) {
            binding.btnNext.text = "SAVE"
            binding.toolbarSupplementinfo.title = "KYC"
            setCurrentFragment(KYCFragment())
        }/*else if (strcurrentfrag.equals("KYC")) {
            binding.btnNext.text = "SAVE"
            binding.toolbarSupplementinfo.title = "KYC"
            setCurrentFragment(KYCFragment())
        }*/
    }

    private fun onBackFragment(strcurrentfrag: String) {
        binding.btnNext.text = "Next"
        if (strcurrentfrag.equals("KYC")) {
            binding.toolbarSupplementinfo.title = "Stockist"
            setCurrentFragment(StockistFragment())
        } else if (strcurrentfrag.equals("Stockist")) {
            binding.toolbarSupplementinfo.title = "Comment/Observation"
            setCurrentFragment(CommentsObservationFragment())
        } else if (strcurrentfrag.equals("CommentObservation")) {
            binding.toolbarSupplementinfo.title = "Purchase Authority"
            setCurrentFragment(PurchaseAuthorityFragment())
        } else if (strcurrentfrag.equals("PurchaseAuthority")) {
            binding.toolbarSupplementinfo.title = "Competitor Brand"
            setCurrentFragment(CompetitorBrandFragment())
        } else if (strcurrentfrag.equals("CompetitorBrand")) {
            binding.toolbarSupplementinfo.title = "Consumption"
            setCurrentFragment(ConsumptionFragment())
        } else if (strcurrentfrag.equals("Consumption")) {
            binding.toolbarSupplementinfo.title = "Strength"
            setCurrentFragment(StrengthFragment())
        } else if (strcurrentfrag.equals("Strength")) {
            binding.toolbarSupplementinfo.title = "Molecule"
            setCurrentFragment(MoleculeFragment())
        } else if (strcurrentfrag.equals("Molecule")) {
            binding.toolbarSupplementinfo.title = "Speciality"
            setCurrentFragment(SpecialityFragment())
        } else if (strcurrentfrag.equals("Speciality")) {
            binding.toolbarSupplementinfo.title = "Facility"
            setCurrentFragment(FacilityFragment())
        } else if (strcurrentfrag.equals("Facility")) {
            binding.toolbarSupplementinfo.title = "Add Details"
            setCurrentFragment(AddDetailsFragment())
        } else {
            addDetailsViewModel?.let {
                if(it.ValidateName(it.strname.value.toString())|| it.ValidateName(it.straddress.value.toString()) ||
                    it.ValidateState() || it.ValidateCity() || it.ValidatePincode(it.strpincode.value.toString())
                    || it.ValidateHospital() || it.ValidateBoardImage() || it.ValidatePhysicianImage()
                ){
                    alertdialog.showdialog(this)
                }else{
                    super.onBackPressed()
                }
            }
        }
    }

    private fun AddDetailsValidation() {
        addDetailsViewModel?.let {
            if (it.ValidateName(it.strname.value.toString())) {
                if (it.ValidateName(it.straddress.value.toString())) {
                    if (it.ValidateState()) {
                        if (it.ValidateCity()) {
                            if (it.ValidatePincode(it.strpincode.value.toString())) {
                                if (it.ValidateHospital()) {
                                    if(it.ValidateBoardImage()){
                                        if(it.ValidatePhysicianImage()){
                                            onNextFragment(addDetailsViewModel.CurrentFlag)
                                        }else{
                                            Toast.makeText(
                                                this,
                                                "Please Select Physician Image",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }
                                    }else{
                                        Toast.makeText(
                                            this,
                                            "Please Select Board Image",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }

                                } else {
                                    Toast.makeText(
                                        this,
                                        "Please Select Hospital",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                            } else {
                                Toast.makeText(this, "Please Enter Pincode", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            Toast.makeText(this, "Please Select City", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(this, "Please Select State", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please Enter Address", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun CommentObservationValidation() {
        addDetailsViewModel?.let {
            if (it.ValidateName(it.strobservation.value.toString())) {
                onNextFragment(addDetailsViewModel.CurrentFlag)
            } else {
                Toast.makeText(this, "Please Enter Observation", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun KycValidation() {
        addDetailsViewModel?.let {
            if (it.ValidateGstNo(it.strgstno.value.toString())) {
                if (it.ValidatePanNo(it.strpanno.value.toString())) {
                    binding!!.addDetailsProgressbar.visibility = View.VISIBLE
                    getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    addDetailsViewModel.SubmitRCPAData(this, binding!!.addDetailsProgressbar)
                } else {
                    Toast.makeText(this, "Please Enter Valid Pan Number", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Enter Valid Gst Number", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onBackPressed() {
        onBackFragment(addDetailsViewModel.CurrentFlag)
    }
}
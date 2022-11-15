package com.globalspace.miljonsales.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.globalspace.miljonsales.ui.add_details.AddDetailsActivity
import com.globalspace.miljonsales.ui.add_details.AddDetailsFragment
import com.globalspace.miljonsales.ui.add_details.comments_observation.CommentsObservationFragment
import com.globalspace.miljonsales.ui.add_details.competitorbrands.CompetitorBrandActivity
import com.globalspace.miljonsales.ui.add_details.competitorbrands.CompetitorBrandFragment
import com.globalspace.miljonsales.ui.add_details.consumptions.ConsumptionActivity
import com.globalspace.miljonsales.ui.add_details.consumptions.ConsumptionFragment
import com.globalspace.miljonsales.ui.add_details.facility.FacilityFragment
import com.globalspace.miljonsales.ui.add_details.kyc.KYCFragment
import com.globalspace.miljonsales.ui.add_details.molecule.MoleculeFragment
import com.globalspace.miljonsales.ui.add_details.purchaseauthority.PurchaseAuthorityActivity
import com.globalspace.miljonsales.ui.add_details.purchaseauthority.PurchaseAuthorityFragment
import com.globalspace.miljonsales.ui.add_details.speciality.SpecialityFragment
import com.globalspace.miljonsales.ui.add_details.stockist.StockistActivity
import com.globalspace.miljonsales.ui.add_details.stockist.StockistFragment
import com.globalspace.miljonsales.ui.add_details.strength.StrengthFragment
import com.globalspace.miljonsales.ui.add_details_dashboard.AddDetailsDashboardFragment
import com.globalspace.miljonsales.ui.add_details_dashboard.DashboardItemDetailsActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class,AppModule::class,NetworkModule::class,DatabaseModule::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : ApplicationComponent
    }

    fun getMap() : Map<Class<*>, ViewModel>
    fun inject(addDetailsFragment: AddDetailsDashboardFragment)
    fun inject(frag_add_details: AddDetailsFragment)
    fun inject(commentsObservationFragment: CommentsObservationFragment)
    fun inject(facilityFragment: FacilityFragment)
    fun inject(kycFragment: KYCFragment)
    fun inject(consumptionFragment: ConsumptionFragment)
    fun inject(competitorBrandFragment: CompetitorBrandFragment)
    fun inject(purchaseAuthorityFragment: PurchaseAuthorityFragment)
    fun inject(stockistFragment: StockistFragment)
    fun inject(specialityFragment: SpecialityFragment)
    fun inject(moleculeFragment: MoleculeFragment)
    fun inject(strengthFragment: StrengthFragment)
  //  fun inject(dialog: AddDetaillsDialog)
    fun inject(addDetailsActivity: AddDetailsActivity)
    fun inject(dashboardItemDetailsActivity: DashboardItemDetailsActivity)
    fun inject(consumptionActivity: ConsumptionActivity)
    fun inject(competitorBrandActivity: CompetitorBrandActivity)
    fun inject(purchaseAuthorityActivity: PurchaseAuthorityActivity)
    fun inject(stockistActivity: StockistActivity)
}
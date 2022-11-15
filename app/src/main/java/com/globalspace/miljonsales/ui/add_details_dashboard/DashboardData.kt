package com.globalspace.miljonsales.ui.add_details_dashboard

import androidx.room.Embedded
import androidx.room.Relation
import com.globalspace.miljonsales.local_db.entity.*
import java.io.Serializable

class DashboardData : Serializable {
    //parentColumn refers to Embedded User table's id column,
    @Embedded
    var hospitalDetails: FetchHospitalDetails? = null

    //entityColumn refers to fetchHospitalFacilityDetails table's hoapital_id (FetchHospitalDetails - FetchHospitalFacilityDetails relation) column,
    //entity refers to table(FetchHospitalFacilityDetails) which has relation with FetchHospitalDetails table.
    @Relation(parentColumn = "HOSPITAL_ID", entityColumn = "hoapital_id", entity = FetchHospitalFacilityDetails::class)
    var hospitalfacility: List<FetchHospitalFacilityDetails>? = null

    @Relation(parentColumn = "HOSPITAL_ID", entityColumn = "hoapital_id", entity = FetchHospitalSpecialityDetails::class)
    var fetchHospitalSpecialityDetails: List<FetchHospitalSpecialityDetails>? = null

    @Relation(parentColumn = "HOSPITAL_ID", entityColumn = "hoapital_id", entity = FetchHospitalStrengthDetails::class)
    var fetchHospitalStrengthDetails: List<FetchHospitalStrengthDetails>? = null

    @Relation(parentColumn = "HOSPITAL_ID", entityColumn = "hoapital_id", entity = FetchHospitalMoleculeDetails::class)
    var fetchHospitalMoleculeDetails: List<FetchHospitalMoleculeDetails>? = null

    @Relation(parentColumn = "HOSPITAL_ID", entityColumn = "hoapital_id", entity = FetchHospitalConsumptionDetails::class)
    var fetchHospitalConsumptionDetails: List<FetchHospitalConsumptionDetails>? = null

    @Relation(parentColumn = "HOSPITAL_ID", entityColumn = "hoapital_id", entity = FetchHospitalStockistDetails::class)
    var fetchHospitalStockistDetails: List<FetchHospitalStockistDetails>? = null

}
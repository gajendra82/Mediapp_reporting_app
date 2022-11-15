package com.globalspace.miljonsales.local_db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.globalspace.miljonsales.local_db.dao.DaoDB
import com.globalspace.miljonsales.local_db.entity.*
import com.globalspace.miljonsales.utils.Converters

@Database(
    entities = arrayOf(
        FetchGeography::class,
        FetchHospital::class,
        FetchHospitalFacility::class,
        FetchSpecialityTypes::class,
        FetchConsumptionTypes::class,
        FetchHospitalSummmary::class,
        FetchHospitalDetails::class,
        FetchHospitalFacilityDetails::class,
        FetchHospitalSpecialityDetails::class,
        FetchHospitalStrengthDetails::class,
        FetchHospitalMoleculeDetails::class,
        FetchHospitalConsumptionDetails::class,
        FetchHospitalStockistDetails::class,
    ), version = 1, exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun daoDb() : DaoDB
}
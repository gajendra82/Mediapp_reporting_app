package com.globalspace.miljonsales.di

import android.content.Context
import androidx.room.Room
import com.globalspace.miljonsales.local_db.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideReportingDB(context: Context) : AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java,"DB_Reporting.db").build()
    }
}
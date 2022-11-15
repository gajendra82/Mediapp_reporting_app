package com.globalspace.miljonsales.di

import com.globalspace.miljonsales.retrofit.ApiInterface
import com.globalspace.miljonsales.retrofit.ApiInterfaceNew
import com.globalspace.miljonsales.retrofit.ExampleInterceptor
import com.globalspace.miljonsales.retrofit.NullOnEmptyConverterFactory
import com.globalspace.miljonsales.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    val gson = GsonBuilder().setLenient().create();

    @Singleton
    @Provides
    fun provideOKHttpClient():OkHttpClient{
        return  OkHttpClient.Builder()
            .readTimeout(1,TimeUnit.MINUTES)
            .connectTimeout(1,TimeUnit.MINUTES)
            .build()

    }
    @Singleton
    @Provides
    fun provideGSON(): GsonConverterFactory {

        return  GsonConverterFactory.create(gson)

    }

    @Singleton
    @Provides
    fun providesRetrofit(gsonConverterFactory: GsonConverterFactory,okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL) // Dummy Base URL needed to create instance
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesAPI(retrofit: Retrofit) : ApiInterfaceNew {
        return retrofit.create(ApiInterfaceNew::class.java)
    }

}
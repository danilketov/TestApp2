package com.danilketov.testapp2.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://gitlab.65apps.com/"
    private var apiFactory: ApiFactory? = null


    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    fun getInstance(): ApiFactory? {
        if (apiFactory == null) {
            apiFactory = ApiFactory
        }
        return apiFactory
    }

    val getApiService = retrofit.create(ApiService::class.java)
}
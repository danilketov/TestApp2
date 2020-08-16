package com.danilketov.testapp2.network

import com.danilketov.testapp2.entity.Response
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("65gb/static/raw/master/testTask.json")
    fun getResponse(): Observable<Response>
}
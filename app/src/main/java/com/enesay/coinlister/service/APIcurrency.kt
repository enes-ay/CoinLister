package com.enesay.coinlister.service

import com.enesay.coinlister.model.Data
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers


interface APIcurrency {

    @Headers("YOUR_API_KEY")
    @GET("cryptocurrency/listings/latest")
    fun getCoins(): Single<com.enesay.coinlister.model.Response>



}
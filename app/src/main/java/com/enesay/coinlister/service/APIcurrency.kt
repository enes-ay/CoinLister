package com.enesay.coinlister.service

import com.enesay.coinlister.model.Data
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers


interface APIcurrency {

    @Headers("X-CMC_PRO_API_KEY:d3a4fb0d-987b-4c4c-8197-ad090828f9c2")
    @GET("cryptocurrency/listings/latest")
    fun getCoins(): Single<com.enesay.coinlister.model.Response>



}
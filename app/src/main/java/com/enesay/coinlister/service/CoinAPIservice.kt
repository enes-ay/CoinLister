package com.enesay.coinlister.service

import com.enesay.coinlister.model.Data
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CoinAPIservice {

    // base url

    private val BASE_URL="https://pro-api.coinmarketcap.com/v1/"


    private val api=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(APIcurrency::class.java)

    fun getData(): Single<com.enesay.coinlister.model.Response> {

        return  api.getCoins()
    }


}
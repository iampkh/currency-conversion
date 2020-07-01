package com.app.currencyconversion.model.remote

import com.app.currencyconversion.model.data.Currency
import com.app.currencyconversion.model.data.LiveQuotes
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface RemoteAPIService {

    @GET(RetrofitBuilder.LIVEQUOTE)
    suspend fun getLiveCurrencyData():Response<LiveQuotes>
}
package com.app.currencyconversion.model.remote

import com.app.currencyconversion.model.data.LiveQuotes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "http://api.currencylayer.com/"
    const val API_KEY = "ca2712b036190d4b9f40bcc4bd1deb7e"
    const val LIVEQUOTE = "live?access_key="+ API_KEY



    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(createGsonConverter())
            .build()
    }

    private fun createGsonConverter(): Converter.Factory{
        var gsonBuilder:GsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(LiveQuotes::class.java,LiveQuotesJsonDeserializer())
        var gson:Gson = gsonBuilder.create()
        return GsonConverterFactory.create(gson)
    }

    val apiService: RemoteAPIService = getRetrofit().create(RemoteAPIService::class.java)

}
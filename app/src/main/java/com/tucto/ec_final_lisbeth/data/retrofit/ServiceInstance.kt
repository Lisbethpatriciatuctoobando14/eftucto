package com.tucto.ec_final_lisbeth.data.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://swapi.dev/api/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getMichiService(): ApiService = retrofit.create(ApiService::class.java)
}
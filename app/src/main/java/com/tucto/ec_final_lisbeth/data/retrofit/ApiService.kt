package com.tucto.ec_final_lisbeth.data.retrofit

import com.tucto.ec_final_lisbeth.data.response.ApiListResponse
import retrofit2.http.GET

interface ApiService {
    @GET("people/")
    suspend fun getCupons(): ApiListResponse

}
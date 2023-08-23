package com.tucto.ec_final_lisbeth.data.repository

import com.tucto.ec_final_lisbeth.data.db.ApiDao
import com.tucto.ec_final_lisbeth.data.response.ApiListResponse
import com.tucto.ec_final_lisbeth.data.response.ApiResponse
import com.tucto.ec_final_lisbeth.data.retrofit.ServiceInstance
import com.tucto.ec_final_lisbeth.model.Api

class ApisRepository (val apiDao: ApiDao? = null){
    suspend fun getCupons(): ApiResponse<ApiListResponse> {
        return try {
            val response = ServiceInstance.getMichiService().getCupons()
            ApiResponse.Success(response)
        }catch (e: Exception){
            ApiResponse.Error(e)
        }
    }
    suspend fun addFavorite(api: Api) {
        apiDao?.let {
            it.addFavorite(api)
        }
    }

    suspend fun removeFavorite(api: Api) {
        apiDao?.let {
            it.removeFavorite(api)
        }
    }
    fun getFavorites(): List<Api> {
        apiDao?.let {
            return it.getFavorites()
        } ?: kotlin.run {
            return listOf()
        }
    }
}
package com.tucto.ec_final_lisbeth.view.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tucto.ec_final_lisbeth.data.db.ApiDataBase
import com.tucto.ec_final_lisbeth.data.repository.ApisRepository
import com.tucto.ec_final_lisbeth.model.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApiDetailViewModel (application: Application) : AndroidViewModel(application){
    private val repository: ApisRepository
    private val favoriteApi: MutableSet<String> = mutableSetOf()

    init {
        val db = ApiDataBase.getDatabase(application)
        repository = ApisRepository(db.apiDao())
        // Llena el conjunto de favoritos al inicio
        viewModelScope.launch(Dispatchers.IO) {
            val favorites = repository.getFavorites()
            favoriteApi.addAll(favorites.map { it.name })
        }
    }

    fun addFavorite(api: Api) {
        if (!favoriteApi.contains(api.name)) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addFavorite(api)
                favoriteApi.add(api.name)
            }
        }
    }

    fun removeFavorite(api: Api) {
        if (favoriteApi.contains(api.name)) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.removeFavorite(api)
                favoriteApi.remove(api.name)
            }
        }
    }

    fun isFavorite(api: Api): Boolean {
        return api.name in favoriteApi
    }

}
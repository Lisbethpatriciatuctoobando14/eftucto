package com.tucto.ec_final_lisbeth.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.tucto.ec_final_lisbeth.model.Api
import androidx.room.*

@Dao
interface ApiDao {

    @Query("SELECT * FROM api")
    fun getFavorites(): List<Api>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(api: Api)

    @Delete
    suspend fun removeFavorite(api: Api)
}
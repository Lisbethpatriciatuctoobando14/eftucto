package com.tucto.ec_final_lisbeth.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "api")
@Parcelize
data class Api(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val gender: String,
    val height: String,
    val mass: String,
    val hair_color: String,
    val skin_color: String,
    var isFavorite: Boolean = false
):Parcelable
fun getData(): List<Api> =

    listOf(
        Api(1,"", "Nike cupon", "Nike Store", "50", "Obten un 50% de descuento por dia del padre.", "24 June 2023",false)

    )
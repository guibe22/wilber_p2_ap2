package com.example.wilber_p2_ap2.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "")
data class gastosDto (
    @PrimaryKey
    @Json(name = "idGasto")
    val idGasto : Int? = null,
    @Json(name = "fecha")
    val fecha: String,
    @Json(name = "idSuplidor")
    val idSuplidor : Int? = null,
    @Json(name = "suplidor")
    val suplidor : String?,
    @Json(name="concepto")
    val concepto: String,
    @Json(name="ncf")
    val nfc: Int,
    @Json(name="itbis")
    val itbis: Int,
    @Json(name="monto")
    val monto: Int,
    )
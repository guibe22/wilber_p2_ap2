package com.example.wilber_p2_ap2.data

import com.example.wilber_p2_ap2.data.remote.dto.gastosDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface gastosApi{
    @GET("/api/gastos")
    suspend fun getGastos(): List<gastosDto>

    @GET("/api/gastos/{id}")
    suspend fun getGastosId(@Path("id") id: Int): gastosDto

    @POST("/api/gastos")
    suspend fun postGastos(@Body gastos: gastosDto): Response<gastosDto>

    @DELETE("/api/gastos/{id}")
    suspend fun deleteGastos(@Path("id") id: Int): Response<Unit>
    @PUT("/api/gastos/{id}")
    suspend fun putGastos(@Path("id") id: Int): Response<Unit>
}
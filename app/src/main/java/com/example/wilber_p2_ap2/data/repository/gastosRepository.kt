package com.example.wilber_p2_ap2.data.repository

import com.example.wilber_p2_ap2.data.gastosApi
import com.example.wilber_p2_ap2.data.remote.dto.gastosDto
import com.example.wilber_p2_ap2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class gastosRepository @Inject constructor(
    private val api: gastosApi
) {
   fun getGastos(): Flow<Resource<List<gastosDto>>> = flow {
        try {
            emit(Resource.Loading())

            val gastos = api.getGastos()

            emit(Resource.Success(gastos))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    fun getGastosId(id: Int): Flow<Resource<gastosDto>> = flow {
        try {
            emit(Resource.Loading())
            val gastos = api.getGastosId(id)

            emit(Resource.Success(gastos))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }


    suspend fun deleteGastos(id: Int) {
        api.deleteGastos(id)
    }
     suspend fun postGastos(gastosDto: gastosDto) {
        api.postGastos(gastosDto)
    }
    suspend fun putGastos(id: Int,gastosDto: gastosDto) {
        api.putGastos(id, gastosDto)
    }


}
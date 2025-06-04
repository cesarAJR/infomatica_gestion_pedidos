package com.cesar.data.datasource.remote.api

import com.cesar.data.core.RetrofitResponse
import com.cesar.data.remote.Methods
import com.cesar.data.remote.model.Mapper
import com.cesar.data.remote.model.Mapper.toListOrder
import com.cesar.data.remote.model.Mapper.toRequest
import com.cesar.data.remote.model.OrderResponse
import com.cesar.domain.model.Order

class OrderRemoteDataSourceImp(private val methods: Methods)  : OrderRemoteDataSource{
    override suspend fun getListOrder(): Result<List<Order>> {
        val response =   RetrofitResponse<List<OrderResponse>>().getResult(methods.getListOrder())
        return if (response.isSuccess){
            response.getOrNull()?.let {
                Result.success(it.toListOrder())
            }?:run {Result.failure(Exception("ocurrio con la data"))  }
        }else{
            Result.failure(Exception(response.exceptionOrNull()?.localizedMessage?:""))
        }
    }

    override suspend fun addOrder(order: Order): Result<Boolean> {
        val response =   RetrofitResponse<OrderResponse>().getResult(methods.addOrder(order.toRequest()))
        return if (response.isSuccess){
            Result.success(true)
        }else{
            Result.failure(Exception(response.exceptionOrNull()?.localizedMessage?:""))
        }
    }

    override suspend fun editOrder(order: Order): Result<Boolean> {
        val response =   RetrofitResponse<OrderResponse>().getResult(methods.editOrder(order.id?:"",order.toRequest()))
        return if (response.isSuccess){
            Result.success(true)
        }else{
            Result.failure(Exception(response.exceptionOrNull()?.localizedMessage?:""))
        }
    }

    override suspend fun deleteOrder(id: String): Result<Boolean> {
        val response =   RetrofitResponse<OrderResponse>().getResult(methods.deleteOrder(id))
        return if (response.isSuccess){
            Result.success(true)
        }else{
            Result.failure(Exception(response.exceptionOrNull()?.localizedMessage?:""))
        }
    }
}
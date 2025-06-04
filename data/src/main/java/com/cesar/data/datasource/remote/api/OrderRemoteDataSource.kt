package com.cesar.data.datasource.remote.api

import com.cesar.domain.model.Order

interface OrderRemoteDataSource {
    suspend fun getListOrder(): Result<List<Order>>
    suspend fun addOrder(order:Order): Result<Boolean>
    suspend fun editOrder(order:Order): Result<Boolean>
    suspend fun deleteOrder(id:String): Result<Boolean>
}
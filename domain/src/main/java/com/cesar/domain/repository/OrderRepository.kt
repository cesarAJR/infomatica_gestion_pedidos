package com.cesar.domain.repository

import com.cesar.domain.core.Result
import com.cesar.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun getListOrder(): Flow<Result<List<Order>>>
    suspend fun getListPendingOrder(): Flow<Result<List<Order>>>
    suspend fun addOrder(order: Order): Flow<Result<Boolean>>
    suspend fun deleteOrder(order: Order): Flow<Result<Boolean>>
    suspend fun deleteOrderPending(order: Order): Flow<Result<Boolean>>
    suspend fun sendOrderPending(order: Order): Flow<Result<Boolean>>
    suspend fun editOrder(order: Order): Flow<Result<Boolean>>
}
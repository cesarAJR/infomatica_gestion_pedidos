package com.cesar.data.datasource.local

import com.cesar.data.database.model.OrderEntity
import kotlinx.coroutines.flow.Flow

interface OrderLocalDataSource {
    fun insertOrder(order:OrderEntity)
    fun deleteOrder(order:OrderEntity)
    fun editOrder(order:OrderEntity)
    suspend fun getListOrder():List<OrderEntity>
}
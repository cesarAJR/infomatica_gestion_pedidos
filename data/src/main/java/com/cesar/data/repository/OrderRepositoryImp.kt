package com.cesar.data.repository

import android.content.Context

import com.cesar.data.database.model.Mapper.toListOrder
import com.cesar.data.database.model.Mapper.toOrderEntity
import com.cesar.data.database.model.Mapper.toSaveOrderEntity
import com.cesar.data.datasource.local.OrderLocalDataSource
import com.cesar.data.datasource.remote.api.OrderRemoteDataSource
import com.cesar.data.util.NetworkUtil
import com.cesar.domain.core.Result
import com.cesar.domain.model.Order
import com.cesar.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class OrderRepositoryImp(
    private val orderRemoteDataSource: OrderRemoteDataSource,
    private val orderLocalDataSource: OrderLocalDataSource,
    private val context: Context
) : OrderRepository {
    override suspend fun getListOrder(): Flow<Result<List<Order>>> = flow{
        if(NetworkUtil.isConnected(context)){
            val response = orderRemoteDataSource.getListOrder()
            val dataRemote =  response.getOrNull()
            val responseLocal = orderLocalDataSource.getListOrder().toListOrder()
            val data = dataRemote?.plus(responseLocal)
            emit(Result.SuccessFull(data))
        }else{
            emit(Result.SuccessFull(orderLocalDataSource.getListOrder().toListOrder()))
        }
    }

    override suspend fun getListPendingOrder(): Flow<Result<List<Order>>> = flow {
        emit(Result.SuccessFull(orderLocalDataSource.getListOrder().toListOrder()))
    }

    override suspend fun addOrder(order: Order): Flow<Result<Boolean>> = flow{
        if(NetworkUtil.isConnected(context)){
            val response = orderRemoteDataSource.addOrder(order)
            if(response.isSuccess) emit(Result.SuccessFull(true)) else emit(Result.Error("hubo un error"))
        }else{
            orderLocalDataSource.insertOrder(order.toSaveOrderEntity())
            emit(Result.SuccessFull(true))
        }
    }

    override suspend fun deleteOrder(order: Order): Flow<Result<Boolean>> = flow{
        if (order.isPending==true) {
            orderLocalDataSource.deleteOrder(order.toOrderEntity())
            emit(Result.SuccessFull(true))
        }else{
            val response = orderRemoteDataSource.deleteOrder(order.id?:"")
            if(response.isSuccess) emit(Result.SuccessFull(true)) else emit(Result.Error("hubo un error"))
        }

    }

    override suspend fun deleteOrderPending(order: Order): Flow<Result<Boolean>> = flow{
        orderLocalDataSource.deleteOrder(order.toOrderEntity())
        emit(Result.SuccessFull(true))
    }

    override suspend fun sendOrderPending(order: Order): Flow<Result<Boolean>> = flow {
        val response = orderRemoteDataSource.addOrder(order)
        if(response.isSuccess) emit(Result.SuccessFull(true)) else emit(Result.Error("hubo un error"))
    }

    override suspend fun editOrder(order: Order): Flow<Result<Boolean>> = flow{
        if (order.isPending==true) {
            orderLocalDataSource.editOrder(order.toOrderEntity())
            emit(Result.SuccessFull(true))
        }else{
            val response = orderRemoteDataSource.editOrder(order)
            if(response.isSuccess) emit(Result.SuccessFull(true)) else emit(Result.Error("hubo un error"))
        }
    }
}
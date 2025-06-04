package com.cesar.data.datasource.local

import com.cesar.data.database.dao.OrderDao
import com.cesar.data.database.model.OrderEntity


class OrderLocalDataSourceImp(private val orderDao :OrderDao) : OrderLocalDataSource {
    override fun insertOrder(order: OrderEntity) {
        orderDao.insert(order)
    }

    override fun deleteOrder(order: OrderEntity) {
       orderDao.deleteOrder(order)
    }

    override fun editOrder(order: OrderEntity) {
        orderDao.editOrder(order)
    }

    override suspend fun getListOrder(): List<OrderEntity> {
        return orderDao.getListOrder()
    }
}
package com.cesar.data.database.model

import com.cesar.domain.model.Order
import java.util.UUID
import kotlin.math.truncate

object Mapper {

    fun Order.toSaveOrderEntity():OrderEntity{
        return OrderEntity(
            product = this.product,
            client = this.client,
            amount = this.amount,
            date = this.date,
            pendingSave = true,
            id = UUID.randomUUID().toString()
        )
    }

    fun Order.toOrderEntity():OrderEntity{
        return OrderEntity(
            product = this.product,
            client = this.client,
            amount = this.amount,
            date = this.date,
            pendingSave = this.isPending?:false,
            id = this.id?:""
        )
    }

    fun List<OrderEntity>.toListOrder():List<Order>{
        return map {it->
            Order(
                id = it.id,
                client = it.client,
                product  = it.product,
                amount = it.amount,
                date = it.date,
                isPending = it.pendingSave
            )
        }
    }
}
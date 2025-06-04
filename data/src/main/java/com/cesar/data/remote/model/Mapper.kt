package com.cesar.data.remote.model

import com.cesar.domain.model.Order

object Mapper {

    fun List<OrderResponse>.toListOrder():List<Order>{
        return map {it->
            Order(
                id = it.id,
                product = it.product,
                client = it.client,
                imageUrl = it.imageUrl,
                amount = it.amount,
                date = it.date
            )
        }
    }

    fun Order.toRequest():OrderRequest{
        return OrderRequest(
            product = this.product,
            client = this.client,
            amount = this.amount,
            date = this.date,
            imageUrl = this.imageUrl?:"",
        )
    }
}
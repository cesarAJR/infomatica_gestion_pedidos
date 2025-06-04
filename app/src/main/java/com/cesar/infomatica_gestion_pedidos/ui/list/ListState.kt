package com.cesar.infomatica_gestion_pedidos.ui.list

import com.cesar.domain.model.Order

data class ListState(
    val list : List<Order>?=null,
    val listFilter : List<Order>?=null,
    val isLoading:Boolean?=null,
    val isRefreshing: Boolean = false
)
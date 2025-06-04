package com.cesar.infomatica_gestion_pedidos.ui.list

import com.cesar.domain.model.Order

sealed class ListUiState  {
    data class ListSuccess(val list: List<Order>?): ListUiState()
    data object DeleteSuccess : ListUiState()
    data class Error(val message: String): ListUiState()
    data object Loading:ListUiState()
    data object Nothing:ListUiState()
}
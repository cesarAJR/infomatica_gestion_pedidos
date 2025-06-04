package com.cesar.infomatica_gestion_pedidos.ui.add

import com.cesar.domain.model.Order

sealed class AddUiState  {
    data object AddSuccess : AddUiState()
    data class Error(val message: String): AddUiState()
    data object Loading:AddUiState()
    data object Nothing:AddUiState()
}
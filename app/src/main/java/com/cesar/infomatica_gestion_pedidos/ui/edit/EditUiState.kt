package com.cesar.infomatica_gestion_pedidos.ui.edit

sealed class EditUiState  {
    data object EditSuccess : EditUiState()
    data class Error(val message: String): EditUiState()
    data object Loading:EditUiState()
    data object Nothing:EditUiState()
}
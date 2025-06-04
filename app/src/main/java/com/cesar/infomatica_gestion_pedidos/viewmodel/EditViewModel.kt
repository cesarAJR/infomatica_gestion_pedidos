package com.cesar.infomatica_gestion_pedidos.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.domain.model.Order
import com.cesar.domain.useCase.edit.EditOrderUseCase
import com.cesar.infomatica_gestion_pedidos.ui.add.AddElements
import com.cesar.infomatica_gestion_pedidos.ui.add.AddState
import com.cesar.infomatica_gestion_pedidos.ui.add.AddUiState
import com.cesar.infomatica_gestion_pedidos.ui.edit.EditElements
import com.cesar.infomatica_gestion_pedidos.ui.edit.EditState
import com.cesar.infomatica_gestion_pedidos.ui.edit.EditUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditViewModel(val editUseCase : EditOrderUseCase) : ViewModel() {
    var stateElements by mutableStateOf(EditElements())

    private val _uiState = MutableStateFlow<EditUiState>(EditUiState.Nothing)
    val uiState : StateFlow<EditUiState> = _uiState
    var state by mutableStateOf(EditState())
    private var order : Order?=null

    fun editOrder(){
        if (validateForm()){
            _uiState.value = EditUiState.Loading
            viewModelScope.launch(Dispatchers.IO) {
                order?.client = stateElements.client!!
                order?.product = stateElements.product!!
                order?.amount = stateElements.amount!!.toInt()
                order?.date = stateElements.date!!
                editUseCase(order!!).collect{ r->
                    if (r.message!=null)
                        _uiState.value = EditUiState.Error(r.message?:"")
                    else _uiState.value = EditUiState.EditSuccess
                }
            }
        }else{
            _uiState.value = EditUiState.Error("Complete los campos")
        }
    }

    private fun validateForm(): Boolean{
        if (stateElements.client.isNullOrEmpty()==true) return false
        if (stateElements.product.isNullOrEmpty()==true) return false
        if (stateElements.amount.isNullOrEmpty()==true) return false
        if (stateElements.date.isNullOrEmpty()==true) return false
        return true
    }

    fun changeClient(client:String){
        stateElements= stateElements.copy(client = client)
    }

    fun changeProduct(product:String){
        stateElements= stateElements.copy(product = product)
    }

    fun changeAmount(amount:String){
        stateElements= stateElements.copy(amount = amount)
    }

    fun changeDate(date:String){
        stateElements= stateElements.copy(date = date)
    }

    fun setOrder(order:Order){
        this.order = order
        stateElements = stateElements.copy(client = order.client, product = order.product, amount = order.amount.toString(), date = order.date)
    }
}
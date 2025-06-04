package com.cesar.infomatica_gestion_pedidos.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.domain.model.Order
import com.cesar.domain.useCase.add.AddOrderUseCase
import com.cesar.infomatica_gestion_pedidos.R
import com.cesar.infomatica_gestion_pedidos.ui.add.AddElements
import com.cesar.infomatica_gestion_pedidos.ui.add.AddState
import com.cesar.infomatica_gestion_pedidos.ui.add.AddUiState
import com.cesar.infomatica_gestion_pedidos.ui.list.ListState
import com.cesar.infomatica_gestion_pedidos.ui.list.ListUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddViewModel(val addUseCase:AddOrderUseCase) : ViewModel() {
    var stateElements by mutableStateOf(AddElements())

    private val _uiState = MutableStateFlow<AddUiState>(AddUiState.Nothing)
    val uiState : StateFlow<AddUiState> = _uiState
    var state by mutableStateOf(AddState())

    fun addOrder(){
        if (validateForm()){
            _uiState.value = AddUiState.Loading
            viewModelScope.launch(Dispatchers.IO) {
                addUseCase(Order(client = stateElements.client?:"", product = stateElements.product?:"", amount = stateElements.amount?.toInt()?:0, date = stateElements.date?:"")).collect{ r->
                    if (r.message!=null)
                        _uiState.value = AddUiState.Error(r.message?:"")
                    else _uiState.value = AddUiState.AddSuccess
                }
            }
        }else{
            _uiState.value = AddUiState.Error("Complete los campos")
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
}
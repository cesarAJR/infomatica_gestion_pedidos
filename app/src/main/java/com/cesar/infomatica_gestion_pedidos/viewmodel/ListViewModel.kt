package com.cesar.infomatica_gestion_pedidos.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.domain.model.Order
import com.cesar.domain.useCase.delete.DeleteOrderUseCase
import com.cesar.domain.useCase.list.ListOderUseCase
import com.cesar.infomatica_gestion_pedidos.ui.add.AddElements
import com.cesar.infomatica_gestion_pedidos.ui.list.ListElements
import com.cesar.infomatica_gestion_pedidos.ui.list.ListState
import com.cesar.infomatica_gestion_pedidos.ui.list.ListUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class ListViewModel(val listOrderUseCase:ListOderUseCase,val deleteOrderUseCase: DeleteOrderUseCase):ViewModel() {
    var stateElements by mutableStateOf(ListElements())

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Nothing)
    val uiState : StateFlow<ListUiState> = _uiState
    var state by mutableStateOf(ListState())

     fun getListOrder(){
         _uiState.value = ListUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            listOrderUseCase().collect{ r->
                if (r.message!=null)
                    _uiState.value = ListUiState.Error(r.message?:"")
                else _uiState.value = ListUiState.ListSuccess(r.data?: listOf())
            }
        }
    }

     fun deleteOrder(order: Order){
         _uiState.value = ListUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            deleteOrderUseCase(order).collect{ r->
                if (r.message!=null)
                    _uiState.value = ListUiState.Error(r.message?:"")
                else _uiState.value = ListUiState.DeleteSuccess
            }
        }
    }

    fun changeSearch(search:String){
        stateElements = stateElements.copy(search=search)
        var results : MutableList<Order> = mutableListOf()
        if (search.isEmpty()){
            results = state.list as MutableList<Order>
        }else{
            results = state.list?.filter { v->
                v.client.lowercase().contains(search.lowercase())
            }?.toMutableList() ?: mutableListOf()
        }

        state = state.copy(listFilter = results)

    }

    fun cleanSearch(){
        stateElements = stateElements.copy(search="")
    }
}
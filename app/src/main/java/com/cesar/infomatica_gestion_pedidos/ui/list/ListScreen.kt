package com.cesar.infomatica_gestion_pedidos.ui.list


import android.provider.CalendarContract.Colors
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cesar.domain.model.Order
import com.cesar.infomatica_gestion_pedidos.R
import com.cesar.infomatica_gestion_pedidos.ui.theme.BackgroundPending
import com.cesar.infomatica_gestion_pedidos.ui.theme.ColorBlack
import com.cesar.infomatica_gestion_pedidos.ui.theme.ColorWhite
import com.cesar.infomatica_gestion_pedidos.ui.theme.PrimaryLight
import com.cesar.infomatica_gestion_pedidos.viewmodel.ListViewModel
import com.example.prueba_softtek.component.DialogLoading
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(viewModel: ListViewModel= koinViewModel(),onAddOrder:()->Unit,onEditOrder:(Order)->Unit) {
    val stateElements = viewModel.stateElements
    val state = viewModel.state
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember{ mutableStateOf(false) }
    var orderSelect by remember{ mutableStateOf(Order("","","",2,"","",false)) }
    val ptrState= rememberPullToRefreshState()

    LaunchedEffect( true){
        viewModel.getListOrder()
    }

    LaunchedEffect( Unit){
        viewModel.uiState.collect{
            when(it){
                is ListUiState.DeleteSuccess -> {
                    viewModel.state = viewModel.state.copy(isLoading = false)
                    viewModel.cleanSearch()
                    viewModel.getListOrder()
                }
                is ListUiState.Error ->{
                    viewModel.state = viewModel.state.copy(isLoading = false)
                }
                is ListUiState.ListSuccess ->{
                    viewModel.state = viewModel.state.copy(list = it.list, listFilter = it.list, isLoading = false)
                }
                ListUiState.Loading -> {
                    viewModel.state = viewModel.state.copy(isLoading = true)
                }
                is ListUiState.Nothing -> {}
            }
        }
    }

    Scaffold(
        topBar ={
            TopAppBar(
                navigationIcon = {

                },
                title = {
                    Text(text = stringResource(R.string.orders))
                },
                actions = {
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddOrder()
                    viewModel.cleanSearch()
                },
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {


        Surface(modifier = Modifier
            .padding(top = it.calculateTopPadding())
            .fillMaxSize()
        ) {
            if (state.isLoading==true){
                DialogLoading(true)
            }else{
                Column () {
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        value = stateElements.search?:"",
                        colors = TextFieldDefaults.colors().copy(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        onValueChange = {
                            viewModel.changeSearch(it)
                        },
                        placeholder = {
                            Text(stringResource(R.string.client))
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    PullToRefreshBox(
                        isRefreshing = state.isRefreshing,
                        onRefresh = {
                            viewModel.cleanSearch()
                            viewModel.getListOrder()
                        },
                    ) {
                        LazyColumn(
                            contentPadding = PaddingValues(8.dp),
                        ) {
                            state.listFilter?.let { orders ->
                                items(orders){order->
                                    ItemOrder(order,{item->
                                        orderSelect = item
                                        showBottomSheet = true
                                    },{item->
                                        onEditOrder(item)
                                        viewModel.cleanSearch()
                                    })
                                }
                            }
                        }
                    }

                }
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(horizontalArrangement = Arrangement.Center) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = stringResource(R.string.question_delete),
                                fontSize = 18.sp,
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row {
                                Button(onClick = {
                                    viewModel.deleteOrder(orderSelect)
                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            showBottomSheet = false
                                        }
                                    }
                                }
                                ) {
                                    Text(stringResource(R.string.yes))
                                }
                            Spacer(modifier = Modifier.width(20.dp))
                            Button(onClick = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheet = false
                                    }
                                }
                            }) {
                                Text(stringResource(R.string.no))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemOrder(
    order: Order,
    onDelete:(Order)->Unit,
    onEdit:(Order)->Unit,
) {
    Card(
        border = BorderStroke(1.dp, Color.Black),

        colors = CardDefaults.cardColors(
            containerColor = if(order.isPending==true) BackgroundPending  else ColorWhite
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onEdit(order)
            }
    ) {
        Column(Modifier.padding(10.dp)) {
            Row {
                Text(
                    text = stringResource(R.string.client) + " :",
                    fontSize = 18.sp,
                    color = ColorBlack
                )
                Text(
                    text = order.client,
                    fontSize = 18.sp,
                    color =  ColorBlack
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Text(
                    text = stringResource(R.string.product) + " :",
                    fontSize = 18.sp,
                    color =  ColorBlack
                )
                Text(
                    text = order.product,
                    fontSize = 18.sp,
                    color =  ColorBlack
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Text(
                    text = stringResource(R.string.amount) + " :",
                    fontSize = 18.sp,
                    color =  ColorBlack
                )
                Text(
                    text = order.amount.toString(),
                    fontSize = 18.sp,
                    color =  ColorBlack
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Text(
                    text = stringResource(R.string.date) + " :",
                    fontSize = 18.sp,
                    color = ColorBlack
                )
                Text(
                    text = order.date,
                    fontSize = 18.sp,
                    color =  ColorBlack
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(modifier = Modifier
                    .height(45.dp)
                    .padding(horizontal = 30.dp),
                    shape = RoundedCornerShape(10),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    onClick = {
                        onDelete(order)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.delete) ,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    )
                }
            }

        }

    }
    Spacer(modifier = Modifier.height(10.dp))
}
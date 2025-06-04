package com.cesar.infomatica_gestion_pedidos.ui.edit

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.cesar.domain.model.Order
import com.cesar.infomatica_gestion_pedidos.R
import com.cesar.infomatica_gestion_pedidos.ui.add.AddUiState
import com.cesar.infomatica_gestion_pedidos.viewmodel.AddViewModel
import com.cesar.infomatica_gestion_pedidos.viewmodel.EditViewModel
import com.example.prueba_softtek.component.DialogLoading
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(viewModel: EditViewModel = koinViewModel(), onBack:()->Unit, order: Order) {
    val stateElements = viewModel.stateElements
    val state = viewModel.state
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    LaunchedEffect( true){
       viewModel.setOrder(order)
    }


    LaunchedEffect( Unit){
        viewModel.uiState.collect{
            when(it){
                is EditUiState.EditSuccess -> {
                    viewModel.state = viewModel.state.copy(isLoading = false)
                    Toast.makeText(context,R.string.message_success_edit,Toast.LENGTH_LONG).show()
                    onBack()
                }
                is EditUiState.Error -> {
                    viewModel.state = viewModel.state.copy(isLoading = false)
                    Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                }
                is EditUiState.Loading -> {
                    viewModel.state = viewModel.state.copy(isLoading = true)
                }
                is EditUiState.Nothing -> {}
            }
        }
    }



    if (state.isLoading==true) {
        DialogLoading(true)
    }

    if (showDatePicker) {

        DatePickerModal(
            onDateSelected = {
                selectedDate = convertMillisToDate(it!!)
                viewModel.changeDate(selectedDate)
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

    Scaffold(
        topBar ={
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        onBack()
                    }) {
                        Icon(Icons.Filled.ArrowBack,"")
                    }
                },
                title = {
                    Text(text = stringResource(R.string.edit_order))
                },
                actions = {

                }
            )
        }
    ) {
        Surface(modifier = Modifier
            .padding(top = it.calculateTopPadding())
            .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = stateElements.client?:"",
                    onValueChange = {
                        viewModel.changeClient(it)
                    },
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.client)) }
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = stateElements.product?:"",
                    onValueChange = {
                        viewModel.changeProduct(it)
                    },
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.product)) }
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = stateElements.amount?:"",
                    onValueChange = {
                        if (it.isDigitsOnly())  viewModel.changeAmount(it)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.amount)) }
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = stateElements.date?:"",
                    readOnly = true,
                    onValueChange = {

                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                showDatePicker = !showDatePicker
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = ""
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    label = { Text(text =stringResource(R.string.date)) }
                )
                Spacer(modifier = Modifier.height(50.dp))
                Button(modifier = Modifier
                    .height(45.dp)
                    .padding(horizontal = 30.dp),
                    shape = RoundedCornerShape(10),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    onClick = {
                        viewModel.editOrder()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.edit),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
package com.cesar.infomatica_gestion_pedidos.di

import com.cesar.infomatica_gestion_pedidos.viewmodel.AddViewModel
import com.cesar.infomatica_gestion_pedidos.viewmodel.EditViewModel
import com.cesar.infomatica_gestion_pedidos.viewmodel.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ListViewModel(get(),get()) }
    viewModel { AddViewModel(get()) }
    viewModel { EditViewModel(get()) }


}

package com.cesar.infomatica_gestion_pedidos.ui.navigation

sealed class Screen(val route:String) {

    object List : Screen("list_screen")
    object Add : Screen("add_screen")
    object Edit : Screen("edit_screen/?order={order}"){
        fun createRoute(order:String) = "edit_screen/?order=$order"
    }
}
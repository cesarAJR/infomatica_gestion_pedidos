package com.cesar.infomatica_gestion_pedidos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cesar.domain.model.Order
import com.cesar.infomatica_gestion_pedidos.ui.add.AddScreen
import com.cesar.infomatica_gestion_pedidos.ui.edit.EditScreen
import com.cesar.infomatica_gestion_pedidos.ui.list.ListScreen
import com.google.gson.Gson

@Composable
fun SetupNavGraph(navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = Screen.List.route
    ){
        composable(route = Screen.List.route) {
            ListScreen(onAddOrder = {
                navController.navigate(Screen.Add.route)
            },
                onEditOrder = {
                    val orderJson =  Gson().toJson(it)
                    navController.navigate(Screen.Edit.createRoute(orderJson))
                })
        }

        composable(route = Screen.Add.route) {
            AddScreen(onBack = {
                navController.popBackStack()
            })
        }

        composable(route = Screen.Edit.route,
            arguments = listOf(
                navArgument("order"){defaultValue = "" },
            )
        ) {backStackEntry->
            val orderJson = backStackEntry.arguments?.getString("order")
            val order =  Gson().fromJson(orderJson, Order::class.java)
            requireNotNull(order)
            EditScreen (onBack = {
                navController.popBackStack()
            }, order = order)
        }
    }
}
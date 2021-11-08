package com.hachitovhummus.androidapp.ui

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.hachitovhummus.androidapp.ui.screens.*
import com.hachitovhummus.androidapp.ui.theme.HachiTovHummusTheme

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun HummusApp(activity: Activity) {
    ProvideWindowInsets { // draws app above bottom system bar
        HachiTovHummusTheme(){
            var orderListViewModel = OrderListViewModel()
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "welcomeScreen"){
                composable("welcomeScreen"){
                    WelcomeScreen(
                        onClickNewOrder = {navController.navigate("orderAssemblyScreen")},
                        onClickTakeOut = {orderListViewModel.createOrderCollection(true); navController.navigate("orderAssemblyScreen")},
                        orderListViewModel
                    )
                }

                composable(route = "orderAssemblyScreen"){
                    OrderAssemblyScreen(
                        onClickFinishedOrder = {orderListViewModel.addOrder(); navController.navigate("ordersScreen")},
                        orderViewModel = orderListViewModel.currentOrder.value!!
                    )
                }

                composable(route = "ordersScreen"){
                    OrdersScreen(
                        onClickNewOrder = {orderListViewModel.setInEditMode(false); navController.navigate("orderAssemblyScreen")},
                        onClickEditOrder = {orderListViewModel.setInEditMode(true); orderListViewModel.editOrder(); navController.navigate("orderAssemblyScreen")},
                        orderListViewModel = orderListViewModel,
                        restartApp = { orderListViewModel = OrderListViewModel(); navController.navigate("welcomeScreen"); restartApp(activity)},
                    )
                }
            }
        }
    }
}

fun restartApp(context: Activity) {
    val intent = Intent(context, context::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
    if (context is Activity) {
        (context as Activity).finish()
    }
    Runtime.getRuntime().exit(0)
}
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
        HachiTovHummusTheme{
            lateinit var orderListViewModel: OrderListViewModel
            val navController = rememberNavController()

            fun goToNextScreen(takeOut: Boolean){
                orderListViewModel = OrderListViewModel(takeOut)
                navController.navigate("orderAssemblyScreen")
            }

            NavHost(navController = navController, startDestination = "welcomeScreen"){
                composable("welcomeScreen"){
                    WelcomeScreen(
                        onClickNewOrder = {goToNextScreen(false)},
                        onClickTakeOut = {goToNextScreen(true)},
                    )
                }

                composable(route = "orderAssemblyScreen"){
                    OrderAssemblyScreen(
                        onClickFinishedOrder = {
                            orderListViewModel.addOrder()
                            navController.navigate("ordersScreen")},
                        orderViewModel = orderListViewModel.currentOrderVM
                    )}

                composable(route = "ordersScreen"){

                    fun goToPrevScreen(editMode: Boolean) {
                        orderListViewModel.setInEditMode(editMode)
                        if (editMode){
                            orderListViewModel.editOrder()
                        }
                        navController.navigate("orderAssemblyScreen")
                    }

                    OrdersScreen(
                        onClickNewOrder = {goToPrevScreen(false)},
                        onClickEditOrder = {goToPrevScreen(true)},
                        orderListViewModel = orderListViewModel,
                        restartApp = { navController.navigate("welcomeScreen"); triggerRestart(activity)},
                    )}
            }
        }
    }
}

fun triggerRestart(context: Activity) {
    val intent = Intent(context, context::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
    context.finish()
    Runtime.getRuntime().exit(0)
}
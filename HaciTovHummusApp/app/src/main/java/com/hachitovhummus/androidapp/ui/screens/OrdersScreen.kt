package com.hachitovhummus.androidapp.ui.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.hachitovhummus.androidapp.R
import com.hachitovhummus.androidapp.model.*
import com.hachitovhummus.androidapp.ui.components.OrderCardContainer
import com.hachitovhummus.androidapp.ui.theme.*

private val BottomBarHeight = 88.dp

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun OrdersScreen(onClickNewOrder: () -> Unit, onClickEditOrder: () -> Unit, orderListViewModel: OrderListViewModel, restartApp: () -> Unit){
    val refreshList: Boolean by orderListViewModel.refreshList.observeAsState(true)

    Box(modifier = Modifier
        .navigationBarsPadding()
        .fillMaxSize()
        .background(
            Brush.verticalGradient(listOf(Cream1, Cream2))
        )
    ){
        OrderList(modifier = Modifier.statusBarsPadding().padding(bottom = BottomBarHeight, start = 16.dp, end = 16.dp), orders = orderListViewModel.orders.value!!, onClickNewOrder = onClickNewOrder, onClickEditOrder = onClickEditOrder, vm = orderListViewModel, refreshList)
        BottomArea(orderListViewModel = orderListViewModel, restartApp, modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun OrderList(modifier: Modifier, orders: List<Order>, onClickNewOrder: () -> Unit, onClickEditOrder: () -> Unit, vm: OrderListViewModel, refreshList: Boolean)
{
    if(refreshList){
        LazyColumn(modifier = modifier) {
            item{NextOrder(onClickNewOrder)}
            itemsIndexed(items = orders.reversed()) { index, order  -> OrderCardContainer(
                order, onClickEditOrder, orders.size - index - 1, vm,   order.price, order.drink != Drink.NONE, order.hasSmallSalad, order.drink)
            }
            item{Spacer(Modifier.size(28.dp))}
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun BottomArea(orderListViewModel: OrderListViewModel, restartApp: () -> Unit, modifier: Modifier){
    var visibilityOfFinishScreen by remember { mutableStateOf(false)}
    var activateUserNameAnimation by remember { mutableStateOf(true)}
    val focusManager = LocalFocusManager.current

    Box(modifier = modifier){
        Surface(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp), color = Color.White, modifier = modifier.height(BottomBarHeight), elevation = 8.dp){

        }
        Surface(shape = CircleShape, color = Color.White, elevation = 6.dp, modifier = Modifier
            .size(118.dp)
            .align(Alignment.BottomCenter)){}
        Surface(color = Color.White,modifier = Modifier
            .height(BottomBarHeight - 28.dp)
            .fillMaxWidth()
            .align(Alignment.BottomCenter)){}
        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 16.dp, end = 32.dp, bottom = 18.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

            AnimatedVisibility(visible = activateUserNameAnimation, enter = slideInVertically(), exit = slideOutVertically() + fadeOut() ){
                userName(orderListViewModel, focusManager)
            }
            Text(text = "בטל הזמנה", style = hummusAppTypography.h4, color = Color.Red, modifier = Modifier.clickable(onClick = {
                restartApp()
            }))
        }
        Surface(shape = CircleShape, color = Color.Gray, elevation = 4.dp, modifier = Modifier
            .size(92.dp)
            .align(Alignment.Center)
        ){
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.background(
                Brush.horizontalGradient(listOf(Grad4,Grad6), startX = -100f))
                .clickable(onClick = {
                    if(orderListViewModel.orders.value!!.isEmpty()){

                    }
                    else if(orderListViewModel.userName.value == ""){
                        activateUserNameAnimation = false
                        val handler = Handler(Looper.getMainLooper())
                        handler.postDelayed({
                            activateUserNameAnimation = true
                        }, 40)
                    }
                    else{
                        focusManager.clearFocus()
                        visibilityOfFinishScreen = true;

                        val handler = Handler(Looper.getMainLooper())
                        handler.postDelayed({
                            restartApp()
                        }, 8000)
                    }
                }
                )
            ) {
                Text(text = stringResource(R.string.place_order), style = hummusAppTypography.h2, textAlign = TextAlign.Center, color = Color.White, modifier = Modifier.fillMaxWidth())
            }
        }
    }
    AnimatedVisibility(visible = visibilityOfFinishScreen, enter = fadeIn() ){
        FinishScreen(true)
    }
}

@Composable
fun userName(vm: OrderListViewModel, foc: FocusManager){
    var userText by remember { mutableStateOf(vm.userName.value!!) }

    OutlinedTextField(value = userText, onValueChange = {vm.setUserName(it); userText = vm.userName.value!!}, label = { Text(text = "שם המזמין", style = hummusAppTypography.subtitle1, textAlign = TextAlign.Center) }, singleLine = true, textStyle = hummusAppTypography.subtitle1,
        keyboardActions = KeyboardActions(onDone = {foc.clearFocus(); userText = vm.userName.value!!}),modifier = Modifier
            .width(116.dp).height(58.dp))
}

@Composable
fun NextOrder(onClickNewOrder: () -> Unit){
    Column {
        Spacer(Modifier.size(16.dp))
        Surface(shape = RoundedCornerShape(12.dp), color = Color.White, contentColor = Color.Black, elevation = 4.dp, modifier = Modifier

            .fillMaxWidth()
            .height(216.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(onClick = onClickNewOrder)
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(184.dp)
                ) {
                    Surface(shape = RoundedCornerShape(32.dp)) {
                        Image(
                            painter = painterResource(R.drawable.empty),
                            contentDescription = "Dish Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.TopCenter
                        )
                        Surface(color = Grey1.copy(alpha = 0.5f), modifier = Modifier.matchParentSize()) {
                            Icon(imageVector = Icons.Rounded.Add, contentDescription = null, tint = Color.White.copy(alpha = 0.75f), modifier = Modifier
                                .wrapContentSize()
                                .scale(5f))
                        }
                    }
                    Surface(shape = RoundedCornerShape(topEnd = 24.dp), modifier = Modifier
                        .size(height = 40.dp, width = 64.dp)
                        .align(Alignment.BottomStart)
                    )  {
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun OrderListPreview()
{
    HachiTovHummusTheme(){
        Surface{
            OrdersScreen({},{},OrderListViewModel(),{})
        }
    }
}

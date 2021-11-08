package com.hachitovhummus.androidapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hachitovhummus.androidapp.model.Drink
import com.hachitovhummus.androidapp.model.Order
import com.hachitovhummus.androidapp.ui.HummusApp
import com.hachitovhummus.androidapp.ui.screens.OrderListViewModel
import com.hachitovhummus.androidapp.ui.theme.*
import kotlin.math.roundToInt

@Composable
fun OrderCard(order: Order, price: Int, withDrink: Boolean, withSmallSalad: Boolean, drink: Drink){ //withoutSpices: Boolean,
    var orderCardHeight = 292
    var smallSaladText: String = ""
    if(withDrink || withSmallSalad){
        orderCardHeight += 16
        if(withSmallSalad){
            smallSaladText = "סלט אישי"
        }
    }

    Surface(color = Color.White, contentColor = Color.Black, elevation = 4.dp, modifier = Modifier
        .fillMaxWidth()
        .height(orderCardHeight.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(196.dp)){
                Surface(shape = RoundedCornerShape(32.dp)) {
                    Image(painter = painterResource(order.dish.imageID), contentDescription = "Dish Image", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop, alignment = Alignment.TopCenter)
                }
                Surface(shape = RoundedCornerShape(topEnd = 24.dp), modifier = Modifier
                    .size(height = 40.dp, width = 64.dp)
                    .align(Alignment.BottomStart)
                ) {
                    Text(
                        buildAnnotatedString {
                            append(price.toString())
                            withStyle(style = SpanStyle(fontSize = 16.sp)){
                                append('₪')
                            }
                        }, style = hummusAppTypography.h2, textAlign = TextAlign.Center, modifier = Modifier.offset(x = -4.dp, y = 4.dp))
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {

                Column(horizontalAlignment = Alignment.Start) {
                    var temp = 4
                    var temp2 = ""
                    if(withDrink && withSmallSalad){
                        temp2 = ", "
                    }
                    if(withDrink || withSmallSalad){
                        Text(text = smallSaladText + temp2 + drink.type, style = hummusAppTypography.h4, color = Green2, modifier = Modifier.padding(top = 2.dp).offset(y=8.dp))
                        temp = 0
                    }
                    Text(text = order.dish.name, style = hummusAppTypography.h1, modifier = Modifier.padding(top = temp.dp))
                    Text(text = order.dish.excludingSpices, style = hummusAppTypography.subtitle1)
                }
            }
        }
    }
}

@Composable
fun OrderCardContainer(order: Order, onClickEditOrder: () -> Unit, index: Int, vm: OrderListViewModel, price: Int, withDrink: Boolean, withSmallSalad: Boolean, drink: Drink){
    Box(modifier = Modifier
        .fillMaxWidth()){
        Surface(shape = RoundedCornerShape(24.dp), elevation = 4.dp, modifier = Modifier.clickable(onClick = {vm.currentIndex(index); onClickEditOrder()})) {
            OrderCard(order = order, price, withDrink, withSmallSalad, drink)
        }
        Surface(shape = RoundedCornerShape(topStart = 24.dp, bottomEnd = 24.dp), color = Grad6, modifier = Modifier.align(Alignment.TopStart).height(46.dp).width(64.dp)) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                IconButton(onClick = { vm.removeOrder(index)}) {  //put removeState = false; in between to activate animation
                    Icon(imageVector = Icons.Filled.HighlightOff,
                        tint = Color.White,
                        contentDescription = "Remove Dish", modifier = Modifier.size(36.dp))
                }
            }
        }
    }
    Spacer(modifier = Modifier.size(16.dp))
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable //another version of container, using slider
fun OrderCardContainerSliderVersion(order: Order, onClickEditOrder: () -> Unit, onClickRemoveOrder: () -> Unit, index: Int, vm: OrderListViewModel, price: Int, withDrink: Boolean, withSmallSalad: Boolean, drink: Drink){
    var swipeableState = rememberSwipeableState(initialValue = 0)
    val sizePx = with(LocalDensity.current) { 48.dp.toPx() }
    val anchors = mapOf(0f to 0,sizePx  to 1)

    var removeState by remember { mutableStateOf(true) }
    AnimatedVisibility(visible = removeState, exit = shrinkHorizontally()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                reverseDirection = true,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )){
            Surface(shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp), elevation = (swipeableState.offset.value.roundToInt()/26).dp, color = Color.White, modifier = Modifier
                .width(60.dp)
                .height(120.dp)
                .align(Alignment.CenterStart)){//.offset(y=-30.dp)){
                Column(modifier = Modifier
                    .height(20.dp)
                    .width(48.dp)
                    .align(Alignment.CenterStart)
                    .padding(top = 8.dp, bottom = 8.dp)
                    , verticalArrangement = Arrangement.SpaceBetween){
                    IconButton(onClick = {vm.currentIndex(index); onClickEditOrder()}) {
                        Icon(imageVector = Icons.Filled.Edit,
                            tint = Grad6,
                            contentDescription = "Edit Dish")
                    }
                    IconButton(onClick = { println("@@@${order.dish.name}  $index");  vm.removeOrder(index); swipeableState.performDrag(-100F)}) {  //put removeState = false; in between to activate animation
                        Icon(imageVector = Icons.Filled.Delete,
                            tint = Grad6,
                            contentDescription = "Remove Dish")
                    }
                }
            }
            Surface(shape = RoundedCornerShape(12.dp), elevation = 4.dp,
                modifier = Modifier.offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }) {
                OrderCard(order = order, price, withDrink, withSmallSalad, drink)
            }
        }
    }
    Spacer(modifier = Modifier.size(8.dp))
}

@Preview()
@Composable
fun OrderCardPreview()
{
    HachiTovHummusTheme() {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl ){
            Surface {
                OrderCard(Order(), 25, true, true, Drink.NONE)
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview()
@Composable
fun OrderCardContainerPreview()
{
    HachiTovHummusTheme() {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl ){
            OrderCardContainer(Order(), {},1,OrderListViewModel(),25,false,false,Drink.NONE)
        }
    }
}
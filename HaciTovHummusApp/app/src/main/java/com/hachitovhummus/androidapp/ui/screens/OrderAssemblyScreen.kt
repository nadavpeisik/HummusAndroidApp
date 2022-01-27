package com.hachitovhummus.androidapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.hachitovhummus.androidapp.R
import com.hachitovhummus.androidapp.model.*
import com.hachitovhummus.androidapp.ui.components.*
import com.hachitovhummus.androidapp.ui.theme.*

@ExperimentalAnimationApi
@Composable
fun OrderAssemblyScreen(orderViewModel: OrderViewModel, onClickFinishedOrder: () -> Unit){
    val order: Order by orderViewModel.order.observeAsState(Order())
    val price = orderViewModel.order.value!!.price
    val businessDish = orderViewModel.order.value!!.isBusinessMeal
    val smallDish = orderViewModel.order.value!!.dish.isSmallDish
    val special = orderViewModel.order.value!!.dish.special
    val basics = orderViewModel.order.value!!.dish.basics
    val enabledBasics: Set<Basic> by orderViewModel.enabledBasics.observeAsState(setOf())
    val spices = orderViewModel.order.value!!.dish.spices
    val smallSalad = orderViewModel.order.value!!.hasSmallSalad
    val drink = orderViewModel.order.value!!.drink
    val isASaladDish = orderViewModel.order.value!!.dish.isSaladDish
    var startToastAnimation by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().statusBarsPadding()
        .navigationBarsPadding()){
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Brush.verticalGradient(listOf(Cream1, Cream2)))) {
            Spacer(modifier = Modifier.size(292.dp))
            Spacer(modifier = Modifier.size(24.dp))
            Row{
                Row(modifier = Modifier.padding(start = 16.dp).toggleable(value = businessDish, onValueChange = {orderViewModel.onBusinessDishSelect()}), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = businessDish, onCheckedChange = {orderViewModel.onBusinessDishSelect()}, modifier = Modifier.scale(0.85f).offset(y=1.dp), colors = CheckboxDefaults.colors(Green2))
                    Text(text = stringResource(R.string.business_meal), style = hummusAppTypography.subtitle1, textAlign = TextAlign.Start, modifier = Modifier.padding(start = 1.dp))

                }
                Row(modifier = Modifier.padding(start = 16.dp).toggleable(value = smallDish, onValueChange = {orderViewModel.onSmallDishSelect()}), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = smallDish, onCheckedChange = {orderViewModel.onSmallDishSelect()}, modifier = Modifier.scale(0.85f).offset(y=1.dp), colors = CheckboxDefaults.colors(Green2))
                    Text(text = stringResource(R.string.small_dish), style = hummusAppTypography.subtitle1, textAlign = TextAlign.Start, modifier = Modifier.padding(start = 1.dp))
                }
            }

            Text(text = stringResource(R.string.specials), style = hummusAppTypography.h2, textAlign = TextAlign.Start, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp))
            when {
                smallDish -> {
                    SpecialsRow(currentSpecial = special, onSpecialChange = {orderViewModel.onSpecialChange(it)}, smallDishSpecialsList)
                }
                businessDish -> {
                    SpecialsRow(currentSpecial = special, onSpecialChange = {orderViewModel.onSpecialChange(it)}, businessDishSpecialsList)
                }
                else -> {
                    SpecialsRow(currentSpecial = special, onSpecialChange = {orderViewModel.onSpecialChange(it)}, specialsList)
                }
            }

            Column(
                Modifier
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    buildAnnotatedString {
                        append(stringResource(R.string.basics))
                        withStyle(style = SpanStyle(fontSize = 16.sp, letterSpacing = 0.1.sp, fontWeight = FontWeight.Light, color = Color.Black)){
                            append(" ללא תשלום")
                        }
                    }, style = hummusAppTypography.h2, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())

                BasicsRow(basics = basics.toSet(), onBasicSelect = {orderViewModel.onBasicSelect(it)}, enabledBasics = enabledBasics)
                Spacer(modifier = Modifier.size(16.dp))
                SpicesRow(spices = spices.toSet(), onSpiceSelect = {orderViewModel.onSpiceSelect(it)}, isASaladDish)
                SmallSalad(isSaladDish = isASaladDish, currentState = smallSalad, onSmallSaladSelect = {orderViewModel.onSmallSaladSelect()})
                Text(text = stringResource(R.string.drink), style = hummusAppTypography.h2, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
            }
            DrinksRow(drinksList, drink) { orderViewModel.onDrinkChange(it) }
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = stringResource(R.string.user_comment), style = hummusAppTypography.h2, textAlign = TextAlign.Start, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp))
            UserComment(orderViewModel)

            if(startToastAnimation){
                Spacer(modifier = Modifier.size(8.dp))
                AnimatedVisibility(visible = startToastAnimation, enter =  fadeIn(animationSpec = tween(durationMillis = 700, delayMillis = 50)) ){
                    CustomToast(Modifier.align(CenterHorizontally).padding(start = 280.dp), stringResource(R.string.add_drink_toast))
                }
            }
            else{
                Spacer(modifier = Modifier.size(22.dp))
            }

            Surface(shape = RoundedCornerShape(24.dp), elevation = 4.dp, modifier = Modifier.align(CenterHorizontally)
                .padding(top = 24.dp, bottom = 24.dp, start = 40.dp, end = 40.dp)
                .width(240.dp)
                .height(56.dp)
            ) {

                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = CenterHorizontally, modifier = Modifier.clickable(
                    onClick = { if(orderViewModel.order.value!!.deservesDrink(orderViewModel.happyHour)){
                        startToastAnimation = true
                    } else{
                        onClickFinishedOrder()}
                    }).background(
                    Brush.horizontalGradient(listOf(Grad4,Grad6), startX = -100f))) {
                    Text(text = stringResource(R.string.finish_order), style = hummusAppTypography.h2, textAlign = TextAlign.Center, color = Color.White, modifier = Modifier.fillMaxWidth())
                }
            }
        }
        OrderCard(order, price, drink != Drink.NONE, smallSalad, drink)
    }

}

@Composable
private fun BasicsRow(
    basics: Set<Basic>,
    onBasicSelect: (Basic) -> Unit,
    enabledBasics: Set<Basic>,
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
        .height(112.dp), verticalAlignment = Alignment.CenterVertically) {
        BasicCard(Basic.CHICKPEA, basics, onBasicSelect, enabledBasics)
        BasicCard(Basic.TAHINI, basics, onBasicSelect, enabledBasics)
        BasicCard(Basic.EGG, basics, onBasicSelect, enabledBasics)
        BasicCard(Basic.FUL, basics, onBasicSelect, enabledBasics)
    }
}

@Composable
private fun SpecialsRow(
    currentSpecial: Special,
    onSpecialChange: (Special) -> Unit,
    specials: List<Special>
) {

    LazyRow(
        modifier = Modifier.height(136.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(start = 8.dp, end = 16.dp),
    ) {
        items(specials) { special ->
            SpecialCard(special, currentSpecial, onSpecialChange)
        }
    }
}

@Composable
private fun SpicesRow(
    spices: Set<Spice>,
    onSpiceSelect: (Spice) -> Unit,
    isSaladDish: Boolean
) {
    if(!isSaladDish){
        Text(text = stringResource(R.string.spices), style = hummusAppTypography.h2, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .height(100.dp), verticalAlignment = Alignment.CenterVertically) {
            SpiceCard(Spice.CUMIN, spices, onSpiceSelect)
            SpiceCard(Spice.PAPRIKA, spices, onSpiceSelect)
            SpiceCard(Spice.OLIVE_OIL, spices, onSpiceSelect)
            SpiceCard(Spice.PARSLEY, spices, onSpiceSelect)
        }
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
private fun SmallSalad(isSaladDish: Boolean, currentState: Boolean, onSmallSaladSelect: () -> Unit){
    if(!isSaladDish){
        Text(text = stringResource(R.string.small_salad), style = hummusAppTypography.h2, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.size(4.dp))
        SmallSaladCard(currentState = currentState, onSmallSaladClick = onSmallSaladSelect)
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
private fun DrinksRow(
    drinks: List<Drink>,
    currentDrink: Drink,
    onDrinkSelect: (Drink) -> Unit,
) {
    LazyRow(
        modifier = Modifier.height(80.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(start = 8.dp, end = 16.dp),
    ) {
        items(drinks) { drink ->
            DrinkCard(drink, currentDrink, onDrinkSelect)
        }
    }
}

@Composable
fun UserComment(vm:OrderViewModel){
    val focusManager = LocalFocusManager.current
    var userText by remember { mutableStateOf("") }
    OutlinedTextField(value = userText, onValueChange = {if(it.length <= 26){userText = it; vm.updateComment(userText)}}, label = { Text(text = "מקום לבקשות מיוחדות", style = hummusAppTypography.subtitle1, textAlign = TextAlign.Start) }, singleLine = true, textStyle = hummusAppTypography.subtitle1,
        keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus(); vm.updateComment(userText)}),modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 160.dp))
}

@Composable
fun CustomToast(modifier: Modifier, toastText: String){
    Surface(shape = RoundedCornerShape(12.dp), color = Color.Black.copy(alpha = 0.6f), modifier = modifier
        .width(116.dp)
        .height(24.dp)) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = CenterHorizontally) {
            Text(text = toastText, textAlign = TextAlign.Center, style = hummusAppTypography.subtitle1, color = Color.White)
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun OrderAssemblyScreenPreview() {
    HachiTovHummusTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl ){
            OrderAssemblyScreen(OrderViewModel(true)) {}
        }
    }
}
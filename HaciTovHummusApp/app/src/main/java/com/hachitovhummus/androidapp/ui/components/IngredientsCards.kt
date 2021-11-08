package com.hachitovhummus.androidapp.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hachitovhummus.androidapp.R
import com.hachitovhummus.androidapp.model.*
import com.hachitovhummus.androidapp.ui.theme.*
import com.hachitovhummus.androidapp.ui.theme.Grey2

@Composable
fun BasicCard(basic: Basic, basics: Set<Basic>, onBasicClick: (Basic) -> Unit, enabledBasics: Set<Basic>,){
    var checked = basics.contains(basic)
    val basicCardTransitionState = checkAnimation(checked)

    Surface(shape = RoundedCornerShape(12.dp), elevation = 4.dp, color = Color.White, contentColor = Color.Black, modifier = Modifier.size(
        width = 84.dp,
        height = 96.dp
    )
    ) {
        if(enabledBasics.contains(basic)){
            Box(modifier = Modifier.toggleable(value = checked,
                onValueChange = {checked = it; onBasicClick(basic)}
            )){
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)) {
                    Surface(shape = RoundedCornerShape(14.dp),color = basic.backgroundColor, modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)) {
                        Image(painter = painterResource(basic.imageID), contentDescription = "picture of a basic ingredient", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Fit)
                    }
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){
                        Text(text = basic.type, style = MaterialTheme.typography.h4, modifier = Modifier.offset(y = 3.dp)) // TODO: Ideally use textAlign = TextAlign.Center and delete the column, but it doesn't work...
                    }
                }
                if(checked){
                    Surface(color = Grey1.copy(alpha = basicCardTransitionState.selectedBGAlpha), modifier = Modifier.matchParentSize()) {
                        Icon(imageVector = Icons.Filled.Done, contentDescription = null, tint = Green2.copy(alpha = basicCardTransitionState.selectedCheckAlpha), modifier = Modifier
                            .wrapContentSize()
                            .scale(basicCardTransitionState.checkScale)
                            .align(Alignment.Center).offset(y=-8.dp))
                    }
                }
            }
        }
        else{
            Box{
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)) {
                    Surface(shape = RoundedCornerShape(14.dp),color = Grey4, modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)) {
                        Image(painter = painterResource(basic.imageID), contentDescription = "picture of a basic ingredient", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Fit, alpha = 0.25F)
                    }
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){
                        Text(text = basic.type, style = MaterialTheme.typography.h4, modifier = Modifier.offset(y = 3.dp), color = Grey2) // TODO: Ideally use textAlign = TextAlign.Center and delete the column, but it doesn't work...
                    }
                }
            }
        }
    }
}

val specialsList = listOf(Special.NONE, Special.HAMSHUKA, Special.MUSHROOMS, Special.MSABAHA, Special.BIG_SALAD, Special.SPECIAL_SALAD)
val smallDishSpecialsList = listOf(Special.NONE, Special.HAMSHUKA, Special.MUSHROOMS)
val businessDishSpecialsList = listOf(Special.NONE, Special.HAMSHUKA, Special.MUSHROOMS, Special.MSABAHA)

@Composable
fun SpecialCard(special: Special, currentSpecial: Special, onSpecialChange: (Special) -> Unit){
    var checked = special == currentSpecial
    val specialCardTransitionState = checkAnimation(checked)
    Surface(shape = RoundedCornerShape(16.dp), elevation = 4.dp, color = Color.White, contentColor = Color.Black, modifier = Modifier
        .padding(start = 14.dp)
        .size(
            width = 112.dp,
            height = 120.dp
        )
    ) {
        Box(modifier = Modifier.toggleable(value = checked,
            onValueChange = {
                onSpecialChange(special)
            }
        )){
            Box(){
                Column(Modifier.fillMaxSize()) {
                    Box(modifier = androidx.compose.ui.Modifier
                        .background(Brush.horizontalGradient(special.gradientColors))
                        .fillMaxWidth()
                        .height(56.dp)) {}
                    Column(verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){
                        Text(text = special.type, style = MaterialTheme.typography.h4, modifier = Modifier.offset(y = -4.dp)) //TODO: same as in basic card
                    }
                }
                SpecialImage(imageID = special.imageID, modifier = Modifier
                    .size(76.dp)
                    .align(Alignment.Center)
                    .offset(y = -6.dp)
                )
            }
            if(checked){
                Surface(color = Grey1.copy(alpha = specialCardTransitionState.selectedBGAlpha), modifier = Modifier.matchParentSize()) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = null, tint = Green2.copy(alpha = specialCardTransitionState.selectedCheckAlpha), modifier = Modifier
                        .wrapContentSize()
                        .scale(specialCardTransitionState.checkScale)
                        .align(Alignment.Center).offset(x=-1.dp, y=-4.dp))
                }
            }
        }
    }
}

@Composable
fun SpecialImage(imageID: Int, modifier: Modifier, elevation: Dp = 0.dp) //align(Alignment.BottomCenter)
{
    Surface(color = Color.White, elevation = elevation, shape = CircleShape, modifier = modifier){
        Image(painter = painterResource(imageID), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
    }
}

@Composable
fun SpiceCard(spice: Spice, spices: Set<Spice>, onSpiceClick: (Spice) -> Unit) {
    var checked = spices.contains(spice)
    val spiceCardTransitionState = checkAnimation(checked)

    Surface(elevation = 4.dp, shape = CircleShape, color = spice.backgroundColor, modifier = Modifier
        .size(80.dp)
    ) {
        Box(Modifier.toggleable(value = checked, onValueChange = {onSpiceClick(spice)})){
            Image(painter = painterResource(spice.imageID), contentDescription = null, modifier = Modifier
                .fillMaxSize(), contentScale = ContentScale.Crop) //TODO: should be drinksMap[drink]
            if(checked){
                Surface(color = Grey1.copy(alpha = spiceCardTransitionState.selectedBGAlpha), modifier = Modifier.matchParentSize()) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = null, tint = Green2.copy(alpha = spiceCardTransitionState.selectedCheckAlpha), modifier = Modifier
                        .wrapContentSize()
                        .scale(spiceCardTransitionState.checkScale))
                }
            }
        }
    }
}

@Composable
fun SmallSaladCard(currentState: Boolean, onSmallSaladClick: () -> Unit) {
    var checked = currentState
    val smallSaladCardTransitionState = checkAnimation(checked)
    Surface(elevation = 4.dp, shape = RoundedCornerShape(16.dp), color = Green2, modifier = Modifier
        .size(80.dp)) {
        Box(modifier = Modifier.toggleable(value = checked, onValueChange = {onSmallSaladClick()})){

            Image(painter = painterResource(R.drawable.small_salad), contentDescription = null, modifier = Modifier
                .padding(3.dp)
                .fillMaxSize(), contentScale = ContentScale.Crop)

            if(checked){
                Surface(color = Grey1.copy(alpha = smallSaladCardTransitionState.selectedBGAlpha), modifier = Modifier.matchParentSize()) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = null, tint = Green2.copy(alpha = smallSaladCardTransitionState.selectedCheckAlpha), modifier = Modifier
                        .wrapContentSize()
                        .scale(smallSaladCardTransitionState.checkScale))
                }
            }
        }
    }
}

val drinksList = listOf(Drink.WATER, Drink.COKE, Drink.COKE_ZERO, Drink.FANTA, Drink.SPRITE, Drink.SPRITE_ZERO, Drink.GRAPE_JUICE, Drink.SODA)

@Composable
fun DrinkCard(drink: Drink, currentDrink: Drink, onDrinkClick: (Drink) -> Unit) {
    var checked = drink == currentDrink
    val drinkCardTransitionState = checkAnimation(checked)
    Surface(elevation = 4.dp, shape = RoundedCornerShape(8.dp), color = drink.backgroundColor, modifier = Modifier
        .padding(start = 16.dp)
        .size(height = 76.dp, width = 64.dp)) {
        Box(modifier = Modifier.toggleable(value = checked, onValueChange = {onDrinkClick(drink)})){
            Image(painter = painterResource(drink.imageID), contentDescription = null, modifier = Modifier
                .padding(6.dp)
                .fillMaxSize(), contentScale = ContentScale.Crop) //TODO: should be drinksMap[drink]
            if(checked){
                Surface(color = Grey1.copy(alpha = drinkCardTransitionState.selectedBGAlpha), modifier = Modifier.matchParentSize()) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = null, tint = Green2.copy(alpha = drinkCardTransitionState.selectedCheckAlpha), modifier = Modifier
                        .wrapContentSize()
                        .scale(drinkCardTransitionState.checkScale))
                }
            }
        }
    }
}

@Preview
@Composable
fun BasicCardPreview()
{
    HachiTovHummusTheme() {
        BasicCard(Basic.TAHINI, setOf(Basic.TAHINI), {}, setOf(Basic.TAHINI))
    }
}

@Preview
@Composable
fun SpecialCardPreview()
{
    HachiTovHummusTheme {
        SpecialCard(Special.HAMSHUKA, Special.HAMSHUKA, {})
    }
}

@Preview
@Composable
fun SpiceCardPreview()
{
    HachiTovHummusTheme {
        SpiceCard(Spice.PARSLEY, setOf(), {})
    }
}

@Preview
@Composable
fun SmallSaladCardPreview()
{
    HachiTovHummusTheme {
        SmallSaladCard(true, {})
    }
}

@Preview
@Composable
fun DrinkCardPreview()
{
    HachiTovHummusTheme {
        DrinkCard(Drink.COKE, Drink.COKE, {})
    }
}


/**
 * Class holding animating values when transitioning topic chip states.
 */
private class CheckAnimation(
    selectedBGAlpha: State<Float>,
    selectedCheckAlpha: State<Float>,
    checkScale: State<Float>
) {
    val selectedBGAlpha by selectedBGAlpha
    val selectedCheckAlpha by selectedCheckAlpha
    val checkScale by checkScale
}

private enum class SelectionState { Unselected, Selected }

@Composable
private fun checkAnimation(topicSelected: Boolean): CheckAnimation {
    val transition = updateTransition(
        targetState = if (topicSelected) SelectionState.Selected else SelectionState.Unselected
    )
    val selectedBGAlpha = transition.animateFloat { state ->
        when (state) {
            SelectionState.Unselected -> 0f
            SelectionState.Selected -> 0.35f
        }
    }
    val selectedCheckAlpha = transition.animateFloat { state ->
        when (state) {
            SelectionState.Unselected -> 0f
            SelectionState.Selected -> 0.95f
        }
    }
    val checkScale = transition.animateFloat { state ->
        when (state) {
            SelectionState.Unselected -> 0.5f
            SelectionState.Selected -> 2f
        }
    }
    return remember(transition) {
        CheckAnimation(selectedBGAlpha, selectedCheckAlpha, checkScale)
    }
}
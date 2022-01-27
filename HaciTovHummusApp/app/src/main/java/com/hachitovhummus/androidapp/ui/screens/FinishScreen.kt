package com.hachitovhummus.androidapp.ui.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hachitovhummus.androidapp.R
import com.hachitovhummus.androidapp.ui.theme.*

@ExperimentalAnimationApi
@Composable
fun FinishScreen(animate: Boolean){
    var startAnimation by remember { mutableStateOf(false) }

    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
        startAnimation = animate
    }, 10)

    val offsetAnimation: Dp by animateDpAsState(
        if (!startAnimation) (-900).dp else 0.dp,
        spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = 100.8F)
    )

    Surface(color = Yellow7, modifier = Modifier.fillMaxSize()){
        Column(Modifier.fillMaxSize().offset(y=40.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Image(
                painter = painterResource(R.drawable.finish_screen_logo),
                contentDescription = "Thank You Logo",
                modifier = Modifier.size(280.dp).absoluteOffset(y = offsetAnimation),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.size(16.dp))
            //startAnimation
            AnimatedVisibility(visible = startAnimation, enter =  fadeIn(animationSpec = tween(durationMillis = 700, delayMillis = 350)) + slideInVertically(animationSpec = tween(durationMillis = 700, delayMillis = 300)) ){
                Text(text = stringResource(R.string.thank_you), style = hummusAppTypography.h5, fontSize = 46.sp, color = Red4, textAlign = TextAlign.Center)
            }

            AnimatedVisibility(visible = startAnimation, enter = fadeIn(animationSpec = tween(durationMillis = 650, delayMillis = 550)) + slideInVertically(animationSpec = tween(durationMillis = 650, delayMillis = 500))){
                Text(text = stringResource(R.string.go_pay),style = hummusAppTypography.h6, fontSize = 32.sp, color = Red4)
            }
            Spacer(modifier = Modifier.size(50.dp))
            AnimatedVisibility(visible = startAnimation, enter = fadeIn(animationSpec = tween(durationMillis = 600, delayMillis = 700)) + slideInVertically(animationSpec = tween(durationMillis = 600, delayMillis = 650))){

                Text(text = stringResource(R.string.order_place_in_line),style = hummusAppTypography.h4, fontSize = 20.sp, color = Color.Black)
            }
            AnimatedVisibility(visible = startAnimation, enter = fadeIn(animationSpec = tween(durationMillis = 550, delayMillis = 750)) + slideInVertically(animationSpec = tween(durationMillis = 600, delayMillis = 650))){

                Text(text = "4",style = hummusAppTypography.h4, fontSize = 40.sp, color = Color.Black)
            }


        }

    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun FinishScreenPreview(){
    FinishScreen(true)
}
package com.hachitovhummus.androidapp.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.hachitovhummus.androidapp.ui.theme.*
import com.hachitovhummus.androidapp.R

@Composable
fun WelcomeScreen(onClickNewOrder: () -> Unit, onClickTakeOut: () -> Unit)
{
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.radialGradient(
                listOf(Grad1, Grad2),
                radius = 800f,
                center = Offset(0f, 400f)
            )
        )
        .navigationBarsPadding()){
        Column(
            Modifier
                .statusBarsPadding()
                .fillMaxSize()){
            Image(painter = painterResource(R.drawable.welcome_screen_logo_v6), contentDescription = "logo pic")
            ButtonsArea(onClickNewOrder, onClickTakeOut)
        }
    }
}

@Composable
fun ButtonsArea(onClickNewOrder: () -> Unit, onClickTakeOut:() -> Unit){
    Surface(shape = RoundedCornerShape(topStart = 40.dp,topEnd = 40.dp), elevation = 20.dp, color = Color.White, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(top = 32.dp, bottom = 16.dp, start = 40.dp, end = 40.dp), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(shape = RoundedCornerShape(24.dp), elevation = 4.dp, modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                        , color = Color.Red) {
                        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable(onClick = onClickNewOrder).background(Brush.horizontalGradient(listOf(Grad4,Grad6), startX = -300f))
                        ){
                            Text(text = stringResource(R.string.new_order), style = hummusAppTypography.h1, color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.size(32.dp))
                    Surface(shape = RoundedCornerShape(24.dp), elevation = 4.dp, modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                        , color = Color.Red) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.clickable(onClick = onClickTakeOut).background(
                            Brush.horizontalGradient(listOf(Grad6,Grad5,Grad4)))){
                            Text(text = stringResource(R.string.take_out), style = hummusAppTypography.h1, color = Color.White)
                        }
                    }
            }
            val context = LocalContext.current
            Row(modifier = Modifier.clickable(onClick = {dialPhoneNumber("039031030", context)}), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Filled.Phone,
                    tint = Grad6,
                    contentDescription = "phone icon")
                Text(text = stringResource(R.string.contact_us), color = Grad6, style = hummusAppTypography.h4)
            }
        }
    }
}

fun dialPhoneNumber(phoneNumber: String, context: Context) {
    Log.d("Dialing", "now")
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}

@Preview
@Composable
fun WelcomeScreenPreview(){
    HachiTovHummusTheme {
        Surface{
            WelcomeScreen({},{})
        }
    }
}
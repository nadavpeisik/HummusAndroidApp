package com.hachitovhummus.androidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.view.WindowCompat
import com.hachitovhummus.androidapp.ui.HummusApp

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false) // draws app behind top system bar
        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl ){
                HummusApp(this)
            }

        }
    }

    override fun onBackPressed() { // disabling the back button

    }
}
package com.jyproject.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jyproject.placepick.ui.theme.PlacePickTheme
import com.jyproject.presentation.ui.login.LoginScreen

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PlacePickTheme {
                LoginScreen()
            }
        }
    }
}
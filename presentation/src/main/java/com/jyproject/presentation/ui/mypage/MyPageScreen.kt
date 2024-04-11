package com.jyproject.presentation.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun MyPageScreen(
    navController: NavController
) {
    Column { Text(text = "MYPAGE") }
}
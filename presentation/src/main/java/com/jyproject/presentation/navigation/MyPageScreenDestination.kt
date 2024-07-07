package com.jyproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.jyproject.presentation.ui.feature.mypage.MyPageScreen

@Composable
fun MyPageScreenDestination(
    navController: NavController
) {
    MyPageScreen(navController = navController)
}
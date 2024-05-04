package com.jyproject.presentation.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap

@Composable
fun MyPageScreen(
    navController: NavController
) {
    Map()
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun Map(){
    NaverMap(
        modifier = Modifier.fillMaxSize()
    )
}
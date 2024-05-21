package com.jyproject.presentation.ui.home.placeDetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun PlaceDetailScreen(
    navController: NavController,
    place: String?
){
    Text(text = place!!)
}
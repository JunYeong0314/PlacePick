package com.jyproject.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AddPlaceScreen(){
    Column {
        PlaceSearchBox()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceSearchBox(){
    var searchText by remember { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .background(color = Color.LightGray)
        ,
        value = searchText,
        onValueChange = { searchText = it },
        maxLines = 1
    )
}
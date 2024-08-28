package com.jyproject.presentation.ui.feature.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jyproject.presentation.ui.feature.register.composable.NickTextField
import com.jyproject.presentation.ui.feature.register.composable.RegisterTopBar

@Composable
fun RegisterScreen(
    userNum: String
){
    var nickText by remember { mutableStateOf("") }
    var nickLength by remember { mutableIntStateOf(0) }

    LaunchedEffect(nickText) {
        nickLength = nickText.length
    }
    
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = { RegisterTopBar() }
    ) { innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NickTextField(
                nickText = nickText,
                nickLength = nickLength,
                onValueChange = {
                    if (it.length <= 10) nickText = it
                }
            )
        }
    }

}
package com.jyproject.presentation.ui.feature.home.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jyproject.presentation.R

@Composable
fun EmptyPlaceList(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_empty), contentDescription = null)
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            modifier = Modifier,
            text = "앗! 아직 등록된 장소가 없어요.",
            fontWeight = FontWeight.Bold,
            color = Color.LightGray
        )
    }
}
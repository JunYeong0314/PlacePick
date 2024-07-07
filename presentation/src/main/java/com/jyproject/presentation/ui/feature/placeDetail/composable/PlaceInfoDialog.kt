package com.jyproject.presentation.ui.feature.placeDetail.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jyproject.presentation.R

@Composable
fun PlaceInfoDialog(
    onConfirmation: () -> Unit,
    place: String,
    placeArea: String?
){
    AlertDialog(
        modifier = Modifier
            .width(300.dp)
            .height(220.dp),
        containerColor = Color.White,
        text = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = colorResource(id = R.color.app_base)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "지역명: $place")
                Text(text = "관할구: $placeArea")
            }
        },
        onDismissRequest = { onConfirmation() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = "확인")
            }
        },
        dismissButton = null
    )

}
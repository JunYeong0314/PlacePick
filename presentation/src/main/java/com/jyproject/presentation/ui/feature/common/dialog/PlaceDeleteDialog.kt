package com.jyproject.presentation.ui.feature.common.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.jyproject.presentation.R

@Composable
fun PlaceDeleteDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
){
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = colorResource(id = R.color.app_base)
            )
        },
        containerColor = Color.White,
        text = {
            Column {
                Text(text = "삭제 후 다시 추가 가능해요!")
                Text(text = "해당지역을 정말 삭제하시겠어요?")
            }
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(
                    text = "삭제",
                    color = Color.Red
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(
                    text = "취소",
                    color = Color.DarkGray
                )
            }
        },
    )
}
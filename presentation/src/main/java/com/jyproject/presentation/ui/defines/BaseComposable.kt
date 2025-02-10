package com.jyproject.presentation.ui.defines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jyproject.presentation.R

object BaseComposable {
    @Composable
    fun RoundedBox(
        modifier: Modifier = Modifier,
        color: Color = colorResource(R.color.app_base),
        padding: Dp = 8.dp,
        roundedShapeSize: Dp = 10.dp,
        content: @Composable () -> Unit,
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    color = color,
                    shape = RoundedCornerShape(roundedShapeSize)
                ),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}
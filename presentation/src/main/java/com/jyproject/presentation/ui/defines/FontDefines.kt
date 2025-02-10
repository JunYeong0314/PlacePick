package com.jyproject.presentation.ui.defines

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jyproject.presentation.R

object FontDefines {
    val body18BlackBold = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )

    @Composable
    fun body14BlackHardGray(): TextStyle {
        return TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.hard_gray_1)
        )
    }

    @Composable
    fun body14BlackBlack(): TextStyle {
        return TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
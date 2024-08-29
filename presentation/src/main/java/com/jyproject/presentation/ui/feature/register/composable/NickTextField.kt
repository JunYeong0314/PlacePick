package com.jyproject.presentation.ui.feature.register.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.presentation.R

@Composable
fun NickTextField(
    nickText: String,
    nickLength: Int,
    checkNickMsg: String,
    onValueChange: (String) -> Unit
) {
    Column {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.light_gray_weak1),
                    shape = RoundedCornerShape(8.dp)
                )
                .height(50.dp),
            value = nickText,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontSize = 18.sp
            ),
            cursorBrush = SolidColor(Color.DarkGray),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                    ){
                        if (nickText.isEmpty()) {
                            Text(
                                text = "닉네임 입력 (최대 10자 가능)",
                                color = colorResource(id = R.color.light_gray_hard1),
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
        Text(
            modifier = Modifier.align(Alignment.End),
            text = "$nickLength/10",
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            fontSize = 12.sp
        )
    }
}
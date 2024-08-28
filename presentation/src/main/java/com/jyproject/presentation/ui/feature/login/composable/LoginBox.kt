package com.jyproject.presentation.ui.feature.login.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jyproject.domain.models.Platform
import com.jyproject.presentation.R

@Composable
fun LoginBox(
    painterId: Int,
    platform: String,
    onLoginClick: (String) -> Unit
){
    val title = platform + "로 시작하기"
    val barColor =
        when(platform){
            Platform.KAKAO -> R.color.kakao_login
            Platform.NAVER -> R.color.naver_login
            else -> R.color.black
        }
    val textColor =
        when(platform){
            Platform.KAKAO -> Color.Black
            Platform.NAVER -> Color.White
            else -> Color.Transparent
        }

    Button(
        modifier = Modifier
            .width(310.dp)
            .height(50.dp)
        ,
        onClick = { onLoginClick(platform) },
        colors = ButtonDefaults.buttonColors(colorResource(id = barColor)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = painterId),
                contentDescription = platform
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 6.dp)
                ,
                text = title,
                color = textColor,
                fontWeight = FontWeight.Bold
            )

        }
    }
}
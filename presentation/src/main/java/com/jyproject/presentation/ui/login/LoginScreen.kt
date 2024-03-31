package com.jyproject.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyproject.placepick.ui.theme.PlacePickTheme
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.login.Platform.KAKAO
import com.jyproject.presentation.ui.login.Platform.NAVER

@Composable fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_app),
            contentDescription = "app icon"
        )

        Spacer(modifier = Modifier.size(50.dp))

        LoginBox(painterId = R.drawable.ic_login_kakao, platform = KAKAO, onLoginClick = {})
        Spacer(modifier = Modifier.size(12.dp))
        LoginBox(painterId = R.drawable.ic_login_naver, platform = NAVER, onLoginClick = {})
    }
}

@Composable fun LoginBox(
    painterId: Int,
    platform: String,
    onLoginClick: (String) -> Unit
){
    val title = platform + "로 시작하기"
    val barColor =
        when(platform){
            KAKAO -> R.color.kakao_login
            NAVER -> R.color.naver_login
            else -> R.color.black
        }
    val textColor =
        when(platform){
            KAKAO -> Color.Black
            NAVER -> Color.White
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

@Preview(showBackground = true)
@Composable fun PreviewLoginScreen() {
    PlacePickTheme {
        LoginScreen()
    }
}
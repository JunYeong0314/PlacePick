package com.jyproject.presentation.ui.feature.common.error

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.presentation.R

@Composable
fun ErrorScreen(
    exception: Throwable?,
    onClickErrorSend: (String) -> Unit,
    onClickClear: () -> Unit
) {
    Scaffold(
        topBar = { TopBar(onClickClear = onClickClear) }
    ) { innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(0.85f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(72.dp),
                    imageVector = Icons.Default.Warning,
                    contentDescription = "wifi_off",
                    tint = Color.LightGray
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "앗! 화면을 불러올 수 없어요",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            }
            Button(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 24.dp)
                    .fillMaxWidth()
                ,
                onClick = { onClickErrorSend(exception?.message.toString()) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.app_base)
                )
            ) {
                Text(
                    text = "에러로그 보내기",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
private fun TopBar(
    onClickClear: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(Color.White),
    ){
        Icon(
            modifier = Modifier
                .size(48.dp)
                .padding(start = 8.dp, top = 8.dp)
                .clickable{ onClickClear() },
            imageVector = Icons.Default.Clear,
            contentDescription = "clear",
            tint = Color.LightGray,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewErrorScreen(){
    ErrorScreen(onClickErrorSend = {}, exception = java.lang.Exception("TMP"), onClickClear = {})
}
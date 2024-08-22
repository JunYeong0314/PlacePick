package com.jyproject.presentation.ui.feature.common.error

import android.content.Context
import android.content.Intent
import android.provider.Settings.ACTION_WIFI_SETTINGS
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.util.modifierExtensions.singleClick.clickableSingle

@Composable
fun NetworkErrorScreen(
    onClickRetry: () -> Unit,
    onClickClear: () -> Unit
){
    val context = LocalContext.current

    Scaffold(
        topBar = { NetWorkTopBar(onClickClear = onClickClear) }
    ) { innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(62.dp),
                painter = painterResource(id = R.drawable.ic_wifi_off),
                contentDescription = "wifi_off",
                tint = Color.LightGray
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "앗! 네트워크 상황이 좋지않아요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.size(18.dp))
            Row {
                SolutionButton(
                    placeHolder = "네트워크 설정",
                    onClickButton = { networkIntent(context) }
                )
                Spacer(modifier = Modifier.size(12.dp))
                SolutionButton(placeHolder = "재시도", onClickButton = onClickRetry)
            }
        }
        
    }
}

@Composable
fun SolutionButton(
    placeHolder: String,
    onClickButton: () -> Unit
){
    Text(
        modifier = Modifier
            .border(
                width = 0.5.dp,
                color = Color.DarkGray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickableSingle { onClickButton() },
        text = placeHolder,
        fontSize = 16.sp,
        color = Color.DarkGray
    )
}

private fun networkIntent(context: Context){
    val intent = Intent(ACTION_WIFI_SETTINGS)
    startActivity(context, intent, null)
}

@Composable
private fun NetWorkTopBar(
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
                .clickable { onClickClear() },
            imageVector = Icons.Default.Clear,
            contentDescription = "clear",
            tint = Color.LightGray,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewNetworkErrorScreen(){
    NetworkErrorScreen(onClickRetry = {}, onClickClear = {})
}
package com.jyproject.presentation.ui.feature.placeDetail.composable.seoulbike

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import com.jyproject.presentation.ui.theme.PlacePickTheme

@Composable
fun NoPermission(
    onClickPermission: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = Icons.Default.Info,
            contentDescription = "location",
            tint = Color.LightGray
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "위치정보 권한을 설정해 따릉이 정보를 확인해보세요!",
            fontWeight = FontWeight.Bold,
            color = Color.LightGray,
            fontSize = 12.sp
        )
        Button(
            modifier = Modifier
                .padding(horizontal = 72.dp, vertical = 2.dp)
                .fillMaxWidth()
            ,
            onClick = { onClickPermission() },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.app_base)
            )
        ) {
            Text(
                text = "위치권한 설정하기",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewNoPermission(){
    PlacePickTheme {
        NoPermission(onClickPermission = {})
    }
}
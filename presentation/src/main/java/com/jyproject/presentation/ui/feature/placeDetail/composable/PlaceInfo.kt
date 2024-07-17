package com.jyproject.presentation.ui.feature.placeDetail.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailContract

@Composable
fun PlaceInfo(state: PlaceDetailContract.State,) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.ic_app),
            contentDescription = "icon",
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .shadow(elevation = 24.dp, shape = RoundedCornerShape(0.05.dp))
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 2.dp)
                .padding(start = 6.dp, end = 8.dp)
                .width(250.dp),
            text = state.placeInfo?.livePeopleInfoMsg ?: "불러오는 중...",
            fontSize = 12.sp
        )
    }
}
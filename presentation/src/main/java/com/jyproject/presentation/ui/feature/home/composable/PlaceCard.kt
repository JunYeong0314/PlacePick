package com.jyproject.presentation.ui.feature.home.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.feature.home.HomeContract

@Composable
fun PlaceCard(
    place: String,
    painterId: Int,
    onEventSend: (event: HomeContract.Event) -> Unit,
){
    Box(
        modifier = Modifier
            .width(300.dp)
            .height(500.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(4.dp),
                color = colorResource(id = R.color.app_base)
            )
            .clickable { onEventSend(HomeContract.Event.NavigationToPlaceDetail(place)) }
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth()
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 40.dp, horizontal = 18.dp)
                    ,
                    text = place,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 35.sp,
                    color = colorResource(id = R.color.app_base),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Image(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                ,
                painter = painterResource(id = painterId),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}
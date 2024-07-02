package com.jyproject.presentation.ui.feature.placeAdd.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.presentation.R

@Composable
fun PlaceAddTitle(
    place: String
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        val placeText = if(place.length > 6) place.substring(0, 6) + ".." else place
        Row {
            Text(
                text = placeText,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.app_base),
                fontSize = 32.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "을(를)",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
        }
        Text(
            text = "추가하시겠어요?",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
    }
}
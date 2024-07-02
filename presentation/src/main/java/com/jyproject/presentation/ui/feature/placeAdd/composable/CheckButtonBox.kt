package com.jyproject.presentation.ui.feature.placeAdd.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.feature.placeAdd.PlaceAddContract
import kotlinx.coroutines.launch

@Composable
fun CheckButtonBox(
    place: String,
    onEventSend: (event: PlaceAddContract.Event) -> Unit,
){
    Button(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
        ,
        onClick = { onEventSend(PlaceAddContract.Event.OnPlaceAdd(place)) },
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.app_base)
        )
    ) {
        Text(text = "네, 추가할래요!", fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.size(4.dp))
    Button(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
        ,
        onClick = { onEventSend(PlaceAddContract.Event.NavigateToBack) },
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray
        )
    ) {
        Text(text = "아니요, 다음에 할게요!", fontWeight = FontWeight.Bold)
    }
}
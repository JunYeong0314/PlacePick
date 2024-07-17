package com.jyproject.presentation.ui.feature.placeDetail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.jyproject.presentation.ui.feature.placeDetail.composable.AgeChart
import com.jyproject.presentation.ui.feature.placeDetail.composable.PlaceInfo
import com.jyproject.presentation.ui.feature.placeDetail.composable.TopBar
import kotlinx.coroutines.flow.Flow

@Composable
fun PlaceDetailScreen(
    place: String?,
    state: PlaceDetailContract.State,
    effectFlow: Flow<PlaceDetailContract.Effect>?,
    onEventSend: (event: PlaceDetailContract.Event) -> Unit,
    onEffectSend: (effect: PlaceDetailContract.Effect) -> Unit
){
    LaunchedEffect(effectFlow) {
        effectFlow?.collect { effect->
            when(effect) {
                is PlaceDetailContract.Effect.Navigation.ToBack ->
                    onEffectSend(PlaceDetailContract.Effect.Navigation.ToBack)
                is PlaceDetailContract.Effect.Navigation.ToMain ->
                    onEffectSend(PlaceDetailContract.Effect.Navigation.ToMain)
            }
        }
    }

    place?.let {
        Scaffold(
            modifier = Modifier.background(Color.White),
            topBar = {
                TopBar(
                    place = place,
                    state = state,
                    onEventSend = onEventSend,
                )
            }
        ) { innerPadding->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color.White)
                    .fillMaxSize()
            ) {
                PlaceInfo(state = state)
                AgeChart()
            }
        }
    }
}





package com.jyproject.presentation.ui.feature.placeDetail

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jyproject.presentation.ui.feature.placeDetail.composable.AgeChart
import com.jyproject.presentation.ui.feature.placeDetail.composable.PlaceInfo
import com.jyproject.presentation.ui.feature.placeDetail.composable.TopBar
import com.jyproject.presentation.ui.feature.placeDetail.composable.seoulbike.SeoulBikeLocation
import kotlinx.coroutines.flow.Flow

@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION]
)
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
                    .fillMaxSize(),
            ) {
                PlaceInfo(state = state)
                Spacer(modifier = Modifier.size(36.dp))
                AgeChart(state = state)
                Spacer(modifier = Modifier.size(24.dp))
                SeoulBikeLocation(state = state)
            }
        }
    }
}





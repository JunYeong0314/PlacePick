package com.jyproject.presentation.ui.feature.mapDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.util.modifierExtensions.singleClick.clickableSingle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.NaverMapConstants
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapDetailScreen(
    placeArea: String,
    state: MapDetailContract.State,
    effectFlow: Flow<MapDetailContract.Effect>?,
    onEventSend: (event: MapDetailContract.Event) -> Unit,
    onEffectSend: (effect: MapDetailContract.Effect) -> Unit
) {
    LaunchedEffect(effectFlow) {
        effectFlow?.collect { collect ->
            when(collect) {
                is MapDetailContract.Effect.Navigation.ToBack ->
                    onEffectSend(MapDetailContract.Effect.Navigation.ToBack)
            }
        }
    }

    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                pickTolerance = NaverMapConstants.DefaultPickTolerance,
                isZoomControlEnabled = false,
                isScaleBarEnabled = false,
            )
        )
    }
    val initLocation = LatLng(state.seoulBikeInfo[0].latitude!!, state.seoulBikeInfo[0].longitude!!)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(initLocation, 12.0)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState
        ) {
            state.seoulBikeInfo.map {
                Marker(
                    width = 18.dp,
                    height = 18.dp,
                    icon = OverlayImage.fromResource(R.drawable.ic_seoulbike),
                    state = MarkerState(position = LatLng(it.latitude!!, it.longitude!!)),
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 8.dp, top = 8.dp)
                .shadow(elevation = 24.dp, shape = RoundedCornerShape(1.dp))
                .size(40.dp)
                .background(Color.White, shape = CircleShape)
                .clickableSingle { onEventSend(MapDetailContract.Event.NavigationToBack) }
        ){
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                tint = Color.DarkGray
            )
        }
    }
}
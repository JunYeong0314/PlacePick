package com.jyproject.presentation.ui.feature.placeDetail.composable.seoulbike

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailContract
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.NaverMapConstants
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.overlay.OverlayImage

@OptIn(ExperimentalNaverMapApi::class)
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION]
)
@Composable
fun MapScreen(
    state: PlaceDetailContract.State,
    onEventSend: (event: PlaceDetailContract.Event) -> Unit,
){
    if(!state.seoulBikeInfo.isNullOrEmpty()){
        val initLocation = LatLng(state.seoulBikeInfo[0].latitude!!, state.seoulBikeInfo[0].longitude!!)
        val cameraPositionState: CameraPositionState = rememberCameraPositionState {
            position = CameraPosition(initLocation, 11.0)
        }

        NaverMap(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            locationSource = rememberFusedLocationSource(),
            properties = MapProperties(
                locationTrackingMode = LocationTrackingMode.NoFollow
            ),
            uiSettings = MapUiSettings(
                pickTolerance = NaverMapConstants.DefaultPickTolerance,
                isZoomControlEnabled = false,
                isScaleBarEnabled = false,
            ),
            cameraPositionState = cameraPositionState,
            onMapClick = {_, _->
                onEventSend(PlaceDetailContract.Event.NavigationToMap)
            }
        ){
            state.seoulBikeInfo.map {
                Marker(
                    width = 14.dp,
                    height = 14.dp,
                    icon = OverlayImage.fromResource(R.drawable.ic_seoulbike),
                    state = MarkerState(position = LatLng(it.latitude!!, it.longitude!!)),
                )
            }
        }
    }else{
        Column(
            modifier = Modifier
                .background(color = colorResource(id = R.color.light_gray_weak1))
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "플레이스 픽",
                fontWeight = FontWeight.ExtraBold,
                color = colorResource(id = R.color.light_gray_middle1),
                fontSize = 28.sp
            )
        }
    }
}
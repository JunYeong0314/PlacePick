package com.jyproject.presentation.ui.feature.placeAdd

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jyproject.presentation.anim.LottieAddPlaceCheck
import com.jyproject.presentation.ui.feature.placeAdd.composable.CheckButtonBox
import com.jyproject.presentation.ui.feature.placeAdd.composable.PlaceAddTitle
import kotlinx.coroutines.flow.Flow

@Composable
fun PlaceAddScreen(
    place: String,
    state: PlaceAddContract.State,
    effectFlow: Flow<PlaceAddContract.Effect>?,
    onEventSend: (event: PlaceAddContract.Event) -> Unit,
    onEffectSend: (effect: PlaceAddContract.Effect) -> Unit
){
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(effectFlow) {
        effectFlow?.collect { effect ->
            when(effect) {
                is PlaceAddContract.Effect.Navigation.ToBack ->
                    onEffectSend(PlaceAddContract.Effect.Navigation.ToBack)
                is PlaceAddContract.Effect.Navigation.ToMain ->
                    onEffectSend(PlaceAddContract.Effect.Navigation.ToMain)
            }
        }
    }

    LaunchedEffect(state) {
        when(state.placeAddState) {
            PlaceAddState.INIT -> {}
            PlaceAddState.SUCCESS -> onEventSend(PlaceAddContract.Event.NavigateToHome)
            PlaceAddState.DUPLICATE ->
                snackBarHostState.showSnackbar(
                    message = "이미 등록되어있는 장소입니다.",
                    duration = SnackbarDuration.Short
                )
            PlaceAddState.ERROR ->
                snackBarHostState.showSnackbar(
                    message = "[Error] 장소를 추가 할 수 없습니다.",
                    duration = SnackbarDuration.Short
                )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding->
        Icon(
            modifier = Modifier
                .size(42.dp)
                .clickable { onEventSend(PlaceAddContract.Event.NavigateToBack) }
                .padding(top = 8.dp)
            ,
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center
        ) {
            PlaceAddTitle(place = place)
            LottieAddPlaceCheck(modifier = Modifier.height(300.dp))
            CheckButtonBox(
                place = place,
                onEventSend = onEventSend
            )
        }
    }
}
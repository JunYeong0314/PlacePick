package com.jyproject.presentation.ui.feature.placeAdd

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jyproject.presentation.R
import com.jyproject.presentation.anim.LottieAddPlaceCheck
import com.jyproject.presentation.di.ViewModelFactoryProvider
import com.jyproject.presentation.ui.feature.placeAdd.composable.CheckButtonBox
import com.jyproject.presentation.ui.feature.placeAdd.composable.PlaceAddTitle
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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
        when {
            state.isDuplicate -> {
                snackBarHostState.showSnackbar(
                    message = "이미 등록되어있는 장소입니다.",
                    duration = SnackbarDuration.Short
                )
            }
            state.isError -> {
                snackBarHostState.showSnackbar(
                    message = "[Error] 장소를 추가 할 수 없습니다.",
                    duration = SnackbarDuration.Short
                )
            }
            state.isAddPlace -> {
                onEventSend(PlaceAddContract.Event.NavigateToHome)
            }
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
package com.jyproject.presentation.ui.feature.placeSearch

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.feature.placeSearch.composable.PlaceListEmpty
import com.jyproject.presentation.ui.feature.placeSearch.composable.PlaceSearchBox
import com.jyproject.presentation.ui.feature.placeSearch.composable.PlaceSearchResult
import kotlinx.coroutines.flow.Flow

@Composable
fun PlaceSearchScreen(
    state: PlaceSearchContract.State,
    effectFlow: Flow<PlaceSearchContract.Effect>?,
    onEventSend: (event: PlaceSearchContract.Event) -> Unit,
    onEffectSend: (effect: PlaceSearchContract.Effect) -> Unit
){
    val focusManager = LocalFocusManager.current
    var searchText by remember { mutableStateOf("") }
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.isError) {
        if(state.isError) {
            snackBarHostState.showSnackbar(
                message = "장소를 검색할 수 없습니다.",
                duration = SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(searchText) {
        if(searchText.isNotBlank()) onEventSend(PlaceSearchContract.Event.OnPlaceSearchText(searchText))
    }

    LaunchedEffect(effectFlow) {
        effectFlow?.collect { effect ->
            when(effect) {
                is PlaceSearchContract.Effect.Navigation.ToBack ->
                    onEffectSend(PlaceSearchContract.Effect.Navigation.ToBack)
                is PlaceSearchContract.Effect.Navigation.ToPlaceAdd ->
                    onEffectSend(PlaceSearchContract.Effect.Navigation.ToPlaceAdd(effect.place))
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValue->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues = paddingValue)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    focusManager.clearFocus()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { onEventSend(PlaceSearchContract.Event.NavigateToBack) }
                    ,
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = colorResource(id = R.color.light_gray_hard1)
                )
                PlaceSearchBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp)
                        .padding(end = 10.dp)
                        .background(
                            color = colorResource(id = R.color.light_gray_weak1),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .height(50.dp),
                    focusManager = focusManager,
                    value = searchText,
                    onValueChange = { searchText = it }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when {
                    state.placeList.isEmpty() -> PlaceListEmpty()
                    else -> PlaceSearchResult(placeList = state.placeList, onEventSend = onEventSend)
                }
            }
        }
    }
}


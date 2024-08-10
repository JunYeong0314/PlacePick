package com.jyproject.presentation.ui.feature.home
import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jyproject.presentation.ui.feature.home.composable.PlaceBlocks
import kotlinx.coroutines.flow.Flow

@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION]
)
@Composable
fun HomeScreen(
    state: HomeContract.State,
    effectFlow: Flow<HomeContract.Effect>?,
    onEventSend: (event: HomeContract.Event) -> Unit,
    onEffectSend: (effect: HomeContract.Effect.Navigation) -> Unit
){
    LaunchedEffect(effectFlow) {
        effectFlow?.collect { effect ->
            when(effect) {
                is HomeContract.Effect.Navigation -> { onEffectSend(effect) }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        MapScreen()
        PlaceBlocks(
            onEventSend = onEventSend,
            state = state,
            onClickAddBtn = { onEventSend(HomeContract.Event.NavigationToPlaceSearch) }
        )
    }
}




package com.jyproject.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.presentation.ui.feature.placeSearch.PlaceSearchContract
import com.jyproject.presentation.ui.feature.placeSearch.PlaceSearchScreen
import com.jyproject.presentation.ui.feature.placeSearch.PlaceSearchViewModel

@Composable
fun PlaceSearchScreenDestination(
    navController: NavController,
    viewModel: PlaceSearchViewModel = hiltViewModel()
) {
    PlaceSearchScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSend = viewModel::setEvent,
        onEffectSend = { effect ->
            when(effect) {
                is PlaceSearchContract.Effect.Navigation.ToBack -> navController.navigateUp()
                is PlaceSearchContract.Effect.Navigation.ToPlaceAdd ->
                    navController.navigateToPlaceAdd(effect.place)
            }

        }
    )

}
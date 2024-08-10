package com.jyproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.presentation.ui.feature.placeAdd.PlaceAddContract
import com.jyproject.presentation.ui.feature.placeAdd.PlaceAddScreen
import com.jyproject.presentation.ui.feature.placeAdd.PlaceAddState
import com.jyproject.presentation.ui.feature.placeAdd.PlaceAddViewModel

@Composable
fun PlaceAddScreenDestination(
    place: String,
    navController: NavController,
    viewModel: PlaceAddViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel.viewState.value.placeAddState) {
        if(viewModel.viewState.value.placeAddState == PlaceAddState.INIT){
            viewModel.searchPlaceArea(placeName = place)
        }
    }

    PlaceAddScreen(
        place = place,
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSend = viewModel::setEvent,
        onEffectSend = { effect ->
            when(effect) {
                is PlaceAddContract.Effect.Navigation.ToBack ->
                    navController.navigateUp()
                is PlaceAddContract.Effect.Navigation.ToMain -> {
                    navController.popBackStack(
                        route = Navigation.Routes.HOME,
                        inclusive = true
                    )
                    navController.navigate(Navigation.Routes.HOME)
                }
            }
        }
    )

}
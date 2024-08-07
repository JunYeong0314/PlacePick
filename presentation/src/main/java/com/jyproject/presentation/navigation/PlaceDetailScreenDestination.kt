package com.jyproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailContract
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailScreen
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailViewModel
import com.jyproject.presentation.ui.feature.placeDetail.PlaceInfoState

@Composable
fun PlaceDetailScreenDestination(
    place: String,
    navController: NavController,
    viewModel: PlaceDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel.viewState.value.placeInfoState) {
        if(viewModel.viewState.value.placeInfoState == PlaceInfoState.INIT) {
            viewModel.getPlaceDBInfo(place)
            viewModel.getPlaceInfo(place)
        }
    }

    PlaceDetailScreen(
        place = place,
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSend = viewModel::setEvent,
        onEffectSend = { effect->
            when(effect) {
                is PlaceDetailContract.Effect.Navigation.ToBack ->
                    navController.navigateUp()
                is PlaceDetailContract.Effect.Navigation.ToMain -> {
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
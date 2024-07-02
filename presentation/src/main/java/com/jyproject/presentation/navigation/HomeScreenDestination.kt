package com.jyproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.presentation.ui.feature.home.HomeContract
import com.jyproject.presentation.ui.feature.home.HomeScreen
import com.jyproject.presentation.ui.feature.home.HomeViewModel

@Composable
fun HomeScreenDestination(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSend = viewModel::setEvent,
        onEffectSend = { effect ->
            when(effect){
                is HomeContract.Effect.Navigation.ToPlaceDetail -> {
                    navController.navigateToPlaceDetail(effect.place)
                }
                is HomeContract.Effect.Navigation.ToPlaceSearch -> {
                    navController.navigate(Navigation.Routes.PLACE_SEARCH)
                }
            }
        }
    )

}
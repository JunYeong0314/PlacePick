package com.jyproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.presentation.ui.feature.common.error.ErrorScreen
import com.jyproject.presentation.ui.feature.common.error.NetworkErrorScreen
import com.jyproject.presentation.ui.feature.common.util.CircularProgress
import com.jyproject.presentation.ui.feature.mapDetail.MapDetailContract
import com.jyproject.presentation.ui.feature.mapDetail.MapDetailScreen
import com.jyproject.presentation.ui.feature.mapDetail.MapDetailState
import com.jyproject.presentation.ui.feature.mapDetail.MapDetailViewModel

@Composable
fun MapDetailScreenDestination(
    placeArea: String,
    navController: NavController,
    viewModel: MapDetailViewModel = hiltViewModel()
){
    when(viewModel.viewState.value.mapDetailState) {
        MapDetailState.INIT -> {
            viewModel.getSeoulBikeLocationInfo(regionName = placeArea)
        }
        MapDetailState.LOADING -> {
            CircularProgress()
        }
        MapDetailState.SUCCESS -> {
            MapDetailScreen(
                placeArea = placeArea,
                state = viewModel.viewState.value,
                effectFlow = viewModel.effect,
                onEventSend = viewModel::setEvent,
                onEffectSend = { effect ->
                    when(effect) {
                        is MapDetailContract.Effect.Navigation.ToBack ->
                            navController.navigateUp()
                    }
                }
            )
        }
        MapDetailState.ERROR -> {
            ErrorScreen(
                exception = viewModel.viewState.value.errorThrowable,
                onClickErrorSend = {},
                onClickClear = { navController.navigateUp() }
            )
        }
        MapDetailState.NETWORK_ERROR -> {
            NetworkErrorScreen(onClickRetry = {}, onClickClear = {})
        }
    }
}
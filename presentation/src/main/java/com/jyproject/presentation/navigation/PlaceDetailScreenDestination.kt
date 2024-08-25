package com.jyproject.presentation.navigation

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.presentation.ui.feature.common.error.ErrorScreen
import com.jyproject.presentation.ui.feature.common.error.NetworkErrorScreen
import com.jyproject.presentation.ui.feature.common.util.CircularProgress
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailContract
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailScreen
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailViewModel
import com.jyproject.presentation.ui.feature.placeDetail.PlaceInfoState

@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION]
)
@Composable
fun PlaceDetailScreenDestination(
    place: String,
    navController: NavController,
    viewModel: PlaceDetailViewModel = hiltViewModel()
) {
    when(viewModel.viewState.value.placeInfoState){
        PlaceInfoState.INIT -> {
            viewModel.getPlaceDBInfo(place)
            viewModel.getPlaceInfo(place)
        }
        PlaceInfoState.LOADING -> CircularProgress()
        PlaceInfoState.ERROR -> { 
            ErrorScreen(
                exception = viewModel.viewState.value.errorThrowable,
                onClickErrorSend = {  },
                onClickClear = { navController.navigateUp() }
            )
        }
        PlaceInfoState.NETWORK_ERROR -> {
            NetworkErrorScreen(
                onClickRetry = {
                    viewModel.getPlaceDBInfo(place)
                    viewModel.getPlaceInfo(place)
                },
                onClickClear = { navController.navigateUp() }
            )
        }
        PlaceInfoState.SUCCESS -> {
            LaunchedEffect(viewModel.viewState.value.seoulBikeInfo) {
                if(!viewModel.viewState.value.placeAreaInfo?.placeArea.isNullOrBlank() &&
                    viewModel.viewState.value.seoulBikeInfo.isNullOrEmpty()) {
                    viewModel.getSeoulBikeLocationInfo(
                        viewModel.viewState.value.placeAreaInfo?.placeArea
                    )
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
                        is PlaceDetailContract.Effect.Navigation.ToMap -> {
                            viewModel.viewState.value.placeAreaInfo?.placeArea?.let {
                                navController.navigateToMapDetail(placeArea = it)
                            }
                        }
                    }
                }
            )
        }
    }
}
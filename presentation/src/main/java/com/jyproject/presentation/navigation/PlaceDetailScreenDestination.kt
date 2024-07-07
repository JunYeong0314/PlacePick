package com.jyproject.presentation.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jyproject.presentation.di.ViewModelFactoryProvider
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailContract
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailScreen
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailViewModel
import dagger.hilt.android.EntryPointAccessors

@Composable
fun PlaceDetailScreenDestination(
    place: String,
    navController: NavController
) {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).placeDetailViewModelFactory()
    val viewModel: PlaceDetailViewModel = viewModel(
        factory = PlaceDetailViewModel.providePlaceDetailViewModelFactory(factory, place ?: "")
    )

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
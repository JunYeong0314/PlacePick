package com.jyproject.presentation.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jyproject.presentation.di.ViewModelFactoryProvider
import com.jyproject.presentation.ui.feature.placeAdd.PlaceAddContract
import com.jyproject.presentation.ui.feature.placeAdd.PlaceAddScreen
import com.jyproject.presentation.ui.feature.placeAdd.PlaceAddViewModel
import dagger.hilt.android.EntryPointAccessors

@Composable
fun PlaceAddScreenDestination(
    place: String,
    navController: NavController
) {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).placeAddViewModelFactory()

    val viewModel: PlaceAddViewModel = viewModel(
        factory = PlaceAddViewModel.providePlaceAddViewModelFactory(
            factory,
            place
        )
    )

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
package com.jyproject.presentation.navigation.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.presentation.navigation.Navigation
import com.jyproject.presentation.ui.feature.register.RegisterContract
import com.jyproject.presentation.ui.feature.register.RegisterScreen
import com.jyproject.presentation.ui.feature.register.RegisterViewModel

@Composable
fun RegisterScreenDestination(
    userNum: String,
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    RegisterScreen(
        userNum = userNum,
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSend = viewModel::setEvent,
        onEffectSend = { effect ->
            when(effect) {
                is RegisterContract.Effect.Navigate.ToBack -> {
                    navController.navigate(route = Navigation.Routes.LOGIN){
                        popUpTo(Navigation.Routes.REGISTER){ inclusive = true }
                    }
                }
                is RegisterContract.Effect.Navigate.ToMain -> {
                    navController.navigate(route = Navigation.Routes.HOME){
                        popUpTo(Navigation.Routes.REGISTER){ inclusive = true }
                    }
                }
            }
        }
    )

}
package com.jyproject.presentation.navigation.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
        onEffectSend = {

        }
    )

}
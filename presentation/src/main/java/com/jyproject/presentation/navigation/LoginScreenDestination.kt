package com.jyproject.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jyproject.domain.models.LoginState
import com.jyproject.presentation.ui.feature.login.LoginContract
import com.jyproject.presentation.ui.feature.login.LoginScreen
import com.jyproject.presentation.ui.feature.login.LoginViewModel

@Composable
fun LoginScreenDestination(
    context: Context,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel.viewState.value) {
        if(viewModel.viewState.value.loginState == LoginState.BLANK) viewModel.initSetting()
    }
    LoginScreen(
        context = context,
        effectFlow = viewModel.effect,
        state = viewModel.viewState.value,
        onEventSend = viewModel::setEvent,
        onEffectSend = { effect ->
            when(effect){
                is LoginContract.Effect.Navigation.ToMainScreen -> {
                    navController.navigate(route = Navigation.Routes.HOME){
                        popUpTo(Navigation.Routes.LOGIN){ inclusive = true }
                    }
                }
            }
        }
    )
}
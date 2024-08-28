package com.jyproject.presentation.ui.feature.login

import android.content.Context
import com.jyproject.presentation.ui.base.ViewEvent
import com.jyproject.presentation.ui.base.ViewSideEffect
import com.jyproject.presentation.ui.base.ViewState

class LoginContract {
    sealed class Event: ViewEvent {
        data object Retry: Event()
        data object NavigationToMain: Event()
        data object KakaoLogin: Event()
        data class NaverLogin(val context: Context): Event()
        data class NavigationToRegister(val userNum: String): Event()
    }

    data class State(
        val userNum: String?,
        val loginState: LoginState
    ): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {
            data object ToMainScreen: Navigation()
            data class ToRegisterScreen(val userNum: String): Navigation()
        }
    }
}
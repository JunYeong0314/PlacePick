package com.jyproject.presentation.ui.feature.login

import android.content.Context
import com.jyproject.domain.models.LoginState
import com.jyproject.presentation.ui.base.ViewEvent
import com.jyproject.presentation.ui.base.ViewSideEffect
import com.jyproject.presentation.ui.base.ViewState

class LoginContract {
    sealed class Event: ViewEvent {
        data object Retry: Event()
        data object NavigationToMain: Event()
        data object KakaoLogin: Event()
        data class NaverLogin(val context: Context): Event()
        data object SignUp: Event()
    }

    data class State(
        val loginState: LoginState
    ): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {
            data object ToMainScreen: Navigation()
        }
    }
}
package com.jyproject.presentation.ui.feature.register

import com.jyproject.presentation.ui.base.ViewEvent
import com.jyproject.presentation.ui.base.ViewSideEffect
import com.jyproject.presentation.ui.base.ViewState

class RegisterContract {
    sealed class Event: ViewEvent {
        data class OnNickCheck(val nick: String): Event()
        data object NavigateToMain: Event()
        data object NavigateToBack: Event()
        data class OnRegisterRequest(val userNum: String, val nick: String): Event()
    }

    data class State(
        val nick: String?,
        val registerState: RegisterState,
        val checkNickResponseCode: Int?
    ): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigate: Effect() {
            data object ToMain: Navigate()
            data object ToBack: Navigate()
        }
    }
}
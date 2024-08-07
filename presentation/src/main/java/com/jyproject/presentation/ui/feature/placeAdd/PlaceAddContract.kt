package com.jyproject.presentation.ui.feature.placeAdd

import com.jyproject.presentation.ui.base.ViewEvent
import com.jyproject.presentation.ui.base.ViewSideEffect
import com.jyproject.presentation.ui.base.ViewState

class PlaceAddContract {
    sealed class Event: ViewEvent {
        data class OnPlaceAdd(val place: String): Event()
        data object NavigateToHome: Event()
        data object NavigateToBack: Event()
    }

    data class State(
        val placeAddState: PlaceAddState
    ): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {
            data object ToMain: Navigation()
            data object ToBack: Navigation()
        }
    }
}
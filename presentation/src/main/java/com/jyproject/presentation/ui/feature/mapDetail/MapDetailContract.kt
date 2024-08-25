package com.jyproject.presentation.ui.feature.mapDetail

import com.jyproject.domain.models.SeoulBike
import com.jyproject.presentation.ui.base.ViewEvent
import com.jyproject.presentation.ui.base.ViewSideEffect
import com.jyproject.presentation.ui.base.ViewState

class MapDetailContract {
    sealed class Event: ViewEvent {
        data object NavigationToBack: Event()
    }

    data class State(
        val mapDetailState: MapDetailState,
        val seoulBikeInfo: List<SeoulBike>,
        val errorThrowable: Throwable?
    ): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {
            data object ToBack: Navigation()
        }
    }
}
package com.jyproject.presentation.ui.feature.home

import com.jyproject.domain.models.Place
import com.jyproject.presentation.ui.base.ViewEvent
import com.jyproject.presentation.ui.base.ViewSideEffect
import com.jyproject.presentation.ui.base.ViewState

class HomeContract {
    sealed class Event: ViewEvent {
        data class NavigationToPlaceDetail(val place: String): Event()
        data object NavigationToPlaceSearch: Event()
        data class DeletePlace(val place: String): Event()
    }

    data class State(
        val placeList: List<Place>,
        val placeState: PlaceState,
    ): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {
            data class ToPlaceDetail(val place: String): Navigation()
            data object ToPlaceSearch: Navigation()
        }
    }
}
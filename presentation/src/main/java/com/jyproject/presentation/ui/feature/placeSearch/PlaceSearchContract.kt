package com.jyproject.presentation.ui.feature.placeSearch

import com.jyproject.domain.models.Place
import com.jyproject.presentation.ui.base.ViewEvent
import com.jyproject.presentation.ui.base.ViewSideEffect
import com.jyproject.presentation.ui.base.ViewState

class PlaceSearchContract {
    sealed class Event: ViewEvent {
        data object Retry: Event()
        data class OnPlaceSearchText(val search: String): Event()
        data class NavigateToPlaceAdd(val place: String): Event()
        data object NavigateToBack: Event()
    }

    data class State(
        val placeList: List<Place>,
        val searchState: PlaceSearchState,
        val searchStateMsg: String
    ): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {
            data class ToPlaceAdd(val place: String): Navigation()
            data object ToBack: Navigation()
        }
    }

}
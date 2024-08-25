package com.jyproject.presentation.ui.feature.placeDetail

import com.jyproject.domain.models.Place
import com.jyproject.domain.models.PlaceInfo
import com.jyproject.domain.models.SeoulBike
import com.jyproject.presentation.ui.base.ViewEvent
import com.jyproject.presentation.ui.base.ViewSideEffect
import com.jyproject.presentation.ui.base.ViewState

class PlaceDetailContract {
    sealed class  Event: ViewEvent {
        data object Retry: Event()
        data class DeletePlace(val place: String): Event()
        data object NavigationToMain: Event()
        data object NavigationToBack: Event()
        data object NavigationToMap: Event()
    }

    data class State(
        val placeInfo: PlaceInfo?,
        val placeAreaInfo: Place?,
        val placeInfoState: PlaceInfoState,
        val placeStateColor: Int,
        val seoulBikeInfo: List<SeoulBike>?,
        val errorThrowable: Throwable?
    ): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {
            data object ToMain: Navigation()
            data object ToBack: Navigation()
            data object ToMap: Navigation()
        }
    }
}
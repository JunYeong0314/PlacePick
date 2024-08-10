package com.jyproject.presentation.ui.feature.placeSearch

import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.place.PlaceRepository
import com.jyproject.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceSearchViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
): BaseViewModel<PlaceSearchContract.Event, PlaceSearchContract.State, PlaceSearchContract.Effect>() {
    override fun setInitialState() = PlaceSearchContract.State(
        placeList = emptyList(),
        searchState = PlaceSearchState.INIT,
        searchStateMsg = "검색결과가 없습니다."
    )
    override fun handleEvents(event: PlaceSearchContract.Event) {
        when(event) {
            is PlaceSearchContract.Event.Retry -> {}
            is PlaceSearchContract.Event.OnPlaceSearchText -> searchPlace(event.search)
            is PlaceSearchContract.Event.NavigateToPlaceAdd ->
                setEffect { PlaceSearchContract.Effect.Navigation.ToPlaceAdd(event.place) }
            is PlaceSearchContract.Event.NavigateToBack ->
                setEffect { PlaceSearchContract.Effect.Navigation.ToBack }
        }
    }

    private fun searchPlace(placeName: String){
        setState { copy(searchState = PlaceSearchState.LOADING) }
        viewModelScope.launch {
            placeRepository.searchPlace(placeName)
                .onFailure { exception ->
                    when(exception){
                        is java.net.UnknownHostException -> setState {
                            copy(
                                searchState = PlaceSearchState.NETWORK_ERROR,
                                searchStateMsg = "네트워크 연결이 불안정합니다"
                            )
                        }
                        else -> setState {
                            copy(
                                searchState = PlaceSearchState.ERROR,
                                searchStateMsg = "[Error] 관리자에게 문의하세요."
                            )
                        }
                    }
                }
                .onSuccess { placeList->
                    when {
                        placeList.isNullOrEmpty() -> {
                            setState { copy(placeList = emptyList(), searchState = PlaceSearchState.EMPTY) }
                        }
                        placeList.isNotEmpty() -> {
                            setState { copy(placeList = placeList, searchState = PlaceSearchState.SUCCESS) }
                        }
                    }
                }
        }
    }


}
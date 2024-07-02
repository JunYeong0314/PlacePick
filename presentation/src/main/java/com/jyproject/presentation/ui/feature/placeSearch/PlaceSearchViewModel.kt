package com.jyproject.presentation.ui.feature.placeSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.place.PlaceRepository
import com.jyproject.domain.models.Place
import com.jyproject.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceSearchViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
): BaseViewModel<PlaceSearchContract.Event, PlaceSearchContract.State, PlaceSearchContract.Effect>() {
    override fun setInitialState() = PlaceSearchContract.State(
        placeList = emptyList(),
        isLoading = true,
        isError = false
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
        viewModelScope.launch {
            placeRepository.searchPlace(placeName)
                .onFailure { setState { copy(isError = true) } }
                .onSuccess { placeList->
                    placeList?.let { setState { copy(placeList = placeList) } }
                }
        }
    }


}
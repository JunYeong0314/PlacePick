package com.jyproject.presentation.ui.feature.home

import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.db.usecase.PlaceDeleteUseCase
import com.jyproject.domain.features.db.usecase.PlaceReadUseCase
import com.jyproject.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val placeReadUseCase: PlaceReadUseCase,
    private val placeDeleteUseCase: PlaceDeleteUseCase
): BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {
    override fun setInitialState() = HomeContract.State(
        placeList = emptyList(),
        placeState = PlaceState.INIT
    )

    override fun handleEvents(event: HomeContract.Event) {
        when(event){
            is HomeContract.Event.NavigationToPlaceDetail -> setEffect {
                HomeContract.Effect.Navigation.ToPlaceDetail(
                    event.place
                )
            }
            is HomeContract.Event.NavigationToPlaceSearch -> setEffect { HomeContract.Effect.Navigation.ToPlaceSearch }
            is HomeContract.Event.DeletePlace -> deletePlace(event.place)
        }
    }

    fun getPlaceData(){
        viewModelScope.launch {
            try {
                placeReadUseCase().collect{ place->
                    setState { copy(placeList = place, placeState = PlaceState.SUCCESS) }
                }
            } catch (e: Exception) {
                setState { copy(placeState = PlaceState.ERROR) }
            }
        }
    }

    private fun deletePlace(place: String){
        viewModelScope.launch {
            placeDeleteUseCase(place)
        }
    }
}
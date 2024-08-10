package com.jyproject.presentation.ui.feature.placeAdd

import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.db.usecase.PlaceAddUseCase
import com.jyproject.domain.features.db.usecase.PlaceFindUseCase
import com.jyproject.domain.features.place.usecase.SearchPlaceUseCase
import com.jyproject.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceAddViewModel @Inject constructor(
    private val placeAddUseCase: PlaceAddUseCase,
    private val placeFindUseCase: PlaceFindUseCase,
    private val searchPlaceUseCase: SearchPlaceUseCase
): BaseViewModel<PlaceAddContract.Event, PlaceAddContract.State, PlaceAddContract.Effect>() {

    override fun setInitialState() = PlaceAddContract.State(
        placeAddState = PlaceAddState.INIT
    )

    override fun handleEvents(event: PlaceAddContract.Event) {
        when(event) {
            is PlaceAddContract.Event.OnPlaceAdd -> addPlace(event.place)
            is PlaceAddContract.Event.NavigateToBack ->
                setEffect { PlaceAddContract.Effect.Navigation.ToBack }
            is PlaceAddContract.Event.NavigateToHome ->
                setEffect { PlaceAddContract.Effect.Navigation.ToMain }
        }
    }

    private var placeArea: String? = null

    private fun addPlace(place: String){
        setState { copy(placeAddState = PlaceAddState.INIT) }
        placeArea?.let { placeArea->
            viewModelScope.launch {
                if(!placeFindUseCase(place)){
                    placeAddUseCase(place, placeArea)
                    setState { copy(placeAddState = PlaceAddState.SUCCESS) }
                }else{
                    setState { copy(placeAddState = PlaceAddState.DUPLICATE) }
                }
            }
        }
    }

    fun searchPlaceArea(placeName: String) {
        viewModelScope.launch {
            searchPlaceUseCase(placeName)
                .onFailure { setState { copy(placeAddState = PlaceAddState.ERROR) } }
                .onSuccess {
                    placeArea = it?.firstOrNull()?.placeArea
                }
        }
    }
}
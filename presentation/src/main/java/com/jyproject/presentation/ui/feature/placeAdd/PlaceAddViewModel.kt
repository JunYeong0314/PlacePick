package com.jyproject.presentation.ui.feature.placeAdd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.db.usecase.PlaceAddUseCase
import com.jyproject.domain.features.db.usecase.PlaceFindUseCase
import com.jyproject.domain.features.place.usecase.SearchPlaceUseCase
import com.jyproject.domain.models.PlaceAddState
import com.jyproject.presentation.ui.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaceAddViewModel @AssistedInject constructor(
    @Assisted private val place: String,
    private val placeAddUseCase: PlaceAddUseCase,
    private val placeFindUseCase: PlaceFindUseCase,
    private val searchPlaceUseCase: SearchPlaceUseCase
): BaseViewModel<PlaceAddContract.Event, PlaceAddContract.State, PlaceAddContract.Effect>() {
    init {
        viewModelScope.launch {
            searchPlaceArea(place)
        }
    }

    override fun setInitialState() = PlaceAddContract.State(
        placeAddState = PlaceAddState.LOADING
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
        setState { copy(placeAddState = PlaceAddState.LOADING) }
        placeArea?.let { placeArea->
            viewModelScope.launch {
                if(!checkDupPlace(place)) {
                    placeAddUseCase(place, placeArea)
                    setState { copy(placeAddState = PlaceAddState.SUCCESS) }
                }else{
                    setState { copy(placeAddState = PlaceAddState.DUPLICATE) }
                }
            }
        }
    }

    private suspend fun checkDupPlace(place: String): Boolean{
        return placeFindUseCase(place)
    }

    private suspend fun searchPlaceArea(placeName: String) {
        setState { copy(placeAddState = PlaceAddState.LOADING) }
        searchPlaceUseCase(placeName)
            .onFailure { setState { copy(placeAddState = PlaceAddState.ERROR) } }
            .onSuccess {
                placeArea = it?.firstOrNull()?.placeArea
            }
    }

    @AssistedFactory
    interface PlaceAddViewModelFactory {
        fun create(place: String): PlaceAddViewModel
    }

    companion object {
        fun providePlaceAddViewModelFactory(
            factory: PlaceAddViewModelFactory,
            place: String
        ): ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(place) as T
                }
            }
        }
    }


}
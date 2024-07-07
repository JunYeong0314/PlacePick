package com.jyproject.presentation.ui.feature.placeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.db.usecase.GetPlaceDBInfoUseCase
import com.jyproject.domain.features.db.usecase.PlaceDeleteUseCase
import com.jyproject.domain.features.place.usecase.GetPlaceInfoUseCase
import com.jyproject.domain.models.CommonState
import com.jyproject.presentation.R
import com.jyproject.presentation.mappers.UiMapper
import com.jyproject.presentation.ui.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class PlaceDetailViewModel @AssistedInject constructor(
    @Assisted private val place: String,
    private val getPlaceInfoUseCase: GetPlaceInfoUseCase,
    private val placeDeleteUseCase: PlaceDeleteUseCase,
    private val getPlaceDBInfoUseCase: GetPlaceDBInfoUseCase,
    private val uiMapper: UiMapper
): BaseViewModel<PlaceDetailContract.Event, PlaceDetailContract.State, PlaceDetailContract.Effect>() {
    override fun setInitialState() = PlaceDetailContract.State(
        placeAreaInfo = null,
        placeInfo = null,
        placeInfoState = CommonState.LOADING,
        stateColor = R.color.light_gray_middle1
    )

    override fun handleEvents(event: PlaceDetailContract.Event) {
        when(event) {
            is PlaceDetailContract.Event.Retry -> {}
            is PlaceDetailContract.Event.DeletePlace -> deletePlace(event.place)
            is PlaceDetailContract.Event.NavigationToMain ->
                setEffect { PlaceDetailContract.Effect.Navigation.ToMain }
            is PlaceDetailContract.Event.NavigationToBack ->
                setEffect { PlaceDetailContract.Effect.Navigation.ToBack }
        }
    }

    init {
        getPlaceInfo(place)
        getPlaceDBInfo(place)
    }

    private fun deletePlace(place: String){
        viewModelScope.launch {
            placeDeleteUseCase(place)
        }
    }

    private fun getPlaceInfo(place: String){
        setState { copy(placeInfoState = CommonState.LOADING) }
        if(place.isNotBlank()){
            viewModelScope.launch {
                getPlaceInfoUseCase(place)
                    .onFailure {
                        setState { copy(placeInfoState = CommonState.ERROR) }
                    }
                    .onSuccess { response->
                        response?.let {
                            setState {
                                copy(
                                    placeInfo = response,
                                    stateColor = uiMapper.mapperLivePeopleColor(response.livePeopleInfo ?: "")
                                )
                            }
                        }
                    }
            }
        }
    }

    private fun getPlaceDBInfo(place: String){
        if(place.isNotBlank()){
            viewModelScope.launch {
                getPlaceDBInfoUseCase(place).let { place->
                    setState { copy(placeAreaInfo = place) }
                }
            }
        }
    }

    @AssistedFactory
    interface PlaceDetailViewModelFactory {
        fun create(place: String): PlaceDetailViewModel
    }

    companion object{
        fun providePlaceDetailViewModelFactory(
            factory: PlaceDetailViewModelFactory,
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
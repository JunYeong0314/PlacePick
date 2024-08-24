package com.jyproject.presentation.ui.feature.placeDetail

import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.db.usecase.GetPlaceDBInfoUseCase
import com.jyproject.domain.features.db.usecase.PlaceDeleteUseCase
import com.jyproject.domain.features.place.usecase.GetPlaceInfoUseCase
import com.jyproject.domain.features.seoulbike.GetSeoulBikeInfoUseCase
import com.jyproject.presentation.R
import com.jyproject.presentation.mappers.UiMapper
import com.jyproject.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val getPlaceInfoUseCase: GetPlaceInfoUseCase,
    private val placeDeleteUseCase: PlaceDeleteUseCase,
    private val getPlaceDBInfoUseCase: GetPlaceDBInfoUseCase,
    private val getSeoulBikeInfoUseCase: GetSeoulBikeInfoUseCase,
    private val uiMapper: UiMapper
): BaseViewModel<PlaceDetailContract.Event, PlaceDetailContract.State, PlaceDetailContract.Effect>() {
    override fun setInitialState() = PlaceDetailContract.State(
        placeAreaInfo = null,
        placeInfo = null,
        placeInfoState = PlaceInfoState.INIT,
        placeStateColor = R.color.light_gray_middle1,
        seoulBikeInfo = emptyList(),
        errorMsg = null
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

    private fun deletePlace(place: String){
        viewModelScope.launch {
            placeDeleteUseCase(place)
        }
    }

    fun getPlaceInfo(place: String){
        setState { copy(placeInfoState = PlaceInfoState.LOADING) }
        if(place.isNotBlank()){
            viewModelScope.launch {
                getPlaceInfoUseCase(place)
                    .onFailure { exception->
                        val errorState = when(exception) {
                            is java.net.UnknownHostException -> PlaceInfoState.NETWORK_ERROR
                            is java.net.SocketTimeoutException -> PlaceInfoState.NETWORK_ERROR
                            else -> PlaceInfoState.ERROR
                        }
                        setState { copy(placeInfoState = errorState, errorMsg = exception.message) }
                    }
                    .onSuccess { response->
                        response?.let {
                            setState {
                                copy(
                                    placeInfo = response,
                                    placeStateColor = uiMapper.mapperLivePeopleColor(response.livePeopleInfo ?: ""),
                                    placeInfoState = PlaceInfoState.SUCCESS,
                                )
                            }
                        }
                    }
            }
        }
    }

    fun getPlaceDBInfo(place: String){
        if(place.isNotBlank()){
            viewModelScope.launch {
                getPlaceDBInfoUseCase(place).let { place->
                    setState { copy(placeAreaInfo = place) }
                }
            }
        }
    }

    fun getSeoulBikeLocationInfo(regionName: String?) {
        if(!regionName.isNullOrEmpty()) {
            viewModelScope.launch {
                getSeoulBikeInfoUseCase(regionName)
                    .onFailure { exception->
                        val errorState = when(exception) {
                            is java.net.UnknownHostException -> PlaceInfoState.NETWORK_ERROR
                            is java.net.SocketTimeoutException -> PlaceInfoState.NETWORK_ERROR
                            else -> PlaceInfoState.ERROR
                        }
                        setState { copy(placeInfoState = errorState, errorMsg = exception.message) }
                    }
                    .onSuccess { response->
                        response?.let {
                            setState { copy(seoulBikeInfo = response) }
                        }
                    }
            }
        }
    }
}
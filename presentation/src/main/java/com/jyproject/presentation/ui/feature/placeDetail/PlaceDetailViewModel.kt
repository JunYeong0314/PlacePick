package com.jyproject.presentation.ui.feature.placeDetail

import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.cycle.GetCycleInfoUseCase
import com.jyproject.domain.features.db.usecase.GetPlaceDBInfoUseCase
import com.jyproject.domain.features.db.usecase.PlaceDeleteUseCase
import com.jyproject.domain.features.place.usecase.GetPlaceInfoUseCase
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
    private val getCycleInfoUseCase: GetCycleInfoUseCase,
    private val uiMapper: UiMapper
): BaseViewModel<PlaceDetailContract.Event, PlaceDetailContract.State, PlaceDetailContract.Effect>() {
    override fun setInitialState() = PlaceDetailContract.State(
        placeAreaInfo = null,
        placeInfo = null,
        placeInfoState = PlaceInfoState.INIT,
        placeStateInfo = "",
        placeStateColor = R.color.light_gray_middle1,
        placeStateInfoMsg = ""
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
        setState { copy(placeInfoState = PlaceInfoState.LOADING, placeStateInfo = "연결중", placeStateInfoMsg = "불러오는 중..") }
        if(place.isNotBlank()){
            viewModelScope.launch {
                getPlaceInfoUseCase(place)
                    .onFailure {
                        setState {
                            copy(
                                placeInfoState = PlaceInfoState.ERROR,
                                placeStateInfo = "에러",
                                placeStateInfoMsg = "[Error] 데이터를 불러올 수 없습니다.",
                                placeStateColor = R.color.error_red
                            )
                        }
                    }
                    .onSuccess { response->
                        response?.let {
                            setState {
                                copy(
                                    placeInfo = response,
                                    placeStateInfo = response.livePeopleInfo ?: "",
                                    placeStateColor = uiMapper.mapperLivePeopleColor(response.livePeopleInfo ?: ""),
                                    placeInfoState = PlaceInfoState.SUCCESS,
                                    placeStateInfoMsg = response.livePeopleInfoMsg ?: ""
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
}
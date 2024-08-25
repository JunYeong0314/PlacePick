package com.jyproject.presentation.ui.feature.mapDetail

import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.seoulbike.GetSeoulBikeInfoUseCase
import com.jyproject.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapDetailViewModel @Inject constructor(
    private val seoulBikeInfoUseCase: GetSeoulBikeInfoUseCase
): BaseViewModel<MapDetailContract.Event, MapDetailContract.State, MapDetailContract.Effect>() {
    override fun setInitialState(): MapDetailContract.State = MapDetailContract.State(
        mapDetailState = MapDetailState.INIT,
        seoulBikeInfo = emptyList(),
        errorThrowable = null
    )

    override fun handleEvents(event: MapDetailContract.Event) {
        when(event){
            is MapDetailContract.Event.NavigationToBack -> {
                setEffect { MapDetailContract.Effect.Navigation.ToBack }
            }
        }
    }

    fun getSeoulBikeLocationInfo(regionName: String?){
        setState { copy(mapDetailState = MapDetailState.LOADING) }
        if(!regionName.isNullOrEmpty()){
            viewModelScope.launch {
                seoulBikeInfoUseCase(regionName)
                    .onFailure { exception->
                        val errorState = when(exception) {
                            is java.net.UnknownHostException -> MapDetailState.NETWORK_ERROR
                            is java.net.SocketTimeoutException -> MapDetailState.NETWORK_ERROR
                            else -> MapDetailState.ERROR
                        }
                        setState { copy(mapDetailState = errorState, errorThrowable = exception) }
                    }
                    .onSuccess { response->
                        response?.let {
                            setState { copy(seoulBikeInfo = response) }
                        }
                        setState { copy(mapDetailState = MapDetailState.SUCCESS) }
                    }
            }
        }
    }
}
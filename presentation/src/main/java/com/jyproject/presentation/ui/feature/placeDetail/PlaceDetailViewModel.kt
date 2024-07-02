package com.jyproject.presentation.ui.feature.placeDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.db.usecase.GetPlaceDBInfoUseCase
import com.jyproject.domain.features.db.usecase.PlaceDeleteUseCase
import com.jyproject.domain.features.place.usecase.GetPlaceInfoUseCase
import com.jyproject.domain.models.Place
import com.jyproject.domain.models.PlaceInfo
import com.jyproject.presentation.mappers.UiMapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaceDetailViewModel @AssistedInject constructor(
    @Assisted private val place: String,
    private val getPlaceInfoUseCase: GetPlaceInfoUseCase,
    private val placeDeleteUseCase: PlaceDeleteUseCase,
    private val getPlaceDBInfoUseCase: GetPlaceDBInfoUseCase,
    private val uiMapper: UiMapper
): ViewModel() {
    private val _placeInfo = MutableStateFlow<PlaceInfo?>(null)
    val placeInfo = _placeInfo.asStateFlow()

    private val _errorObserver = MutableStateFlow(false)
    val errorObserver = _errorObserver.asStateFlow()

    private val _placeDBInfo = MutableStateFlow<Place?>(null)
    val placeDBInfo = _placeDBInfo.asStateFlow()

    init {
        getPlaceInfo(place)
        getPlaceDBInfo(place)
    }

    fun deletePlace(place: String){
        viewModelScope.launch {
            placeDeleteUseCase(place)
        }
    }

    fun getStateColor(liveState: String): Int{
        return uiMapper.mapperLivePeopleColor(liveState)
    }

    private fun getPlaceInfo(place: String){
        if(place.isNotBlank()){
            viewModelScope.launch {
                getPlaceInfoUseCase(place)
                    .onFailure {
                        _errorObserver.update { true }
                    }
                    .onSuccess { response->
                        response?.let { _placeInfo.update { response } }
                    }
            }
        }
    }

    private fun getPlaceDBInfo(place: String){
        if(place.isNotBlank()){
            viewModelScope.launch {
                _placeDBInfo.value = getPlaceDBInfoUseCase(place)
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
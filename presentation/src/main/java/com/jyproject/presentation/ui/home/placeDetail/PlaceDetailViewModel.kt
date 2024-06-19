package com.jyproject.presentation.ui.home.placeDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.db.usecase.PlaceDeleteUseCase
import com.jyproject.domain.features.place.usecase.GetPlaceInfoUseCase
import com.jyproject.domain.models.PlaceInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlaceDetailViewModel @AssistedInject constructor(
    @Assisted private val place: String,
    private val getPlaceInfoUseCase: GetPlaceInfoUseCase,
    private val placeDeleteUseCase: PlaceDeleteUseCase
): ViewModel() {
    private val _placeInfo = MutableStateFlow<PlaceInfo?>(null)
    val placeInfo = _placeInfo.asStateFlow()

    private val _errorObserver = MutableStateFlow(false)
    val errorObserver = _errorObserver.asStateFlow()

    init {
        getPlaceInfo(place)
    }

    fun deletePlace(place: String){
        viewModelScope.launch {
            placeDeleteUseCase(place)
        }
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
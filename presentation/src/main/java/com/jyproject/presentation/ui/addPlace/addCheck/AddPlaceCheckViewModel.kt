package com.jyproject.presentation.ui.addPlace.addCheck

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.db.usecase.PlaceAddUseCase
import com.jyproject.domain.features.db.usecase.PlaceFindUseCase
import com.jyproject.domain.features.place.PlaceRepository
import com.jyproject.domain.features.place.usecase.SearchPlaceUseCase
import com.jyproject.presentation.ui.addPlace.AddPlaceViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddPlaceCheckViewModel @AssistedInject constructor(
    @Assisted private val placeName: String,
    private val placeAddUseCase: PlaceAddUseCase,
    private val placeFindUseCase: PlaceFindUseCase,
    private val searchPlaceUseCase: SearchPlaceUseCase
): ViewModel() {

    private val _checkDupPlace = MutableStateFlow(false)
    val checkDupPlace: StateFlow<Boolean> = _checkDupPlace.asStateFlow()

    private val _errorObserve = MutableStateFlow(false)
    val errorObserve: StateFlow<Boolean> = _errorObserve.asStateFlow()


    private var placeArea: String? = null

    init {
        viewModelScope.launch {
            searchPlaceArea(placeName)
        }
    }

    fun addPlace(place: String){
        placeArea?.let { placeArea->
            viewModelScope.launch {
                placeAddUseCase(place, placeArea)
            }
        }
    }

    suspend fun checkDupPlace(placeName: String): Boolean{
        val result = placeFindUseCase(placeName)

        _checkDupPlace.update { result }
        return result
    }

    private suspend fun searchPlaceArea(placeName: String) {
        searchPlaceUseCase(placeName)
            .onFailure { _errorObserve.update { true } }
            .onSuccess { placeArea = it?.firstOrNull()?.placeArea }
    }

    @AssistedFactory
    interface AddPlaceCheckViewModelFactory {
        fun create(place: String): AddPlaceCheckViewModel
    }

    companion object {
        fun provideAddPlaceCheckViewModelFactory(
            factory: AddPlaceCheckViewModelFactory,
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
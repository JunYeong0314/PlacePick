package com.jyproject.presentation.ui.addPlace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.place.PlaceRepository
import com.jyproject.domain.models.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlaceViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
): ViewModel() {
    private val _placeList = MutableStateFlow<List<Place>>(emptyList())
    val placeList: StateFlow<List<Place>> = _placeList.asStateFlow()

    private val _searchError = MutableStateFlow<Boolean>(false)
    val searchError: StateFlow<Boolean> = _searchError.asStateFlow()

    fun searchPlace(searchPlace: String){
        viewModelScope.launch {
            placeRepository.searchPlace(searchPlace)
                .onFailure { _searchError.update { true } }
                .onSuccess { placeList->
                    placeList?.let { _placeList.update { placeList } }
                }
        }
    }
}
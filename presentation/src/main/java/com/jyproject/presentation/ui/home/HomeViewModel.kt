package com.jyproject.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.db.repository.PlaceDataRepository
import com.jyproject.domain.models.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val placeDataRepository: PlaceDataRepository
): ViewModel() {
    private val _placeData = MutableStateFlow<List<Place>?>(emptyList())
    val placeData: StateFlow<List<Place>?> = _placeData.asStateFlow()

    init {
        getPlaceData()
    }

    private fun getPlaceData(){
        viewModelScope.launch {
            placeDataRepository.readPlace().collect{ place->
                _placeData.update { place }
            }
        }
    }

}
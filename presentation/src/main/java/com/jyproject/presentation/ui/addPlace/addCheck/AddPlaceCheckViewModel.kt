package com.jyproject.presentation.ui.addPlace.addCheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.db.usecase.PlaceAddUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlaceCheckViewModel @Inject constructor(
    private val placeAddUseCase: PlaceAddUseCase
): ViewModel() {

    fun addPlace(place: String){
        viewModelScope.launch {
            placeAddUseCase(place)
        }
    }
}
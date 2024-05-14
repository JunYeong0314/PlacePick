package com.jyproject.presentation.ui.addPlace.addCheck

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.db.usecase.PlaceAddUseCase
import com.jyproject.domain.features.db.usecase.PlaceFindUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlaceCheckViewModel @Inject constructor(
    private val placeAddUseCase: PlaceAddUseCase,
    private val placeFindUseCase: PlaceFindUseCase
): ViewModel() {

    private val _checkDupPlace = MutableStateFlow(false)
    val checkDupPlace: StateFlow<Boolean> = _checkDupPlace.asStateFlow()

    fun addPlace(place: String){
        viewModelScope.launch {
            placeAddUseCase(place)
        }
    }

    suspend fun checkDupPlace(place: String): Boolean{
        val result = placeFindUseCase(place)

        _checkDupPlace.update { result }
        return result
    }
}
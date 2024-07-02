package com.jyproject.presentation.ui.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.db.repository.PlaceDataRepository
import com.jyproject.domain.features.db.usecase.PlaceReadUseCase
import com.jyproject.domain.models.Place
import com.jyproject.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val placeReadUseCase: PlaceReadUseCase
): BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {
    init {
        getPlaceData()
    }

    override fun setInitialState() = HomeContract.State(
        placeList = emptyList(),
        isLoading = true,
    )

    override fun handleEvents(event: HomeContract.Event) {
        when(event){
            is HomeContract.Event.NavigationToPlaceDetail -> setEffect {
                HomeContract.Effect.Navigation.ToPlaceDetail(
                    event.place
                )
            }
            is HomeContract.Event.NavigationToPlaceSearch -> setEffect { HomeContract.Effect.Navigation.ToPlaceSearch }
        }
    }

    private fun getPlaceData(){
        viewModelScope.launch {
            placeReadUseCase().collect{ place->
                setState { copy(placeList = place, isLoading = false) }
            }
        }
    }



}
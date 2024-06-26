package com.jyproject.presentation.di

import com.jyproject.presentation.ui.addPlace.AddPlaceViewModel
import com.jyproject.presentation.ui.addPlace.addCheck.AddPlaceCheckViewModel
import com.jyproject.presentation.ui.home.placeDetail.PlaceDetailViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun placeDetailViewModelFactory(): PlaceDetailViewModel.PlaceDetailViewModelFactory

    fun addPlaceCheckViewModelFactory(): AddPlaceCheckViewModel.AddPlaceCheckViewModelFactory
}
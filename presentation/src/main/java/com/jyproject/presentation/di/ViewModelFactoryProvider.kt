package com.jyproject.presentation.di

import com.jyproject.presentation.ui.feature.placeAdd.PlaceAddViewModel
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun placeDetailViewModelFactory(): PlaceDetailViewModel.PlaceDetailViewModelFactory

    fun placeAddViewModelFactory(): PlaceAddViewModel.PlaceAddViewModelFactory
}
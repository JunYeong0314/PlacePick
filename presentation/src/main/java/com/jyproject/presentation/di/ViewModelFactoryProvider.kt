package com.jyproject.presentation.di

import com.jyproject.presentation.ui.feature.placeAdd.PlaceAddViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun placeAddViewModelFactory(): PlaceAddViewModel.PlaceAddViewModelFactory
}
package com.jyproject.presentation.ui.home.placeDetail

import android.app.Activity
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jyproject.presentation.di.ViewModelFactoryProvider
import dagger.hilt.android.EntryPointAccessors

@Composable
fun PlaceDetailScreen(
    navController: NavController,
    place: String?,
){
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).placeDetailViewModelFactory()

    val viewModel: PlaceDetailViewModel = viewModel(
        factory = PlaceDetailViewModel.providePlaceDetailViewModelFactory(factory, place ?: "")
    )

    Text(text = place!!)
    val placeInfo = viewModel.placeInfo.collectAsStateWithLifecycle()

    LaunchedEffect(placeInfo.value) {

    }
}
package com.jyproject.presentation.anim

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jyproject.presentation.R

@Composable
fun LottieAddPlaceCheck(
    modifier: Modifier
){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_place_add))

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        isPlaying = true,
        iterations = LottieConstants.IterateForever
    )
}
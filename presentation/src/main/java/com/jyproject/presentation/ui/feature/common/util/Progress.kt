package com.jyproject.presentation.ui.feature.common.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import com.jyproject.presentation.R

@Composable
fun CircularProgress() {
    Box(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = colorResource(id = R.color.app_base))
    }
}
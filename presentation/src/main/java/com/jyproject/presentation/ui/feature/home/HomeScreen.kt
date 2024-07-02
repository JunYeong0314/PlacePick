package com.jyproject.presentation.ui.feature.home
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.domain.models.Place
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.feature.common.CircularProgress
import com.jyproject.presentation.ui.feature.home.composable.EmptyPlaceList
import com.jyproject.presentation.ui.feature.home.composable.PlaceAddButton
import com.jyproject.presentation.ui.feature.home.composable.PlaceCardList
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    state: HomeContract.State,
    effectFlow: Flow<HomeContract.Effect>?,
    onEventSend: (event: HomeContract.Event) -> Unit,
    onEffectSend: (effect: HomeContract.Effect.Navigation) -> Unit
){
    LaunchedEffect(effectFlow) {
        effectFlow?.collect { effect ->
            when(effect) {
                is HomeContract.Effect.Navigation -> { onEffectSend(effect) }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            state.placeList.isEmpty() -> { EmptyPlaceList() }
            state.isLoading -> { CircularProgress() }
            else -> {
                PlaceCardList(
                    placeList = state.placeList,
                    onEventSend = onEventSend
                )
            }
        }

    }
    PlaceAddButton(onClickAddBtn = { onEventSend(HomeContract.Event.NavigationToPlaceSearch) } )
}




package com.jyproject.presentation.ui.feature.home.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jyproject.domain.models.Place
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.feature.home.HomeContract

@Composable
fun PlaceCardList(
    placeList: List<Place>,
    onEventSend: (event: HomeContract.Event) -> Unit
) {
    val cityList = listOf(
        R.drawable.ic_city_1, R.drawable.ic_city_2,
        R.drawable.ic_city_3, R.drawable.ic_city_4
    )

    LazyRow(
        modifier = Modifier
    ) {
        itemsIndexed(placeList){index: Int, place: Place ->
            if(index == 0) Spacer(modifier = Modifier.size(40.dp))
            PlaceCard(
                place = place.place!!,
                painterId = cityList[index%4],
                onEventSend = onEventSend,
            )
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

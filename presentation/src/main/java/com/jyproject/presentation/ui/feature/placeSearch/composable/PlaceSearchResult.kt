package com.jyproject.presentation.ui.feature.placeSearch.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.domain.models.Place
import com.jyproject.presentation.ui.feature.placeSearch.PlaceSearchContract
import com.jyproject.presentation.ui.util.modifierExtensions.singleClick.clickableSingle

@Composable
fun PlaceSearchResult(
    placeList: List<Place>,
    onEventSend: (event: PlaceSearchContract.Event) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        itemsIndexed(placeList) { _, places ->
            places.let { searchResult ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickableSingle {
                            onEventSend(
                                PlaceSearchContract.Event.NavigateToPlaceAdd(
                                    searchResult.place!!
                                )
                            )
                        }
                ){
                    searchResult.place?.let { Text(text = it, fontSize = 18.sp) }
                }
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }
}
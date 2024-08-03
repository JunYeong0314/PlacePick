package com.jyproject.presentation.ui.feature.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.domain.models.Place
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.feature.home.HomeContract
import com.jyproject.presentation.ui.feature.placeDetail.composable.DeleteCheckDialog
import com.jyproject.presentation.ui.util.modifierExtensions.singleClick.clickableSingle

@Composable
fun PlaceBlocks(
    state: HomeContract.State,
    onEventSend: (event: HomeContract.Event) -> Unit,
    onClickAddBtn: () -> Unit
) {
    Column(
        modifier = Modifier.padding(start = 12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "추가한 장소",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableSingle { onClickAddBtn() },
                contentAlignment = Alignment.CenterEnd
            ){
                Icon(
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 6.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "add",
                    tint = Color.DarkGray
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))

        if(state.placeList.isEmpty()){
            EmptyPlaceList()
        }else{
            LazyRow(
                modifier = Modifier
            ) {
                itemsIndexed(state.placeList){_, place: Place ->
                    place.place?.let { placeValue->
                        PlaceBlock(
                            place = placeValue,
                            onEventSend = onEventSend,
                        )
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
        }
    }
}

@Composable
fun PlaceBlock(
    place: String,
    onEventSend: (event: HomeContract.Event) -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if(showDeleteDialog) {
        DeleteCheckDialog(
            onDismissRequest = { showDeleteDialog = false },
            onConfirmation = {
                onEventSend(HomeContract.Event.DeletePlace(place))
                showDeleteDialog = false
            }
        )
    }

    Row(
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.app_base),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .clickableSingle { onEventSend(HomeContract.Event.NavigationToPlaceDetail(place)) }
            ,
            text = place,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.size(4.dp))
        Icon(
            modifier = Modifier
                .size(20.dp)
                .clickableSingle { showDeleteDialog = true },
            imageVector = Icons.Filled.Clear,
            contentDescription = "clear",
            tint = Color.White
        )
    }
}
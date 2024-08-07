package com.jyproject.presentation.ui.feature.placeDetail.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailContract

@Composable
fun TopBar(
    place: String,
    state: PlaceDetailContract.State,
    onEventSend: (event: PlaceDetailContract.Event) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    var offsetX by remember { mutableStateOf(0.dp) }
    var parentWidth by remember { mutableIntStateOf(0) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) }

    if(showDeleteDialog) {
        DeleteCheckDialog(
            onDismissRequest = { showDeleteDialog = false },
            onConfirmation = {
                onEventSend(PlaceDetailContract.Event.DeletePlace(place))
                onEventSend(PlaceDetailContract.Event.NavigationToMain)
                showDeleteDialog = false
            }
        )
    }

    if(showInfoDialog) {
        PlaceInfoDialog(onConfirmation = { showInfoDialog = false }, place = place, placeArea = state.placeAreaInfo?.placeArea)
    }

    // Title
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(Color.White)
            .onPlaced { parentWidth = it.size.width },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = place,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.app_base),
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier
                .background(
                    color = colorResource(id = state.placeStateColor),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 8.dp),
            text = state.placeStateInfo,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }

    // Back Button
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart){
        IconButton(onClick = { onEventSend(PlaceDetailContract.Event.NavigationToBack) }) {
            Icon(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color.LightGray
            )
        }
    }

    // Menu Button
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ){
        IconButton(
            modifier = Modifier,
            onClick = { expanded = !expanded },
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "more",
                tint = Color.LightGray
            )
        }

        DropdownMenu(
            modifier = Modifier
                .onPlaced {
                    val popUpWidthPx = parentWidth - it.size.width
                    offsetX = with(density) {
                        popUpWidthPx.toDp()
                    }
                }
                .background(Color.White),
            expanded = expanded,
            offset = DpOffset(offsetX, 0.dp),
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = { Text(text = "삭제") },
                onClick = {
                    showDeleteDialog = true
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(text = "정보") },
                onClick = {
                    showInfoDialog = true
                    expanded = false
                }
            )
        }
    }
}
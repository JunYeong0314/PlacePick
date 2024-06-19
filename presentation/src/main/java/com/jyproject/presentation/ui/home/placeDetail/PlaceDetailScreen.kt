package com.jyproject.presentation.ui.home.placeDetail

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jyproject.presentation.R
import com.jyproject.presentation.di.ViewModelFactoryProvider
import dagger.hilt.android.EntryPointAccessors

@Composable
fun PlaceDetailScreen(
    navController: NavController,
    place: String?,
    onClickDeletePlace: () -> Unit
){
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).placeDetailViewModelFactory()
    val viewModel: PlaceDetailViewModel = viewModel(
        factory = PlaceDetailViewModel.providePlaceDetailViewModelFactory(factory, place ?: "")
    )
    val placeInfo = viewModel.placeInfo.collectAsStateWithLifecycle()

    LaunchedEffect(placeInfo.value) {

    }

    place?.let {
        Scaffold(
            modifier = Modifier.background(Color.White),
            topBar = {
                DetailTopBar(
                    place = place,
                    viewModel = viewModel,
                    onClickDeletePlace = onClickDeletePlace
                )
            }
        ) { innerPadding->
            Column(modifier = Modifier.padding(innerPadding)) {

            }
        }
    }


}

@Composable
private fun DetailTopBar(
    place: String,
    viewModel: PlaceDetailViewModel,
    onClickDeletePlace: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    var offsetX by remember { mutableStateOf(0.dp) }
    var parentWidth by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    if(showDialog) {
        DeleteCheckDialog(
            onDismissRequest = { showDialog = false },
            onConfirmation = {
                viewModel.deletePlace(place)
                onClickDeletePlace()
                showDialog = false
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(Color.White)
            .onPlaced { parentWidth = it.size.width }
        ,
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
    }

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
                onClick = { showDialog = true }
            )
            DropdownMenuItem(
                text = { Text(text = "정보") },
                onClick = { /*TODO*/ }
            )
        }
    }
}

@Composable
private fun DeleteCheckDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
){
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = colorResource(id = R.color.app_base)
            )
        },
        containerColor = Color.White,
        text = {
            Column {
                Text(text = "삭제 후 다시 추가 가능해요!")
                Text(text = "해당지역을 정말 삭제하시겠어요?")
            }
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(
                    text = "삭제",
                    color = Color.Red
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(
                    text = "취소",
                    color = Color.DarkGray
                )
            }
        },
    )

}


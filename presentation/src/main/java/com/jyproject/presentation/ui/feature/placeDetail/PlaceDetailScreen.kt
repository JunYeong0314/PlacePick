package com.jyproject.presentation.ui.feature.placeDetail

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
    val placeDBInfo = viewModel.placeDBInfo.collectAsStateWithLifecycle()

    place?.let {
        Scaffold(
            modifier = Modifier.background(Color.White),
            topBar = {
                DetailTopBar(
                    place = place,
                    placeArea = placeDBInfo.value?.placeArea,
                    livePeopleInfo = placeInfo.value?.livePeopleInfo,
                    viewModel = viewModel,
                    onClickDeletePlace = onClickDeletePlace,
                    onClickBackBtn = { navController.navigateUp() }
                )
            }
        ) { innerPadding->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color.White)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.ic_app),
                        contentDescription = "icon",
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        modifier = Modifier
                            .shadow(elevation = 24.dp, shape = RoundedCornerShape(0.05.dp))
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(vertical = 2.dp)
                            .padding(start = 6.dp, end = 8.dp)
                            .width(250.dp),
                        text = placeInfo.value?.livePeopleInfoMsg ?: "불러오는 중...",
                        fontSize = 12.sp
                    )
                }

            }
        }
    }


}

@Composable
private fun DetailTopBar(
    place: String,
    placeArea: String?,
    livePeopleInfo: String?,
    viewModel: PlaceDetailViewModel,
    onClickDeletePlace: () -> Unit,
    onClickBackBtn: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    var offsetX by remember { mutableStateOf(0.dp) }
    var parentWidth by remember { mutableIntStateOf(0) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) }
    var stateColor by remember { mutableIntStateOf(R.color.light_gray_middle1) }

    if(!livePeopleInfo.isNullOrBlank()) stateColor = viewModel.getStateColor(livePeopleInfo)

    if(showDeleteDialog) {
        DeleteCheckDialog(
            onDismissRequest = { showDeleteDialog = false },
            onConfirmation = {
                viewModel.deletePlace(place)
                onClickDeletePlace()
                showDeleteDialog = false
            }
        )
    }

    if(showInfoDialog) {
        PlaceInfoDialog(onConfirmation = { showInfoDialog = false }, place = place, placeArea = placeArea)
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
                    color = colorResource(id = stateColor),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 8.dp),
            text = livePeopleInfo ?: "연결중",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }

    // Back Button
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart){
        IconButton(onClick = { onClickBackBtn() }) {
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

@Composable
private fun PlaceInfoDialog(
    onConfirmation: () -> Unit,
    place: String,
    placeArea: String?
){
    AlertDialog(
        modifier = Modifier
            .width(300.dp)
            .height(220.dp),
        containerColor = Color.White,
        text = {
           Column(
               modifier = Modifier.fillMaxSize(),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center
           ) {
               Icon(
                   modifier = Modifier.size(50.dp),
                   imageVector = Icons.Default.LocationOn,
                   contentDescription = null,
                   tint = colorResource(id = R.color.app_base)
               )
               Spacer(modifier = Modifier.size(8.dp))
               Text(text = "지역명: $place")
               Text(text = "관할구: $placeArea")
           }
        },
        onDismissRequest = { onConfirmation() },
        confirmButton = { 
            TextButton(onClick = { onConfirmation() }) {
                Text(text = "확인")
            }
        },
        dismissButton = null
    )
    
}


package com.jyproject.presentation.ui.addPlace.addCheck

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.jyproject.presentation.R
import com.jyproject.presentation.anim.LottieAddPlaceCheck
import kotlinx.coroutines.launch

@Composable
fun AddPlaceCheckScreen(
    navController: NavController,
    placeName: String?,
    onClickAdd: () -> Unit,
    viewModel: AddPlaceCheckViewModel = hiltViewModel()
){
    val snackBarHostState = remember { SnackbarHostState() }
    val dupPlaceState by viewModel.checkDupPlace.collectAsStateWithLifecycle()

    LaunchedEffect(dupPlaceState) {
        if(dupPlaceState){
            snackBarHostState.showSnackbar(
                message = "이미 등록되어있는 장소입니다.",
                duration = SnackbarDuration.Short
            )
        }
    }

    placeName?.let {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
        ) { innerPadding->
            Icon(
                modifier = Modifier
                    .size(42.dp)
                    .clickable { navController.navigateUp() }
                    .padding(top = 8.dp)
                ,
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center
            ) {
                val placeText = if(placeName.length > 6) placeName.substring(0, 6) + ".." else placeName
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Row {
                        Text(
                            text = placeText,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.app_base),
                            fontSize = 32.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "을(를)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        )
                    }
                    Text(
                        text = "추가하시겠어요?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                }
                LottieAddPlaceCheck(modifier = Modifier.height(300.dp))
                CheckButtonBox(
                    onClickAdd = onClickAdd,
                    onClickCancel = { navController.navigateUp() },
                    viewModel = viewModel,
                    placeName = placeName
                )
            }
        }
    }
}

@Composable
private fun CheckButtonBox(
    onClickAdd: () -> Unit,
    onClickCancel: () -> Unit,
    viewModel: AddPlaceCheckViewModel,
    placeName: String
){
    val scope = rememberCoroutineScope()

    Button(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
        ,
        onClick = {
            scope.launch {
                if(!viewModel.checkDupPlace(placeName)){
                    viewModel.addPlace(placeName)
                    onClickAdd()
                }
            }
        },
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.app_base)
        )
    ) {
        Text(text = "네, 추가할래요!", fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.size(4.dp))
    Button(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
        ,
        onClick = { onClickCancel() },
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray
        )
    ) {
        Text(text = "아니요, 다음에 할게요!", fontWeight = FontWeight.Bold)
    }
}
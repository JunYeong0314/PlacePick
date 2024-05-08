package com.jyproject.presentation.ui.addPlace

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.util.Destination

@Composable
fun AddPlaceScreen(
    navController: NavController,
    viewModel: AddPlaceViewModel = hiltViewModel(),
    onClickPlace: (String) -> Unit
){
    val focusManager = LocalFocusManager.current
    var searchText by remember { mutableStateOf("") }
    val snackBarHostState = remember { SnackbarHostState() }
    val placeList by viewModel.placeList.collectAsStateWithLifecycle()
    val searchError by viewModel.searchError.collectAsStateWithLifecycle()

    LaunchedEffect(searchError) {
        if(searchError) snackBarHostState.showSnackbar(message = "장소를 검색할 수 없습니다.", duration = SnackbarDuration.Short)
    }

    LaunchedEffect(searchText) {
        if(searchText.isNotBlank()) viewModel.searchPlace(searchText)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValue->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues = paddingValue)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    focusManager.clearFocus()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { navController.navigateUp() }
                    ,
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = colorResource(id = R.color.light_gray_hard1)
                )
                PlaceSearchBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp)
                        .padding(end = 10.dp)
                        .background(
                            color = colorResource(id = R.color.light_gray_weak1),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .height(50.dp),
                    focusManager = focusManager,
                    value = searchText,
                    onValueChange = { searchText = it }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(placeList.isEmpty() && searchText.isNotBlank()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(painter = painterResource(id = R.drawable.ic_search), contentDescription = null)
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                text = "검색결과가 없습니다.",
                                fontWeight = FontWeight.Bold,
                                color = Color.LightGray
                            )
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    itemsIndexed(placeList) { _, places ->
                        places.place?.let { searchResult ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onClickPlace(searchResult) }
                            ){
                                Text(text = searchResult, fontSize = 18.sp)
                            }
                            Spacer(modifier = Modifier.size(10.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceSearchBox(
    modifier: Modifier,
    focusManager: FocusManager,
    value: String,
    onValueChange: (String) -> Unit
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SearchTextField(
            modifier = Modifier
                .weight(0.9f)
                .padding(horizontal = 15.dp)
            ,
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
        Icon(
            modifier = Modifier.weight(0.1f),
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = Color.DarkGray
        )
    }
}

@Composable
private fun SearchTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions
){
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = TextStyle(
            fontSize = 18.sp
        ),
        cursorBrush = SolidColor(Color.DarkGray),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(value.isEmpty()) {
                    Text(
                        text = "장소를 검색해보세요.",
                        color = colorResource(id = R.color.light_gray_hard1),
                        fontSize = 16.sp
                    )
                }
            }
            innerTextField()
        }
    )

}

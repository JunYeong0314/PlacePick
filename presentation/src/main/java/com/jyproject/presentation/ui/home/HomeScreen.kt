package com.jyproject.presentation.ui.home
import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.jyproject.domain.models.Place
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.util.Destination

@Composable
fun HomeScreen(
    navController: NavController,
    onClickCard: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
){
    val placeData = viewModel.placeData.collectAsStateWithLifecycle()
    val cityList = listOf(
        R.drawable.ic_city_1, R.drawable.ic_city_2,
        R.drawable.ic_city_3, R.drawable.ic_city_4
    )
    Log.d("HOME", placeData.value.toString())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(placeData.value.isNullOrEmpty()) EmptyPlaceText()
        else{
            LazyRow(
                modifier = Modifier
            ) {
                itemsIndexed(placeData.value!!){index: Int, place: Place ->
                    if(index == 0) Spacer(modifier = Modifier.size(40.dp))
                    PlaceCard(
                        place = place.place!!,
                        painterId = cityList[index%4],
                        onClickCard = onClickCard,
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                }
            }
        }
    }
    AddButton(onClickAddBtn = { navController.navigate(Destination.ADD_PLACE_ROUTE)} )
}

@Composable
private fun PlaceCard(
    place: String,
    painterId: Int,
    onClickCard: (String) -> Unit,
){
    Box(
        modifier = Modifier
            .width(300.dp)
            .height(500.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(4.dp),
                color = colorResource(id = R.color.app_base)
            )
            .clickable { onClickCard(place) }
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth()
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 40.dp, horizontal = 18.dp)
                    ,
                    text = place,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 35.sp,
                    color = colorResource(id = R.color.app_base),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Image(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                ,
                painter = painterResource(id = painterId),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun AddButton(onClickAddBtn: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 12.dp)
        ,
        contentAlignment = Alignment.BottomCenter
    ){
        Row(
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.app_base),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(horizontal = 10.dp, vertical = 6.dp)
                .clickable { onClickAddBtn() }
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Default.Add,
                contentDescription = "add",
                tint = Color.White
            )
            Text(
                modifier = Modifier
                    .padding(start = 2.dp, end = 4.dp)
                ,
                text = "장소추가",
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun EmptyPlaceText(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_empty), contentDescription = null)
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            modifier = Modifier,
            text = "앗! 아직 등록된 장소가 없어요.",
            fontWeight = FontWeight.Bold,
            color = Color.LightGray
        )
    }
}

/* 개발 진행중
@Composable
private fun ex(
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    swipeThreshold: Float = 50f,
    sensitivityFactor: Float = 3f,
    content: @Composable () -> Unit
){
    var offset by remember { mutableFloatStateOf(0f) }
    var dismiss by remember { mutableStateOf(false) }
    val density = LocalDensity.current.density

    Box(modifier = Modifier
        .offset { IntOffset(offset.roundToInt(), 0) }
        .pointerInput(Unit) {
            detectHorizontalDragGestures(onDragEnd = {
                if (dismiss) {
                    if (offset > swipeThreshold) {
                        onSwipeLeft()
                    } else if (offset < -swipeThreshold) {
                        onSwipeRight()
                    }
                    offset = 0f
                }
                dismiss = false
            }) { change, dragAmount ->
                offset += (dragAmount / density) * sensitivityFactor
                when {
                    offset > swipeThreshold -> {
                        dismiss = true
                    }

                    offset < -swipeThreshold -> {
                        dismiss = true
                    }
                }
                if (change.positionChange() != Offset.Zero) change.consume()
            }
        }) {
        content()
    }
}
*/
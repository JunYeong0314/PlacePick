package com.jyproject.presentation.ui.feature.placeDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.feature.placeDetail.composable.TopBar
import kotlinx.coroutines.flow.Flow

@Composable
fun PlaceDetailScreen(
    place: String?,
    state: PlaceDetailContract.State,
    effectFlow: Flow<PlaceDetailContract.Effect>?,
    onEventSend: (event: PlaceDetailContract.Event) -> Unit,
    onEffectSend: (effect: PlaceDetailContract.Effect) -> Unit
){
    LaunchedEffect(effectFlow) {
        effectFlow?.collect { effect->
            when(effect) {
                is PlaceDetailContract.Effect.Navigation.ToBack ->
                    onEffectSend(PlaceDetailContract.Effect.Navigation.ToBack)
                is PlaceDetailContract.Effect.Navigation.ToMain ->
                    onEffectSend(PlaceDetailContract.Effect.Navigation.ToMain)
            }
        }
    }

    place?.let {
        Scaffold(
            modifier = Modifier.background(Color.White),
            topBar = {
                TopBar(
                    place = place,
                    state = state,
                    onEventSend = onEventSend,
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
                        text = state.placeInfo?.livePeopleInfoMsg ?: "불러오는 중...",
                        fontSize = 12.sp
                    )
                }

            }
        }
    }
}





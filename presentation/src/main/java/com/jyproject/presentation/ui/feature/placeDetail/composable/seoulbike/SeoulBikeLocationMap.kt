package com.jyproject.presentation.ui.feature.placeDetail.composable.seoulbike
import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailContract

@OptIn(ExperimentalPermissionsApi::class)
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION]
)
@Composable
fun SeoulBikeLocationMap(
    state: PlaceDetailContract.State,
    onEventSend: (event: PlaceDetailContract.Event) -> Unit,
){
    var showPermissionDialog by remember {
        mutableStateOf(false)
    }
    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val permissionState = rememberMultiplePermissionsState(permissions = permissions)
    val allRequiredPermission =
        permissionState.revokedPermissions.none { it.permission in permissions.first() }

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .height(200.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = "${state.placeAreaInfo?.placeArea} 따릉이 정보",
            fontWeight = FontWeight.Bold
        )
        if(allRequiredPermission) {
            MapScreen(
                state = state,
                onEventSend = onEventSend
            )
        }else{
            NoPermission(onClickPermission = { showPermissionDialog = true })
        }
    }

    if(showPermissionDialog){
        LocationDialog(permissionState = permissionState){
            showPermissionDialog = it
        }
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationDialog(
    permissionState: MultiplePermissionsState,
    showPermissionDialog: (Boolean) -> Unit
){
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = { showPermissionDialog(false) },
        title = {
            Text(text = "위치정보 권한 요청")
        },
        text = {
            Text(text = "PlacePick 서비스를 원활하게 이용하기 위해 위치를 설정해보세요!")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    showPermissionDialog(false)
                    permissionState.launchMultiplePermissionRequest()
                }
            ) {
                Text(text = "확인", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = { showPermissionDialog(false) }) {
                Text(text = "취소", color = Color.Red)
            }
        }
    )
}


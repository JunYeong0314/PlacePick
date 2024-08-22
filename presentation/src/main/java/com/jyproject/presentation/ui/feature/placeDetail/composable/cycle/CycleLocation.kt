package com.jyproject.presentation.ui.feature.placeDetail.composable.cycle
import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalPermissionsApi::class)
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION]
)
@Composable
fun CycleLocation(
    state: PlaceDetailContract.State,
){
    val context = LocalContext.current
    var showPermissionDialog by remember {
        mutableStateOf(false)
    }
    val locationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val permissionState = rememberMultiplePermissionsState(permissions = permissions)
    val allRequiredPermission =
        permissionState.revokedPermissions.none { it.permission in permissions.first() }

    Column(
        modifier = Modifier.padding(start = 12.dp)
    ) {
        Text(
            text = "${state.placeAreaInfo?.placeArea} 따릉이 정보",
            fontWeight = FontWeight.Bold
        )
        if(allRequiredPermission) {
            LocationButton(
                locationProviderClient = locationProviderClient,
                userPreciseLocation =
                permissionState.permissions
                    .filter { it.status.isGranted }
                    .map { it.permission }
                    .contains(Manifest.permission.ACCESS_FINE_LOCATION)
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

@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION]
)
@Composable
fun LocationButton(
    locationProviderClient: FusedLocationProviderClient,
    userPreciseLocation: Boolean
){
    val scope = rememberCoroutineScope()
    var locationInfo by remember {
        mutableStateOf("")
    }

    Column {
        IconButton(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    val priority = if (userPreciseLocation){
                        Priority.PRIORITY_HIGH_ACCURACY
                    } else {
                        Priority.PRIORITY_BALANCED_POWER_ACCURACY
                    }

                    val result = locationProviderClient.getCurrentLocation(
                        priority,
                        CancellationTokenSource().token,
                    ).await()
                    result?.let { fetchedLocation->
                        locationInfo = "let: ${fetchedLocation.latitude} long: ${fetchedLocation.longitude}"
                    }
                }
            }
        ) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
        }

        Text(text = locationInfo)
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


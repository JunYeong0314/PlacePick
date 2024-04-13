package com.jyproject.presentation.ui.home
import android.Manifest
import android.location.LocationRequest
import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navController: NavController
){
    // The location request that defines the location update
    var locationRequest by remember {
        mutableStateOf<LocationRequest?>(null)
    }

    var locationUpdates by remember {
        mutableStateOf("")
    }

    var errorText by remember {
        mutableStateOf("")
    }

    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val permissionState = rememberMultiplePermissionsState(permissions = permissions){ map ->
        val rejectedPermission = map.filterValues { !it }.keys
        errorText = if(rejectedPermission.none{ it in permissions.first() }){
            ""
        } else {
            "${rejectedPermission.joinToString()} required for PlacePick"
        }
    }

    val allRequiredPermission =
        permissionState.revokedPermissions.none { it.permission in permissions.first() }

    Log.d("TEST", allRequiredPermission.toString())

    Button(
        onClick = {
            permissionState.launchMultiplePermissionRequest()
        }
    ) {
        Text(text = "Click")
    }

}
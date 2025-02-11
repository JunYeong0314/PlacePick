package com.jyproject.presentation.navigation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jyproject.presentation.R
import com.jyproject.presentation.anim.horizontallyAnimatedComposableArguments
import com.jyproject.presentation.anim.noAnimatedComposable
import com.jyproject.presentation.anim.noAnimatedComposableArguments
import com.jyproject.presentation.anim.verticallyAnimatedComposable
import com.jyproject.presentation.anim.verticallyAnimatedComposableArguments
import com.jyproject.presentation.navigation.auth.LoginScreenDestination
import com.jyproject.presentation.navigation.auth.RegisterScreenDestination
import com.jyproject.presentation.navigation.place.PlaceAddScreenDestination
import com.jyproject.presentation.navigation.place.PlaceDetailScreenDestination
import com.jyproject.presentation.navigation.place.PlaceSearchScreenDestination
import com.jyproject.presentation.ui.feature.calendar.CalendarScreen
import com.jyproject.presentation.ui.feature.common.util.BottomBar
import com.jyproject.presentation.ui.feature.common.util.TopBar
import com.jyproject.presentation.ui.feature.mypage.MyPageScreen

@RequiresApi(Build.VERSION_CODES.O)
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION]
)
@Composable
fun AppNavigation(
    context: Context
) {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Home, Screen.Calendar, Screen.MyPage
    )
    // 상/하단바 보일 화면 리스트
    val routesWithoutBar = listOf(
        Navigation.Routes.HOME, Navigation.Routes.CALENDAR, Navigation.Routes.MYPAGE
    )
    var isBar by remember { mutableStateOf(true) }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        isBar = destination.route in routesWithoutBar
    }

    Scaffold(
        // A page that hides the Bar
        bottomBar = { if(isBar) BottomBar(navController = navController, items = items) },
        topBar = { if(isBar) TopBar() }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Navigation.Routes.LOGIN
        ){
            noAnimatedComposable(route = Navigation.Routes.LOGIN){
                LoginScreenDestination(context = context, navController = navController)
            }

            noAnimatedComposable(route = Navigation.Routes.HOME) {
                HomeScreenDestination(navController = navController)
            }

            noAnimatedComposable(Navigation.Routes.MYPAGE) { MyPageScreen(navController = navController) }

            noAnimatedComposable(Navigation.Routes.CALENDAR) { CalendarScreen(navController = navController) }

            verticallyAnimatedComposable(route = Navigation.Routes.PLACE_SEARCH) {
                PlaceSearchScreenDestination(navController = navController)
            }

            horizontallyAnimatedComposableArguments(
                route = "${Navigation.Routes.PLACE_ADD}/{${Navigation.Args.PLACE_ADD_NAME}}",
                arguments = listOf(navArgument(Navigation.Args.PLACE_ADD_NAME){
                    type = NavType.StringType }
                )
            ) { navBackStackEntry ->
                val place =
                    requireNotNull(navBackStackEntry.arguments?.getString(Navigation.Args.PLACE_ADD_NAME)) {
                        "Require place"
                    }

                PlaceAddScreenDestination(place = place, navController = navController)
            }

            verticallyAnimatedComposableArguments(
                route = "${Navigation.Routes.PLACE_DETAIL}/{${Navigation.Args.PLACE_DETAIL_NAME}}",
                arguments = listOf(navArgument(Navigation.Args.PLACE_DETAIL_NAME){
                    type = NavType.StringType }
                ),
            ){ navBackStackEntry ->
                val place =
                    requireNotNull(navBackStackEntry.arguments?.getString(Navigation.Args.PLACE_DETAIL_NAME)) {
                        "Require place"
                    }

                PlaceDetailScreenDestination(place = place, navController = navController)
            }

            noAnimatedComposableArguments(
                route = "${Navigation.Routes.MAP_DETAIL}/{${Navigation.Args.MAP_DETAIL_NAME}}",
                arguments = listOf(navArgument(Navigation.Args.MAP_DETAIL_NAME){
                    type = NavType.StringType
                })
            ) { navBackStackEntry ->
                val placeArea =
                    requireNotNull(navBackStackEntry.arguments?.getString(Navigation.Args.MAP_DETAIL_NAME)){
                        "Require placeArea"
                    }

                MapDetailScreenDestination(placeArea = placeArea, navController = navController)
            }

            verticallyAnimatedComposableArguments(
                route = "${Navigation.Routes.REGISTER}/{${Navigation.Args.REGISTER_USER_NUM}}",
                arguments = listOf(
                    navArgument(Navigation.Args.REGISTER_USER_NUM){ type = NavType.StringType }
                )
            ) { navBackStackEntry ->
                val userNum =
                    requireNotNull(navBackStackEntry.arguments?.getString(Navigation.Args.REGISTER_USER_NUM)) {
                        "Require userNum"
                    }

                RegisterScreenDestination(userNum = userNum, navController = navController)
            }
        }
    }

    val activity = (LocalContext.current as? Activity)
    BackHandler(enabled = isBar) {
        activity?.finish()
    }
}

fun NavController.navigateToPlaceDetail(place: String) {
    navigate(route = "${Navigation.Routes.PLACE_DETAIL}/$place")
}

fun NavController.navigateToPlaceAdd(place: String) {
    navigate(route = "${Navigation.Routes.PLACE_ADD}/$place")
}

fun NavController.navigateToMapDetail(placeArea: String) {
    navigate(route = "${Navigation.Routes.MAP_DETAIL}/$placeArea")
}

fun NavController.navigateToRegister(userNum: String) {
    popBackStack()
    navigate(route = "${Navigation.Routes.REGISTER}/$userNum")
}

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector){
    data object Home: Screen("home", R.string.route_home, icon = Icons.Filled.Home)
    data object Calendar: Screen("calendar", R.string.route_calendar, icon = Icons.Filled.DateRange)
    data object MyPage: Screen("mypage", R.string.route_mypage, icon = Icons.Filled.Person)
}

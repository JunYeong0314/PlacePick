package com.jyproject.presentation.navigation

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jyproject.presentation.R
import com.jyproject.presentation.anim.horizontallyAnimatedComposableArguments
import com.jyproject.presentation.anim.noAnimatedComposable
import com.jyproject.presentation.anim.verticallyAnimatedComposable
import com.jyproject.presentation.anim.verticallyAnimatedComposableArguments
import com.jyproject.presentation.ui.feature.common.util.BottomBar
import com.jyproject.presentation.ui.feature.common.util.TopBar
import com.jyproject.presentation.ui.feature.mypage.MyPageScreen

@Composable
fun AppNavigation(
    context: Context
) {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Home, Screen.MyPage
    )
    // 상/하단바 숨김 화면 리스트
    val routesWithoutBar = listOf(
        Navigation.Routes.PLACE_SEARCH, Navigation.Routes.LOGIN,
        "${Navigation.Routes.PLACE_ADD}/{${Navigation.Args.PLACE_ADD_NAME}}",
        "${Navigation.Routes.PLACE_DETAIL}/{${Navigation.Args.PLACE_DETAIL_NAME}}",
        "${Navigation.Routes.MAP_DETAIL}/{${Navigation.Args.MAP_DETAIL_NAME}}"
    )
    var isBar by remember { mutableStateOf(true) }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        isBar = destination.route !in routesWithoutBar
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

            composable(route = Navigation.Routes.HOME) {
                HomeScreenDestination(navController = navController)
            }

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

            noAnimatedComposable(Navigation.Routes.MYPAGE) { MyPageScreen(navController = navController) }

            verticallyAnimatedComposableArguments(
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
        }
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

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector){
    data object Home: Screen("home", R.string.route_home, icon = Icons.Filled.Home)
    data object MyPage: Screen("mypage", R.string.route_mypage, icon = Icons.Filled.FavoriteBorder)
}

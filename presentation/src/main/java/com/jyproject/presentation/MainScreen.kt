package com.jyproject.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jyproject.presentation.anim.horizontallyAnimatedComposableArguments
import com.jyproject.presentation.anim.noAnimatedComposable
import com.jyproject.presentation.anim.verticallyAnimatedComposable
import com.jyproject.presentation.ui.addPlace.addCheck.AddPlaceCheckScreen
import com.jyproject.presentation.ui.addPlace.AddPlaceScreen
import com.jyproject.presentation.ui.home.HomeScreen
import com.jyproject.presentation.ui.home.placeDetail.PlaceDetailScreen
import com.jyproject.presentation.ui.mypage.MyPageScreen
import com.jyproject.presentation.ui.util.Destination

@Composable
fun MainScreen(){
    val items = listOf(
        Screen.Home, Screen.Exam, Screen.MyPage,
    )
    val navController = rememberNavController()
    var isBar by remember { mutableStateOf(true) }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        isBar = destination.route != Destination.ADD_PLACE_ROUTE &&
                destination.route != "${Destination.ADD_PLACE_CHECK_ROUTE}/{${Destination.ADD_PLACE_CHECK_NAME}}"
    }

    Scaffold(
        // A page that hides the Bar
        bottomBar = { if(isBar) BottomBar(navController = navController, items = items) },
        topBar = { if(isBar) TopBar() }
    ) { innerPadding->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.Home.route
        ){
            noAnimatedComposable(Screen.Home.route) {
                HomeScreen(
                    navController = navController,
                    onClickCard = { place->
                        navController.navigate("${Destination.PLACE_DETAIL_ROUTE}/$place")
                    }
                )
            }

            verticallyAnimatedComposable(Destination.ADD_PLACE_ROUTE) {
                AddPlaceScreen(
                    navController = navController,
                    onClickPlace = { placeName: String ->
                        navController.navigate("${Destination.ADD_PLACE_CHECK_ROUTE}/$placeName")
                    }
                )
            }
            horizontallyAnimatedComposableArguments(
                route = "${Destination.ADD_PLACE_CHECK_ROUTE}/{${Destination.ADD_PLACE_CHECK_NAME}}",
                arguments = listOf(navArgument(Destination.ADD_PLACE_CHECK_NAME){
                    type = NavType.StringType }
                ),
            ) { backStackEntry->
                val arguments = requireNotNull(backStackEntry.arguments)
                val placeName = arguments.getString(Destination.ADD_PLACE_CHECK_NAME)

                AddPlaceCheckScreen(
                    navController = navController,
                    placeName = placeName,
                    onClickAdd = {
                        navController.popBackStack(
                            route = Screen.Home.route,
                            inclusive = true
                        )
                        navController.navigate(Screen.Home.route)
                    })
            }

            noAnimatedComposable(Screen.MyPage.route) { MyPageScreen(navController = navController) }

            horizontallyAnimatedComposableArguments(
                route = "${Destination.PLACE_DETAIL_ROUTE}/{${Destination.PLACE_DETAIL_NAME}}",
                arguments = listOf(navArgument(Destination.PLACE_DETAIL_NAME){
                    type = NavType.StringType }
                ),
            ){ backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val place = arguments.getString(Destination.PLACE_DETAIL_NAME)

                PlaceDetailScreen(
                    navController = navController,
                    place = place
                )
            }
        }

    }

}

@Composable
private fun BottomBar(
    navController: NavController,
    items: List<Screen>
){
    NavigationBar(
        modifier = Modifier
            .height(60.dp)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(15.dp)),
        containerColor = colorResource(id = R.color.white)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            NavigationBarItem(
                modifier = Modifier
                    .background(Color.White)
                ,
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = null,
                    )
               },
                onClick = {
                    navController.navigate(screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = colorResource(id = R.color.app_base),
                    unselectedIconColor = colorResource(id = R.color.light_gray_hard1)
                )
            )
        }
    }
}

@Composable
private fun TopBar(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(Color.White)
        ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = R.drawable.ic_app), contentDescription = "ic_app")
    }
}

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector){
    data object Home: Screen("home", R.string.route_home, icon = Icons.Filled.Home)
    data object MyPage: Screen("mypage", R.string.route_mypage, icon = Icons.Filled.FavoriteBorder)
    data object Exam: Screen("example", R.string.example, icon = Icons.Filled.Menu)
}